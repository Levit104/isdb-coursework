package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.security.PersonDetails;
import levit104.isdb.coursework.services.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/available-feedback")
@RequiredArgsConstructor
public class RepairmenFeedbackController {
    private final FeedbackService feedbackService;

    @GetMapping
    public String showAll(@AuthenticationPrincipal PersonDetails personDetails, Model model) {
        model.addAttribute("feedbacks", feedbackService.getAllByRepairmanId(personDetails.getId()));
        return "available-feedback/index";
    }
}
