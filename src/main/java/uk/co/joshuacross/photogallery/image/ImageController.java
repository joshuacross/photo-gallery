package uk.co.joshuacross.photogallery.image;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/image")
    public ImageModel createImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map uploadResult = Singleton.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        ImageModel image = new ImageModel();
        image.setId((String) uploadResult.get("public_id"));
        image.setName((String) uploadResult.get("original_filename"));
        image.setUrl((String) uploadResult.get("url"));
        image.setContentType((String) uploadResult.get("resource_type"));
        image.setSize((Integer) uploadResult.get("bytes"));

        return imageRepository.save(image);
    }
}
