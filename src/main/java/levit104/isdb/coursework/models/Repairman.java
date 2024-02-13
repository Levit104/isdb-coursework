package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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

    @ManyToMany
    @JoinTable(
            name = "schedule",
            joinColumns = @JoinColumn(name = "repairman_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id")
    )
    private List<Day> days;
}
