package levit104.isdb.coursework.services;

import levit104.isdb.coursework.util.ImageContainer;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String generateImageUrl(String fileName);

    String save(MultipartFile file);

    ImageContainer load(String fileName);

    void delete(String fileName);
}