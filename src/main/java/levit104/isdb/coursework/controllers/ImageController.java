package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.services.ImageService;
import levit104.isdb.coursework.util.ImageContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) {
        ImageContainer image = imageService.load(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(image.resource());
    }

    @DeleteMapping("/{fileName}")
    public void delete(@PathVariable String fileName) {
        imageService.delete(fileName);
    }

}
