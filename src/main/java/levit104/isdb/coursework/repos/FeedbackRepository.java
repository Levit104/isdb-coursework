package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByOrder_Client_Id(Integer clientId);
    List<Feedback> findAllByOrder_Repairman_Id(Integer repairmanId);
}
