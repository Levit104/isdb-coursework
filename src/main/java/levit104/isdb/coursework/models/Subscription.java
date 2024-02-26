package levit104.isdb.coursework.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "subscription")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = false)
    private SubscriptionPlan subscriptionPlan;

    public Subscription(LocalDate startDate, LocalDate endDate, Client client, SubscriptionPlan subscriptionPlan) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.client = client;
        this.subscriptionPlan = subscriptionPlan;
    }
}
