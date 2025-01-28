package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.*;

import java.util.List;

public interface OrderService {
    Order getById(Integer id);

    List<Order> getAllByClientIdAndActive(Integer clientId, boolean active);

    List<Order> getAllByRepairmanIdAndActive(Integer repairmanId, boolean active);

    List<Order> getAllWithoutRepairman();

    boolean existsByClientAndApplianceAndActive(Client client, Appliance appliance, boolean active);

    void create(Order order);

    void save(Order order);

    void takeOrder(Integer orderId, Repairman repairman);

    void finishOrder(Integer orderId);
}
