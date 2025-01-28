package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.*;
import levit104.isdb.coursework.repos.PaymentTypesRepository;
import levit104.isdb.coursework.repos.OrdersRepository;
import levit104.isdb.coursework.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrdersRepository ordersRepository;
    private final PaymentTypesRepository paymentTypesRepository;

    @Override
    public Order getById(Integer id) {
        return ordersRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id=" + id + " не найден"));
    }

    @Override
    public List<Order> getAllByClientIdAndActive(Integer clientId, boolean active) {
        return ordersRepository.findByClientIdAndActive(clientId, active);
    }

    @Override
    public List<Order> getAllByRepairmanIdAndActive(Integer repairmanId, boolean active) {
        return ordersRepository.findByRepairmanIdAndActive(repairmanId, active);
    }

    @Override
    public List<Order> getAllWithoutRepairman() {
        return ordersRepository.findAllByRepairmanIsNull();
    }

    @Override
    public boolean existsByClientAndApplianceAndActive(Client client, Appliance appliance, boolean active) {
        return ordersRepository.existsByClientAndApplianceAndActive(client, appliance, active);
    }

    @Override
    @Transactional
    public void create(Order order) {
        boolean subscribed = order.getClient().getSubscriptions()
                .stream()
                .anyMatch(s -> s.getSubscriptionPlan().equals(order.getAppliance().getType().getSubscriptionPlan()));

        if (subscribed)
            order.setPaymentType(paymentTypesRepository.findByName("Подписка").orElse(null));
        else
            order.setPaymentType(paymentTypesRepository.findByName("Безналичная").orElse(null));

        int cost = randomCost();
        order.setCost(cost);

        save(order);
    }

    @Override
    public void save(Order order) {
        ordersRepository.save(order);
    }

    @Override
    @Transactional
    public void takeOrder(Integer orderId, Repairman repairman) {
        Order order = getById(orderId);
        order.setRepairman(repairman);
        save(order);
    }

    @Override
    @Transactional
    public void finishOrder(Integer orderId) {
        Order order = getById(orderId);
        order.setActive(false);
        save(order);
    }

    private int randomCost() {
        final int COST_START = 200;
        final int COST_END = 2000;
        return new Random().nextInt(COST_END - COST_START + 1) + COST_START;
    }
}
