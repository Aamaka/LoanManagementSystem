package ezeirunne.chiamaka.loanmanagementsystem.security.manager;

import ezeirunne.chiamaka.loanmanagementsystem.security.provider.LoanAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoanAuthenticationManager implements AuthenticationManager {

    private final LoanAuthenticationProvider loanAuthenticationProvider;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return loanAuthenticationProvider.authenticate(authentication);
    }
}
