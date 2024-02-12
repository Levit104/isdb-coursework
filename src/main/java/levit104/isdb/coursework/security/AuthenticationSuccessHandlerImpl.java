package levit104.isdb.coursework.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

import static levit104.isdb.coursework.security.SecurityUtils.ROLE_USER_CLIENT;
import static levit104.isdb.coursework.security.SecurityUtils.getAuthenticatedPerson;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("ABOBA_SUCCESS_LOGIN");

        int personId = getAuthenticatedPerson(authentication).getId();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String url = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals(ROLE_USER_CLIENT)) ? "/clients/" : "/repairmen/";

        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler(url + personId);

        successHandler.onAuthenticationSuccess(request, response, authentication);
    }
}





