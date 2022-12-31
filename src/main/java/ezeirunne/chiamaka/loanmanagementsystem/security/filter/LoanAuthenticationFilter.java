package ezeirunne.chiamaka.loanmanagementsystem.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.security.jwt.JwtUtil;
import ezeirunne.chiamaka.loanmanagementsystem.security.manager.LoanAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RequiredArgsConstructor
public class LoanAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final LoanAuthenticationManager loanAuthenticationManager;

    private final JwtUtil jwt ;
    ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {
        logger.info("LoanAuthenticationFilter");
        User user;
        try {
            user = objectMapper.readValue(request.getReader(), User.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var authenticationToken = new
                UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        Authentication authenticatedToken =
                loanAuthenticationManager.authenticate(authenticationToken);
        if(authenticatedToken != null){
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticatedToken);
            return authenticatedToken;
        }
        throw  new BadCredentialsException("Invalid details");
    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String accessToken = jwt.generateAccessToken(userDetails);
        String refreshToken = jwt.generateRefreshTokens(userDetails);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("ACCESS TOKEN", accessToken);
        tokens.put("REFRESH TOKEN", refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), tokens);

    }
}
