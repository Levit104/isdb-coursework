package levit104.isdb.coursework.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static levit104.isdb.coursework.validation.ErrorMessages.NOT_BLANK;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString(callSuper = true)
public class Client extends Person {
//    @NotNull
    @NotBlank(message = NOT_BLANK)
    private String address;
}
