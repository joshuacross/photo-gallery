package uk.co.joshuacross.photogallery.image;

import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @GetMapping("/image")
    public List<ImageModel> getImages() {
        return imageRepository.findAll();
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<ImageModel> getImage(@PathVariable(value = "id") String id) {
        ImageModel image = imageRepository.findOne(id);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(image);
    }

    @PostMapping("/image")
    public ImageModel postImage(@RequestParam("file") MultipartFile file) throws IOException {
        Map uploadResult = Singleton.getCloudinary().uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type", "auto"));

        ImageModel image = new ImageModel();
        image.setId((String) uploadResult.get("public_id"));
        image.setName((String) uploadResult.get("original_filename"));
        image.setUrl((String) uploadResult.get("url"));
        image.setContentType((String) uploadResult.get("resource_type"));
        image.setSize((Integer) uploadResult.get("bytes"));

        return imageRepository.save(image);
    }

    @DeleteMapping("/image/{id}")
    public ResponseEntity<ImageModel> deleteImage(@PathVariable(value = "id") String id) throws Exception {
        ImageModel image = imageRepository.findOne(id);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        imageRepository.delete(image);
        Singleton.getCloudinary().api().deleteResources(Arrays.asList(image.getId()), ObjectUtils.emptyMap());

        return ResponseEntity.ok().build();
    }
}
