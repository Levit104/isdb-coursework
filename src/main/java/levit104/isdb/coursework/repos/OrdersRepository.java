package levit104.isdb.coursework.repos;

import levit104.isdb.coursework.models.Appliance;
import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClientIdAndActive(Integer repairmanId, boolean active);

    List<Order> findByRepairmanIdAndActive(Integer repairmanId, boolean active);

    List<Order> findAllByRepairmanIsNull();

    boolean existsByClientAndApplianceAndActive(Client client, Appliance appliance, boolean active);
}
