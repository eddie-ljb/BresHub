package de.etiennebader.breshub_engine.filter;

import com.nimbusds.common.contenttype.ContentType;
import de.etiennebader.breshub_engine.util.JwtUtils;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

public class AuthorizationFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    private UserDetailsService userDetailsService;

    private NimbusJwtDecoder jwtDecoder;

    public AuthorizationFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(jwtUtils.getSigningKey()).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/auth") || request.getRequestURI().startsWith("/swagger-ui") || request.getRequestURI().startsWith("/v3")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Authorization Filter");
        try {
            jwtDecoder = NimbusJwtDecoder.withSecretKey(jwtUtils.getSigningKey()).build();
            String jwt = parseJwt(request);
            System.out.println("parsed JWT: " + jwt);
            if ((!jwt.isEmpty()) && (validateJwtToken(jwt))) {
                System.out.println("JWT Token: " + jwt);
                String username = getUsernameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                System.out.println("Invalid JWT token: " + (!jwt.isEmpty()) + ", " + (validateJwtToken(jwt)));
                writeUnauthorizedResponse(response, "Unauthorized Request");
                return;
            }
        } catch (Exception e) {
            writeUnauthorizedResponse(response, e.getMessage());
            System.out.println("Cannot set user authentication: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private static void writeUnauthorizedResponse(HttpServletResponse response, String exception) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(ContentType.APPLICATION_JSON.toString());
        response.getWriter().write(exception);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }

    public boolean validateJwtToken(String authToken) {
        System.out.println(authToken + ", key: " + jwtUtils.getSigningKey().getAlgorithm().toString());
        Jwts.parserBuilder()
                .setSigningKey(jwtUtils.getSigningKey())
                .build()
                .parseClaimsJws(authToken);
            return true;
    }

    public String getUsernameFromJwtToken(String token) {
        //Map<String, Object> claims = jwtDecoder.decode(token).getClaims();
        return Jwts.parserBuilder()
                .setSigningKey(jwtUtils.getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}