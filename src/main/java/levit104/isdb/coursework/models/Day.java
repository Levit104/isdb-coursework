package levit104.isdb.coursework.models;

import jakarta.persistence.*;
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
    private Integer id;

    @Column(unique = true, nullable = false, length = 12)
    private String fullName;

    @Column(unique = true, nullable = false, length = 2)
    private String shortName;

    @ManyToMany(mappedBy = "days")
    private List<Repairman> repairmen;
}
