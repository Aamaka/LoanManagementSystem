package ezeirunne.chiamaka.loanmanagementsystem.security.provider;

import ezeirunne.chiamaka.loanmanagementsystem.util.LoanUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoanAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoanUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = userDetailService.loadUserByUsername((String) authentication.getPrincipal());
        if(userDetails != null){
            if(passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword())){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), authentication.getAuthorities());
            }
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
