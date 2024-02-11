package levit104.isdb.coursework.controllers;

import levit104.isdb.coursework.models.Repairman;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static levit104.isdb.coursework.security.SecurityUtils.getAuthenticatedPerson;

@Controller
@RequestMapping("/repairmen")
public class RepairmenController {
    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/log-info")
    public String logInfo() {
        Repairman repairman = (Repairman) getAuthenticatedPerson();
        System.out.println(repairman);
        return "index";
    }
}
