package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.order.Order;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.AppliancesService;
import levit104.isdb.coursework.services.OrdersService;
import levit104.isdb.coursework.services.RepairmenService;
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
    private final OrdersService ordersService;
    private final AppliancesService appliancesService;
    private final RepairmenService repairmenService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("orders", ordersService.findAllByClientId(personDetails.getId()));
        return "orders/index";
    }

    @GetMapping("/new")
    public String createForm(@AuthenticationPrincipal PersonDetails personDetails,
                             @RequestParam(value = "repairmanId", required = false) Integer repairmanId,
                             @ModelAttribute("order") Order order, Model model) {
        model.addAttribute("repairmanId", repairmanId);
        model.addAttribute("appliances", appliancesService.findAllByOwnerId(personDetails.getId()));
        return "orders/new";
    }

    @PostMapping
    public String create(@AuthenticationPrincipal PersonDetails personDetails,
                         @RequestParam(value = "repairmanId", required = false) Integer repairmanId,
                         @ModelAttribute("order") @Valid Order order,
                         BindingResult bindingResult,
                         Model model) {
        Integer clientId = personDetails.getId();
        order.setRepairman(repairmenService.findByIdForOrder(repairmanId));
        orderValidator.validate(order, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("repairmanId", repairmanId);
            model.addAttribute("appliances", appliancesService.findAllByOwnerId(clientId));
            return "orders/new";
        }

        ordersService.save(order, clientId);
        return "redirect:/orders";
    }
}
