package levit104.isdb.coursework.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import levit104.isdb.coursework.validation.ErrorMessages;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
public class Client extends Person {
    @NotBlank(message = ErrorMessages.EMPTY_VALUE)
    @Column(nullable = false)
    private String address;

    // TODO каскадирование
    @OneToMany(mappedBy = "owner")
    private List<Appliance> appliances;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    @OneToMany(mappedBy = "client")
    private List<Subscription> subscriptions;
}
