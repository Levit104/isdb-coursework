package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionPlansRepository extends JpaRepository<SubscriptionPlan, Integer> {
}
