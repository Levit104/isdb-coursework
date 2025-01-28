package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Subscription;
import levit104.isdb.coursework.models.SubscriptionPlan;
import levit104.isdb.coursework.repos.SubscriptionPlansRepository;
import levit104.isdb.coursework.repos.SubscriptionsRepository;
import levit104.isdb.coursework.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionPlansRepository subscriptionPlansRepository;

    @Override
    public List<SubscriptionPlan> getAllPlans() {
        return subscriptionPlansRepository.findAll();
    }

    @Override
    public List<SubscriptionPlan> getAvailablePlans(Client client) {
        List<SubscriptionPlan> clientPlans = client.getSubscriptions().stream()
                .map(Subscription::getSubscriptionPlan)
                .toList();
        return getAllPlans().stream()
                .filter(plan -> !clientPlans.contains(plan))
                .toList();
    }

    @Override
    public SubscriptionPlan getPlanById(Integer id) {
        return subscriptionPlansRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тарифный план с id=" + id + " не найден"));

    }

    @Override
    public List<Subscription> getAllByClientId(Integer clientId) {
        return subscriptionsRepository.findAllByClientId(clientId);
    }

    @Override
    @Transactional
    public void subscribe(Client client, Integer planId, Integer duration) {
        LocalDate currentDate = LocalDate.now();

        Subscription subscription = new Subscription(
                currentDate,
                currentDate.plusMonths(duration),
                client,
                getPlanById(planId)
        );

        subscriptionsRepository.save(subscription);
    }

    @Override
    @Transactional
    public void unsubscribe(Integer id) {
        subscriptionsRepository.deleteById(id);
    }
}
