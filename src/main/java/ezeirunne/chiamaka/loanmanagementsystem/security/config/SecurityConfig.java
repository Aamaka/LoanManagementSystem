package ezeirunne.chiamaka.loanmanagementsystem.security.config;

import ezeirunne.chiamaka.loanmanagementsystem.security.filter.LoanAuthenticationFilter;
import ezeirunne.chiamaka.loanmanagementsystem.security.filter.LoanAuthorizationFilter;
import ezeirunne.chiamaka.loanmanagementsystem.security.jwt.JwtUtil;
import ezeirunne.chiamaka.loanmanagementsystem.security.manager.LoanAuthenticationManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final LoanAuthenticationManager loanAuthenticationManager;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter filter =
                new LoanAuthenticationFilter(loanAuthenticationManager, jwtUtil);
        filter.setFilterProcessesUrl("/api/loan/user/login/");
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/loan/users/register-an-admin/",
                        "/api/loan/users/register-a-customer/",
                        "/api/loan/users/login/")
                .permitAll()
                .antMatchers("/api/loan/users/apply-for-loan/",
                        "/api/loan/users/find-loan/",
                        "/api/loan/users/make-payment/",
                        "/api/loan/users/find-payment/"
                )
                .hasAuthority("BORROWER")
                .and()
                .authorizeRequests()
                .antMatchers("/api/loan/users/find-customer/")
                .hasAuthority("LENDER")
                .and()
                .addFilter(filter)
                .addFilterBefore(new LoanAuthorizationFilter(), LoanAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().build();

    }

}
