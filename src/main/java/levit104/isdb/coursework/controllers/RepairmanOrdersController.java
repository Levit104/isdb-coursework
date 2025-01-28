package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.OrdersService;
import levit104.isdb.coursework.services.RepairmenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/available-orders")
@RequiredArgsConstructor
public class RepairmanOrdersController {
    private final OrdersService ordersService;
    private final RepairmenService repairmenService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Integer repairmanId = personDetails.getId();
        model.addAttribute("activeOrders", ordersService.findByRepairmanIdAndActive(repairmanId, true));
        model.addAttribute("finishedOrders", ordersService.findByRepairmanIdAndActive(repairmanId, false));
        model.addAttribute("nonTakenOrders", ordersService.findAllWithoutRepairman());
        return "available-orders/index";
    }

    @PatchMapping("/take")
    public String takeOrder(@AuthenticationPrincipal PersonDetails personDetails,
                            @RequestParam("orderId") Integer orderId,
                            Model model) {
        Integer repairmanId = personDetails.getId();

        Repairman repairman = repairmenService.findById(repairmanId);
        if (repairman.getDays().isEmpty()) {
            model.addAttribute("emptySchedule", "Вы не можете принимать заказы, пока не создадите рабочий график");
            model.addAttribute("activeOrders", ordersService.findByRepairmanIdAndActive(repairmanId, true));
            model.addAttribute("finishedOrders", ordersService.findByRepairmanIdAndActive(repairmanId, false));
            model.addAttribute("nonTakenOrders", ordersService.findAllWithoutRepairman());
            return "available-orders/index";
        }

        ordersService.takeOrder(orderId, repairman);

        return "redirect:/available-orders";
    }

    @PatchMapping("/finish")
    public String finishOrder(@RequestParam("orderId") Integer orderId) {
        ordersService.finishOrder(orderId);
        return "redirect:/available-orders";
    }
}
