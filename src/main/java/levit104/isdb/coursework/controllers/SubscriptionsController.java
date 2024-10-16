package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.ClientsService;
import levit104.isdb.coursework.services.SubscriptionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// TODO Возможность продлить подписку, если срок истек
// TODO Возможность отменить подписку
@Controller
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionsController {
    private final SubscriptionsService subscriptionsService;
    private final ClientsService clientsService;

    @GetMapping
    public String showActive(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("activeSubscriptions", subscriptionsService.findAllByClientId(personDetails.getId()));
        return "subscriptions/index";
    }

    @GetMapping("/new")
    public String showPlans(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        Client client = clientsService.findById(personDetails.getId());
//        model.addAttribute("subscriptionPlans", subscriptionsService.findAllPlans());
        model.addAttribute("subscriptionPlans", subscriptionsService.getAvailablePlans(client));
        return "subscriptions/new";
    }

    @PostMapping
    public String subscribe(@AuthenticationPrincipal PersonDetails personDetails,
                            @RequestParam("planId") Integer planId,
                            @RequestParam("duration") Integer duration) {
        Client client = clientsService.findById(personDetails.getId());
        subscriptionsService.subscribe(client, planId, duration);
        return "redirect:/subscriptions";
    }
}
