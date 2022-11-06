package ezeirunne.chiamaka.loanmanagementsystem.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.Customer;
import ezeirunne.chiamaka.loanmanagementsystem.security.manager.LoanAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class LoanAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    ObjectMapper objectMapper;
    private final LoanAuthenticationManager loanAuthenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Customer user;
        try {
            user = objectMapper.readValue(request.getReader(), Customer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication authenticatedToken =
                loanAuthenticationManager.authenticate(authenticationToken);
        if(authenticatedToken != null){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticationToken);
            return authenticatedToken;
        }
        throw  new BadCredentialsException("Invalid details");
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }
}