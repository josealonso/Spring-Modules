package info.josealonso.SSecurityPrimer.config;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// public class RobotFilter extends GenericFilterBean {
public class RobotFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        // 1. Authentication decision
        var password = request.getHeader("x-robot-password");
        if (!"beep-boop".equals(password)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-type", "text/plain;charset+utf-8");
            response.getWriter().println("==== You are not Mr Robot====");
            return;
        }

        var newContent = SecurityContextHolder.createEmptyContext();
        newContent.setAuthentication(new RobotAuthentication());
        SecurityContextHolder.setContext(newContent);
        filterChain.doFilter(request, response);
        return;

        // 2.- Do the rest

    }
}
