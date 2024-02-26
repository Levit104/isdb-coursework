package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Integer> {
    List<Subscription> findAllByClientId(Integer id);
}
