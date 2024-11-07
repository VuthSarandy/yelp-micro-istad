package kh.edu.cstad.idenity.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean // Bean of oauth2 firewall
    @Order(1)
        // Order of the filter chain oauth2SecurityFilterChain
    SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {

        // Apply default Oauth2 rules
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        //authentication with OIDC (OpenID Connect)
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );
//        http.authorizeHttpRequests((authorize) -> authorize
//                .requestMatchers("oauth2/token").permitAll()
//                .anyRequest().authenticated()
//        );

        http.csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean // Bean of web firewall
    @Order(2)
        // Order of the filter chain webSecurityFilterChain
    SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(endpoint -> endpoint
                        .anyRequest()
                        .authenticated());
        http.formLogin(Customizer.withDefaults());
        return http.build();
    }
}
