package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Order;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.ApplianceService;
import levit104.isdb.coursework.services.ClientService;
import levit104.isdb.coursework.services.OrderService;
import levit104.isdb.coursework.services.RepairmanService;
import levit104.isdb.coursework.validation.OrderValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrdersController {
    private final OrderValidator orderValidator;
    private final OrderService orderService;
    private final ApplianceService applianceService;
    private final ClientService clientService;
    private final RepairmanService repairmanService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Integer clientId = personDetails.getId();
        model.addAttribute("activeOrders", orderService.getAllByClientIdAndActive(clientId, true));
        model.addAttribute("finishedOrders", orderService.getAllByClientIdAndActive(clientId, false));
        return "orders/index";
    }

    @GetMapping("/new")
    public String createForm(@AuthenticationPrincipal PersonDetails personDetails,
                             @RequestParam(value = "repairmanId", required = false) Integer repairmanId,
                             @ModelAttribute("order") Order order,
                             Model model) {
        model.addAttribute("repairmanId", repairmanId);
        model.addAttribute("appliances", applianceService.getAllByOwnerId(personDetails.getId()));
        return "orders/new";
    }

    @PostMapping
    public String create(@AuthenticationPrincipal PersonDetails personDetails,
                         @RequestParam(value = "repairmanId", required = false) Integer repairmanId,
                         @ModelAttribute("order") @Valid Order order,
                         BindingResult bindingResult,
                         Model model) {
        Integer clientId = personDetails.getId();
        order.setClient(clientService.getById(clientId));
        order.setRepairman(repairmanService.getByIdForOrder(repairmanId));
        orderValidator.validate(order, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("repairmanId", repairmanId);
            model.addAttribute("appliances", applianceService.getAllByOwnerId(clientId));
            return "orders/new";
        }

        orderService.create(order);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}")
    public String cancel(@PathVariable("id") Integer id) {
        orderService.finishOrder(id);
        return "redirect:/orders";
    }
}