package uk.co.joshuacross.photogallery.image;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CloudinaryImageService implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public List<ImageModel> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<ImageModel> getImages(List<String> ids) {
         return imageRepository.findAll(ids);
    }

    @Override
    public List<ImageModel> saveImages(List<MultipartFile> files) {
        List<ImageModel> images = new ArrayList<>();

        files.forEach((file -> {
            try {
                Map uploadResult = Singleton.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

                ImageModel image = new ImageModel();
                image.setId((String) uploadResult.get("public_id"));
                image.setName((String) uploadResult.get("original_filename"));
                image.setUrl((String) uploadResult.get("url"));
                image.setContentType((String) uploadResult.get("resource_type"));
                image.setSize((Integer) uploadResult.get("bytes"));

                images.add(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        return imageRepository.save(images);
    }

    @Override
    public List<ImageModel> deleteImages(List<String> ids) {
        List<ImageModel> images = imageRepository.findAll(ids);
        List<String> imagesToDelete = images.stream().map(ImageModel::getId).collect(Collectors.toList());

        try {
            if (images.size() > 0) {
                Singleton.getCloudinary().api().deleteResources(imagesToDelete, ObjectUtils.emptyMap());
                imageRepository.delete(images);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }
}
