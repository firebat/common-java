package common.security.filter;

import common.security.AuthorizationService;
import common.security.config.AuthorizationConfig;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationTokenFilter extends OncePerRequestFilter {

    private AuthorizationConfig authorizationConfig;

    private UserDetailsService userDetailsService;

    private AuthorizationService authService;

    public AuthorizationTokenFilter(AuthorizationConfig authorizationConfig, UserDetailsService userDetailsService, AuthorizationService authorizationService) {
        this.authorizationConfig = authorizationConfig;
        this.userDetailsService = userDetailsService;
        this.authService = authorizationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(authorizationConfig.getTokenHeader());

        if (authorization != null && authorization.startsWith(authorizationConfig.getTokenHead())) {

            // https://tools.ietf.org/html/rfc6750
            final String token = authorization.substring(authorizationConfig.getTokenHead().length());
            final String username = authService.parseUsername(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                if (authService.validate(token)) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (userDetails != null) {
                        // unknown user
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
