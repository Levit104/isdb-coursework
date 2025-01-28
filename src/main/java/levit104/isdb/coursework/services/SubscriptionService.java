package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Subscription;
import levit104.isdb.coursework.models.SubscriptionPlan;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionPlan> getAllPlans();

    List<SubscriptionPlan> getAvailablePlans(Client client);

    SubscriptionPlan getPlanById(Integer id);

    List<Subscription> getAllByClientId(Integer clientId);

    void subscribe(Client client, Integer planId, Integer duration);

    void unsubscribe(Integer id);
}
