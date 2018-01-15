package uk.co.joshuacross.photogallery.image;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CloudinaryImageServiceTest {

    @TestConfiguration
    static class CloudinaryImageServiceTestContextConfiguration {

        @Bean
        public ImageService imageService() {
            return new CloudinaryImageService();
        }
    }

    @Autowired
    private ImageService imageService;

    @MockBean
    private ImageRepository imageRepository;

    @Before
    public void setup() {
        ImageModel image = new ImageModel();
        image.setId("xphkdwc0kiar914yfvzq");

        Mockito.when(imageRepository.findAll(Arrays.asList(image.getId())))
                .thenReturn(Arrays.asList(image));
    }

    @Test
    public void whenValidID_thenImageShouldBeFound() {
        List<String> id = Arrays.asList("xphkdwc0kiar914yfvzq");
        List<ImageModel> image = imageService.getImages(id);

        assertThat(image.get(0).getId())
                .isEqualTo(id.get(0));
    }
}
