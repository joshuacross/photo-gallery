package uk.co.joshuacross.photogallery.image;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileSystemStorage {

    private final Path rootLocation = Paths.get("src/main/resources/static/images");

    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
