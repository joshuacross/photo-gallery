package uk.co.joshuacross.photogallery.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<ImageModel> getAllImages();
    List<ImageModel> getImages(List<String> ids);
    List<ImageModel> saveImages(List<MultipartFile> file);
    List<ImageModel> deleteImages(List<String> ids) throws Exception;
}
