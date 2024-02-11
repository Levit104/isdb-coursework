package levit104.isdb.coursework.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import static levit104.isdb.coursework.validation.ErrorMessages.NOT_BLANK;

@Entity
@Table(name = "repairman")
@Getter
@Setter
@ToString(callSuper = true)
public class Repairman extends Person {

    private double rating;
    @NotNull(message = NOT_BLANK)
    private Byte experience;

    @NotBlank(message = NOT_BLANK)
    private String qualification;
}
