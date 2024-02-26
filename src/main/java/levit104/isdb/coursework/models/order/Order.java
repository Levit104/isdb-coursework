package levit104.isdb.coursework.models.order;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String description;

    @NotNull(message = ErrorMessages.EMPTY_VALUE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "appliance_id", referencedColumnName = "id", nullable = false)
    private Appliance appliance;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "repairman_id", referencedColumnName = "id")
    private Repairman repairman;

    @ManyToOne
    @JoinColumn(name = "payment_type_id", referencedColumnName = "id")
    private PaymentType paymentType;

    @ManyToOne
    @JoinColumn(name = "order_status_id", referencedColumnName = "id", nullable = false)
    private OrderStatus orderStatus;
}
