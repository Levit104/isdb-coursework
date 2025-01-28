package levit104.isdb.coursework.services;

import levit104.isdb.coursework.exceptions.EntityNotFoundException;
import levit104.isdb.coursework.models.*;
import levit104.isdb.coursework.repos.FeedbackRepository;
import levit104.isdb.coursework.repos.PaymentTypesRepository;
import levit104.isdb.coursework.repos.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final PaymentTypesRepository paymentTypesRepository;
    private final FeedbackRepository feedbackRepository;

    public Order findById(Integer id) {
        return ordersRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Заказ с id=" + id + " не найден"));
    }

    public List<Order> findAllByClientIdAndActive(Integer clientId, boolean active) {
        return ordersRepository.findByClientIdAndActive(clientId, active);
    }

    public List<Order> findByRepairmanIdAndActive(Integer repairmanId, boolean active) {
        return ordersRepository.findByRepairmanIdAndActive(repairmanId, active);
    }

    public List<Order> findAllWithoutRepairman() {
        return ordersRepository.findAllByRepairmanIsNull();
    }

    public boolean existsByClientAndApplianceAndActive(Client client, Appliance appliance, boolean active) {
        return ordersRepository.existsByClientAndApplianceAndActive(client, appliance, active);
    }

    @Transactional
    public void create(Order order) {
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

    @Transactional
    public void takeOrder(Integer orderId, Repairman repairman) {
        Order order = findById(orderId);
        order.setRepairman(repairman);
        ordersRepository.save(order);
    }

    @Transactional
    public void finishOrder(Integer orderId) {
        Order order = findById(orderId);
        order.setActive(false);
        ordersRepository.save(order);
    }

    public List<Feedback> findAllFeedbacksByClientId(Integer clientId) {
        return feedbackRepository.findAllByOrder_Client_Id(clientId);
    }

    public List<Feedback> findAllFeedbacksByRepairmanId(Integer repairmanId) {
        return feedbackRepository.findAllByOrder_Repairman_Id(repairmanId);
    }

    @Transactional
    public void addFeedback(Integer orderId, Feedback feedback) {
        feedback.setPublishDate(LocalDate.now());
        feedbackRepository.save(feedback);

        Order order = findById(orderId);
        order.setFeedback(feedback);
        ordersRepository.save(order);
    }

    private int randomCost() {
        final int COST_START = 200;
        final int COST_END = 2000;
        return new Random().nextInt(COST_END - COST_START + 1) + COST_START;
    }
}
