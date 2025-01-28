package levit104.isdb.coursework.services.impl;

import levit104.isdb.coursework.models.Feedback;
import levit104.isdb.coursework.models.Order;
import levit104.isdb.coursework.repos.FeedbackRepository;
import levit104.isdb.coursework.services.FeedbackService;
import levit104.isdb.coursework.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final OrderService orderService;

    @Override
    public List<Feedback> getAllByClientId(Integer clientId) {
        return feedbackRepository.findAllByOrder_Client_Id(clientId);
    }

    @Override
    public List<Feedback> getAllByRepairmanId(Integer repairmanId) {
        return feedbackRepository.findAllByOrder_Repairman_Id(repairmanId);
    }

    @Override
    @Transactional
    public void add(Integer orderId, Feedback feedback) {
        feedback.setPublishDate(LocalDate.now());
        feedbackRepository.save(feedback);

        Order order = orderService.getById(orderId);
        order.setFeedback(feedback);
        orderService.save(order);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        feedbackRepository.findById(id).ifPresent(feedback -> feedback.getOrder().setFeedback(null));
        feedbackRepository.deleteById(id);
    }
}
