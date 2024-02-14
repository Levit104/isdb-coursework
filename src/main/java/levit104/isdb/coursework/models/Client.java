package levit104.isdb.coursework.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString(callSuper = true)
public class Client extends Person {
    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    private String address;
}
