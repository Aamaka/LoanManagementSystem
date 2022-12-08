package ezeirunne.chiamaka.loanmanagementsystem.security.config;

import ezeirunne.chiamaka.loanmanagementsystem.security.filter.LoanAuthenticationFilter;
import ezeirunne.chiamaka.loanmanagementsystem.security.filter.LoanAuthorizationFilter;
import ezeirunne.chiamaka.loanmanagementsystem.security.jwt.JwtUtil;
import ezeirunne.chiamaka.loanmanagementsystem.security.manager.LoanAuthenticationManager;
import lombok.AllArgsConstructor;
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
                .antMatchers(HttpMethod.POST,"/api/loan/user/registerAnAdmin/",
                            "/api/loan/user/registerACustomer/",
                        "/api/loan/user/login/")
                .permitAll()
                .antMatchers("/api/loan/user/applyForLoan/",
                        "/api/loan/user/findCustomer/",
                        "/api/loan/user/findLoan/",

                        "/api/loan/user/makePayment/",
                        "/api/loan/user/findPayment/"
                )
                .hasAnyAuthority("BORROWER")
                .and()
                .addFilter(filter)
                .addFilterBefore(new LoanAuthorizationFilter(), LoanAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().build();
    }

}
