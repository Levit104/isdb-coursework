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

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        int personId = SecurityUtils.getAuthenticatedPerson(authentication).getId();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String url = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals(SecurityUtils.ROLE_USER_CLIENT)) ? "/clients/" : "/repairmen/";

        SimpleUrlAuthenticationSuccessHandler successHandler =
                new SimpleUrlAuthenticationSuccessHandler(url + personId);

        successHandler.onAuthenticationSuccess(request, response, authentication);
    }
}





