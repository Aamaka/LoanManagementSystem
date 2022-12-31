package ezeirunne.chiamaka.loanmanagementsystem.security.provider;

import ezeirunne.chiamaka.loanmanagementsystem.util.LoanUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoanAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LoanUserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication provider => {}", authentication);
        UserDetails userDetails = userDetailService.loadUserByUsername((String) authentication.getPrincipal());
        if(userDetails != null){
            if(isMatches(authentication, userDetails)){
                return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(),
                        userDetails.getAuthorities());
            }
            throw new BadCredentialsException("Incorrect password");
        }
        throw new UsernameNotFoundException("Email not found");
    }

    private boolean isMatches(Authentication authentication, UserDetails userDetails) {
        return passwordEncoder.matches((String) authentication.getCredentials(), userDetails.getPassword());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        Class<UsernamePasswordAuthenticationToken> appAuthType = UsernamePasswordAuthenticationToken.class;
        return authentication.equals(appAuthType);
    }
}
