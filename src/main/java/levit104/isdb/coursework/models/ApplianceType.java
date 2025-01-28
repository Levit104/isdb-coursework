package levit104.isdb.coursework.models;

import jakarta.persistence.*;
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

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Appliance> appliances;

    @ManyToOne
    @JoinColumn(name = "subscription_plan_id", referencedColumnName = "id", nullable = false)
    private SubscriptionPlan subscriptionPlan;
}
