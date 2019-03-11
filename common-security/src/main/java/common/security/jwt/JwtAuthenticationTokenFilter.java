package common.security.jwt;

import common.security.AuthService;
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

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private JwtConfig config;

    private UserDetailsService userDetailsService;

    private AuthService authService;

    public JwtAuthenticationTokenFilter(JwtConfig config, UserDetailsService userDetailsService, AuthService authService) {
        this.config = config;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader(config.getTokenHeader());

        if (authorization != null && authorization.startsWith(config.getTokenHead())) {

            // https://tools.ietf.org/html/rfc6750
            final String token = authorization.substring(config.getTokenHead().length());
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
