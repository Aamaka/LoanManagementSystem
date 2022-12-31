package ezeirunne.chiamaka.loanmanagementsystem.util;

import ezeirunne.chiamaka.loanmanagementsystem.data.models.User;
import ezeirunne.chiamaka.loanmanagementsystem.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoanUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("UserDetailsService => {}", username);
        User user = userService.getUserByUserName(username);
        return new SecureUser(user);
    }
}
