package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "appliance_type")
@Getter
@Setter
public class ApplianceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Appliance> appliances;
}
