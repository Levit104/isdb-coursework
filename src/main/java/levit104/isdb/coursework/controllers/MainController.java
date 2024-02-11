package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Client;
import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.models.Repairman;
import levit104.isdb.coursework.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/clients/index")
    public String indexClients() {
        return "clients/index";
    }

    @GetMapping("/repairmen/index")
    public String indexRepairmen() {
        return "repairmen/index";
    }

    private Person getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    @GetMapping("/clients/show_user_info")
    public String showClientInfo() {
        Client client = (Client) getAuthenticatedPerson();
        System.out.println(client);
        return "clients/index";
    }

    @GetMapping("/repairmen/show_user_info")
    public String showRepairmenInfo() {
        Repairman repairman = (Repairman) getAuthenticatedPerson();
        System.out.println(repairman);
        return "repairmen/index";
    }
}
