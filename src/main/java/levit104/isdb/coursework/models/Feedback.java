package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feedback")
@Getter
@Setter
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // может быть null
    private String description;

    @NotNull(message = ErrorMessages.EMPTY_VALUE)
//    @Size(min = 1, max = 5, message = "Оценка от 1 до 5")
    @Column(nullable = false)
    private Double rating;

    @Column(nullable = false)
    private LocalDate publishDate;

    @OneToOne(mappedBy = "feedback")
    private Order order;
}
