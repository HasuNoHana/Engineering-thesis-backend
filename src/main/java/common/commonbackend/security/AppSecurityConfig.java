package common.commonbackend.security;

import common.commonbackend.users.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); // TODO read how this could be more specific
        configuration.setAllowedMethods(List.of("GET","POST","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/hello", "/index.html", "/", "/home", "/login", "/createUserAndHouse", "/api/createUserAndHouse",
                        "/createUserForExistingHouse", "/api/createUserForExistingHouse", "/logout", "/api/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .logout(logout -> logout.logoutUrl("/api/logout"));


        http.csrf().disable(); //NOSONAR this is not recomended in prtoduction code. Check https://sonarcloud.io/organizations/hasunohana/rules?open=java%3AS4502&rule_key=java%3AS4502 why
    }
}
