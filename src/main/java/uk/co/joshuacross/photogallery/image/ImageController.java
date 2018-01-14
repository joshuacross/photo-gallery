package uk.co.joshuacross.photogallery.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/")
public class ImageController {

    @Bean
    public ImageService imageService() {
        return new CloudinaryImageService();
    }

    @Autowired
    private ImageService imageService;

    @GetMapping("/image")
    public ResponseEntity<List<ImageModel>> getAllImages() {
        return response(imageService.getAllImages());
    }

    @GetMapping("/image/{ids}")
    public ResponseEntity<List<ImageModel>> getImages(@PathVariable(value = "ids") List<String> ids) {
        return response(imageService.getImages(ids));
    }

    @PostMapping("/image")
    public ResponseEntity<List<ImageModel>> postImage(@RequestParam("file") List<MultipartFile> files) {
        return response(imageService.saveImages(files));
    }

    @DeleteMapping("/image/{ids}")
    public ResponseEntity<List<ImageModel>> deleteImage(@PathVariable(value = "ids") List<String> ids) throws Exception {
        return response(imageService.deleteImages(ids));
    }

    public ResponseEntity<List<ImageModel>> response(List<ImageModel> images) {
        if (images == null || images.size() <= 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(images);
    }
}
