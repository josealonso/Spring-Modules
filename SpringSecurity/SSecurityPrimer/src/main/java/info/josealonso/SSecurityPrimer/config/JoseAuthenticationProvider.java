package info.josealonso.SSecurityPrimer.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class JoseAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var username = authentication.getName();
        if ("jose".equals(username)) {
            return UsernamePasswordAuthenticationToken.authenticated(
                    "jose",
                    null,
                    AuthorityUtils.createAuthorityList("ROLE_admin")
            );
        }
        return null;  // This means we delegate the request
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
