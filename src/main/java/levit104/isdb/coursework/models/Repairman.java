package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import levit104.isdb.coursework.models.order.Order;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "repairman")
@Getter
@Setter
@ToString(callSuper = true)
public class Repairman extends Person {
    private double rating;

    @NotNull(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private Byte experience;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String qualification;

    @ManyToMany
    @JoinTable(
            name = "schedule",
            joinColumns = @JoinColumn(name = "repairman_id"),
            inverseJoinColumns = @JoinColumn(name = "day_id")
    )
    private List<Day> days;

    @OneToMany(mappedBy = "repairman")
    private List<Order> orders;
}
