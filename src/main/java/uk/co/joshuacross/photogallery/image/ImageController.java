package uk.co.joshuacross.photogallery.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class ImageController {

    @Autowired
    FileSystemStorage fileSystemStorage;
    @Autowired
    ImageRepository imageRepository;

    @PostMapping("/image")
    public void createImage(@RequestParam("file") MultipartFile file) {
        fileSystemStorage.store(file);
//        return imageRepository.save(new ImageModel());
    }
}
