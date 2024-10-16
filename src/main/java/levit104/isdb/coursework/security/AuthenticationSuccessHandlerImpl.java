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
import java.util.HashMap;
import java.util.Map;

public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER_CLIENT", "/orders");
        roleTargetUrlMap.put("ROLE_USER_REPAIRMAN", "/available-orders");
        roleTargetUrlMap.put("ROLE_ADMIN", "/");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String url = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(roleTargetUrlMap::containsKey)
                .map(roleTargetUrlMap::get)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Ошибка авторизации"));

        SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler(url);
        successHandler.onAuthenticationSuccess(request, response, authentication);
    }
}
