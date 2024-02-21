package levit104.isdb.coursework.util;

import levit104.isdb.coursework.models.Person;
import levit104.isdb.coursework.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static final String ROLE_USER_CLIENT = "ROLE_USER_CLIENT";
    public static final String ROLE_USER_REPAIRMAN = "ROLE_USER_REPAIRMAN";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    public static Person getAuthenticatedPerson() {
        Authentication authentication = getAuthentication();
        return getAuthenticatedPerson(authentication);
    }

    public static Person getAuthenticatedPerson(Authentication authentication) {
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
        return personDetails.getPerson();
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
