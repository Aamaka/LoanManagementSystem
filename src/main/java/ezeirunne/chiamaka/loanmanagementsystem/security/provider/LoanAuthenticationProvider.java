package ezeirunne.chiamaka.loanmanagementsystem.security.provider;

import ezeirunne.chiamaka.loanmanagementsystem.util.LoanUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
