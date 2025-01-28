package levit104.isdb.coursework.controllers;

import jakarta.validation.Valid;
import levit104.isdb.coursework.models.Feedback;
import levit104.isdb.coursework.services.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final OrdersService ordersService;

    @GetMapping("/new")
    public String addForm(@RequestParam(value = "orderId", required = false) Integer orderId,
                          @ModelAttribute("feedback") Feedback feedback,
                          Model model) {
        model.addAttribute("orderId", orderId);
        return "feedback/new";
    }

    @PostMapping
    public String add(@RequestParam(value = "orderId", required = false) Integer orderId,
                      @ModelAttribute("feedback") @Valid Feedback feedback,
                      BindingResult bindingResult,
                      Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("orderId", orderId);
            return "feedback/new";
        }

        ordersService.addFeedback(orderId, feedback);
        return "redirect:/orders";
    }

}
