package ezeirunne.chiamaka.loanmanagementsystem.security.manager;

import ezeirunne.chiamaka.loanmanagementsystem.security.provider.LoanAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class LoanAuthenticationManager implements AuthenticationManager {

    private final LoanAuthenticationProvider loanAuthenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("authentication manager => {}", authentication);
        return loanAuthenticationProvider.authenticate(authentication);
    }
}
