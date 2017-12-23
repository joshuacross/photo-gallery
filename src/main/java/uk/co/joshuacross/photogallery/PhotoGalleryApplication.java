package uk.co.joshuacross.photogallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhotoGalleryApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoGalleryApplication.class, args);
	}
}
