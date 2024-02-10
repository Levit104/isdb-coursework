package levit104.isdb.coursework.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import static levit104.isdb.coursework.validation.ErrorMessages.NOT_BLANK;

//@Entity
//@Table(name = "repairman")
public class Repairman {

    private double rating;
//    @NotNull(message = NOT_BLANK)
    private byte experience;

//    @NotBlank(message = NOT_BLANK)
    private String qualification;
}
