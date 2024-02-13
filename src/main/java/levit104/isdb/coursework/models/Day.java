
package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "day")
@Getter
@Setter
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(unique = true, nullable = false, length = 12)
    private String fullName;

    @NotBlank
    @Size(min = 2, max = 2)
    @Column(unique = true, nullable = false, length = 2)
    private String shortName;

    @ManyToMany(mappedBy = "days")
    private List<Repairman> repairmen;
}
