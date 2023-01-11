package common.commonbackend.security;

import common.commonbackend.users.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public AuthenticationProvider authProvider(@Qualifier("userService") UserDetailsService userDetailsService) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(UserService.getPasswordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .authorizeRequests()
                .antMatchers("/index.html", "/", "/home", "/login", "/createUserAndHouse", "/api/createUserAndHouse",
                        "/createUserForExistingHouse", "/api/createUserForExistingHouse", "/logout", "/api/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .logout(logout -> logout.logoutUrl("/api/logout"));

        http.csrf().disable(); //NOSONAR // TODO this is not recomended in prtoduction code. Check https://sonarcloud.io/organizations/hasunohana/rules?open=java%3AS4502&rule_key=java%3AS4502 why
    }
}
