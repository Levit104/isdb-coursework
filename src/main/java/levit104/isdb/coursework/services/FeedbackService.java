package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getAllByClientId(Integer clientId);

    List<Feedback> getAllByRepairmanId(Integer repairmanId);

    void add(Integer orderId, Feedback feedback);

    void deleteById(Integer id);
}
