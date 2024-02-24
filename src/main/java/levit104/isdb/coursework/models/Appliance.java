package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import levit104.isdb.coursework.models.order.Order;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "appliance")
@Getter
@Setter
public class Appliance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String name;

    @NotNull(message = ErrorMessages.EMPTY_VALUE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate purchaseDate;

    @NotNull(message = ErrorMessages.EMPTY_VALUE)
    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id", nullable = false)
    private ApplianceType type;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    private Client owner;

    @OneToMany(mappedBy = "appliance")
    private List<Order> orders;
}


