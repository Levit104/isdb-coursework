package levit104.isdb.coursework.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ImageStorageException extends RuntimeException {
    public ImageStorageException(String message) {
        super(message);
    }
}
