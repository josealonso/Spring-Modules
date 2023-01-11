package info.josealonso.SSecurityPrimer.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

// public class SecurityConfig extends WebSecurityConfigurerAdapter {   // deprecated

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationEventPublisher publisher
    ) throws Exception {

        var configurer = new RobotLoginConfigurer()
                .password("beep-boop")
                .password("beep-beep");

        return http
                .authorizeRequests(authorizeConfig -> {
                    authorizeConfig.antMatchers("/").permitAll();
                    authorizeConfig.antMatchers("/error").permitAll();
                    authorizeConfig.antMatchers("/favicon.ico").permitAll();
                    authorizeConfig.anyRequest().authenticated();
                })
                .formLogin(withDefaults())
                .oauth2Login(
                        oAuth2Configurer -> {
                            oAuth2Configurer.withObjectPostProcessor(
                                    new ObjectPostProcessor<AuthenticationProvider>() {
                                        @Override
                                        public <O extends AuthenticationProvider> O postProcess(O object) {
                                            return (O) new RateLimitedAuthenticationProvider(object);
                                        }
                                    }
                            );
                        }
                )
                .apply(configurer).and()
                .authenticationProvider(new JoseAuthenticationProvider())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("user")
                        .password("{noop}passwd")
                        .authorities("ROLE_user")
                        .build()
        );
    }

    @Bean
    public ApplicationListener<AuthenticationSuccessEvent> successListener() {
        return event -> {
            System.out.println(
                    String.format("\n SUCCESS [%s] %s",
                            event.getAuthentication().getClass().getSimpleName(),
                            event.getAuthentication().getName()
                    )
            );
        };
    }
}
