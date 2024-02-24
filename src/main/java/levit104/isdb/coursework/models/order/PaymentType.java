package levit104.isdb.coursework.models.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "payment_type")
@Getter
@Setter
public class PaymentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "paymentType")
    private List<Order> orders;
}
