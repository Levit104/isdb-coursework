package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.Subscription;
import levit104.isdb.coursework.models.SubscriptionPlan;
import levit104.isdb.coursework.repos.SubscriptionPlansRepository;
import levit104.isdb.coursework.repos.SubscriptionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubscriptionsService {
    private final SubscriptionsRepository subscriptionsRepository;
    private final SubscriptionPlansRepository subscriptionPlansRepository;
    private final ClientsService clientsService;

    public List<SubscriptionPlan> findAllPlans() {
        return subscriptionPlansRepository.findAll();
    }

    public SubscriptionPlan findPlanById(Integer id) {
        return subscriptionPlansRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Тарифный план с id=" + id + " не найден"));

    }

    public List<Subscription> findAllByClientId(Integer clientId) {
        return subscriptionsRepository.findAllByClientId(clientId);
    }

    @Transactional
    public void subscribe(Integer duration, Integer clientId, Integer planId) {
        LocalDate currentDate = LocalDate.now();

        Subscription subscription = new Subscription(
                currentDate,
                currentDate.plusMonths(duration),
                clientsService.findById(clientId),
                findPlanById(planId)
        );

        subscriptionsRepository.save(subscription);
    }
}
