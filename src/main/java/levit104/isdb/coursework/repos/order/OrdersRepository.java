package levit104.isdb.coursework.repos.order;

import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByClientId(Integer clientId);
    Optional<Order> findByClientAndAppliance(Client client, Appliance appliance);
}
