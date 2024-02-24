package levit104.isdb.coursework.services;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.models.order.Order;
import levit104.isdb.coursework.models.order.OrderStatus;
import levit104.isdb.coursework.repos.order.OrdersRepository;
import levit104.isdb.coursework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ClientsService clientsService;
    private final RepairmenService repairmenService;

    public List<Order> findAllByClientId(Integer id) {
        return ordersRepository.findAllByClientId(id);
    }

    @Transactional
    public void save(Order order, Integer clientId, Integer repairmanId) {
        int cost = randomCost();
        Client client = clientsService.findById(clientId);
        Repairman repairman = (repairmanId != null) ? repairmenService.findById(repairmanId) : null;

        order.setCost(cost);
        order.setClient(client);
        order.setRepairman(repairman);
        ordersRepository.save(order);
    }

    private int randomCost() {
        final int COST_START = 200;
        final int COST_END = 2000;
        return new Random().nextInt(COST_END - COST_START + 1) + COST_START;
    }
}
