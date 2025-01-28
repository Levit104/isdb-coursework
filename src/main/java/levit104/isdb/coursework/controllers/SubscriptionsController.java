package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.ClientService;
import levit104.isdb.coursework.services.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {
    private final SubscriptionService subscriptionService;
    private final ClientService clientService;

    @GetMapping
    public String showActive(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("activeSubscriptions", subscriptionService.getAllByClientId(personDetails.getId()));
        return "subscriptions/index";
    }

    @GetMapping("/new")
    public String showPlans(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Client client = clientService.getById(personDetails.getId());
//        model.addAttribute("subscriptionPlans", subscriptionService.getAllPlans());
        model.addAttribute("subscriptionPlans", subscriptionService.getAvailablePlans(client));
        return "subscriptions/new";
    }

    @PostMapping
    public String subscribe(@AuthenticationPrincipal PersonDetails personDetails,
                            @RequestParam("planId") Integer planId,
                            @RequestParam("duration") Integer duration) {
        Client client = clientService.getById(personDetails.getId());
        subscriptionService.subscribe(client, planId, duration);
        return "redirect:/subscriptions";
    }

    @DeleteMapping("/{id}")
    public String unsubscribe(@PathVariable Integer id) {
        subscriptionService.unsubscribe(id);
        return "redirect:/subscriptions";
    }
}
