package levit104.isdb.coursework.util;

import org.springframework.core.io.Resource;

public record ImageContainer(
        String contentType,
        Resource resource
) {
}
