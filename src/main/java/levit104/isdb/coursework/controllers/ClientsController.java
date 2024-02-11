package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Client;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static levit104.isdb.coursework.security.SecurityUtils.getAuthenticatedPerson;

@Controller
@RequestMapping("/clients")
public class ClientsController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/log-info")
    public String logInfo() {
        Client client = (Client) getAuthenticatedPerson();
        System.out.println(client);
        return "index";
    }
}
