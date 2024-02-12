package levit104.isdb.coursework.security;

import levit104.isdb.coursework.models.Person;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static final String ROLE_USER_CLIENT = "ROLE_USER_CLIENT";
    public static final String ROLE_USER_REPAIRMAN = "ROLE_USER_REPAIRMAN";

    public static Person getAuthenticatedPerson() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getAuthenticatedPerson(authentication);
    }

    public static Person getAuthenticatedPerson(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }
}
