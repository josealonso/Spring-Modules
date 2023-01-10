package info.josealonso.SSecurityPrimer.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

public class RobotLoginConfigurer extends AbstractHttpConfigurer<RobotLoginConfigurer, HttpSecurity> {

    private final List<String> passwords = new ArrayList<>();

    @Override
    public void init(HttpSecurity http) throws Exception {
        // Step 1
        // Initialize a bunch of objects
        // -> AuthenticationProviders
        http.authenticationProvider(new RobotAuthenticationProvider(passwords));
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Step 2
        // It also initializes a bunch of objects, but can reuse objects from step 1, even from other configurers
        // -> Filters
        var authenticationManager = http.getSharedObject(AuthenticationManager.class);
        http.addFilterBefore(new RobotFilter(authenticationManager), FilterSecurityInterceptor.class);
    }

    public RobotLoginConfigurer password(String password) {
        this.passwords.add(password);
        return this;
    }
}
