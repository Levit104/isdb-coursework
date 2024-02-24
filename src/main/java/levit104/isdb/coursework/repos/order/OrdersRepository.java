package levit104.isdb.coursework.repos.order;

import levit104.isdb.coursework.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByClientId(Integer clientId);
}
