package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subscription_plan")
@Getter
@Setter
public class SubscriptionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @OneToMany(mappedBy = "subscriptionPlan")
    private List<ApplianceType> applianceTypes;

    @OneToMany(mappedBy = "subscriptionPlan")
    private List<Subscription> subscriptions;
}
