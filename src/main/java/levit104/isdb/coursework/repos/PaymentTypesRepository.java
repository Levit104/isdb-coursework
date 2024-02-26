package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.order.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTypesRepository extends JpaRepository<PaymentType, Integer> {
    Optional<PaymentType> findByName(String name);
}
