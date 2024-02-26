package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Order;
import levit104.isdb.coursework.repos.PaymentTypesRepository;
import levit104.isdb.coursework.repos.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final PaymentTypesRepository paymentTypesRepository;

    public List<Order> findAllByClientId(Integer id) {
        return ordersRepository.findAllByClientId(id);
    }

    public Optional<Order> findByClientAndAppliance(Client client, Appliance appliance) {
        return ordersRepository.findByClientAndAppliance(client, appliance);
    }

    @Transactional
    public void save(Order order) {
        boolean subscribed = order.getClient().getSubscriptions()
                .stream()
                .anyMatch(s -> s.getSubscriptionPlan().equals(order.getAppliance().getType().getSubscriptionPlan()));

//        FIXME заменить null???
        if (subscribed)
            order.setPaymentType(paymentTypesRepository.findByName("Подписка").orElse(null));
        else
            order.setPaymentType(paymentTypesRepository.findByName("Безналичная").orElse(null));

        int cost = randomCost();
        order.setCost(cost);

        ordersRepository.save(order);
    }

    private int randomCost() {
        final int COST_START = 200;
        final int COST_END = 2000;
        return new Random().nextInt(COST_END - COST_START + 1) + COST_START;
    }
}
