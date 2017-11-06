package com.testspringboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testspringboot.persistance.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.testspringboot.security.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

//    public interface AuthenticationManager Processes an Authentication request.
    private AuthenticationManager authManager;

    private static final Logger LOGGER = Logger.getLogger( JWTAuthenticationFilter.class.getName() );

     JWTAuthenticationFilter(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    /*attemptAuthentication: where we parse the user's credentials and issue them to the AuthenticationManager.*/

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            UserEntity creds = new ObjectMapper()
                    .readValue(request.getInputStream(), UserEntity.class);
            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>())
            );
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*successfulAuthentication: which is the method called when a user successfully logs in.*/
    /*We use this method to generate a JWT for this user.*/

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String token = Jwts.builder()
                .setSubject(((User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .compact();
        LOGGER.log(Level.INFO, token);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
      //  response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + SecurityConstants.generateToken(((User)authResult.getPrincipal()).getUsername()));

    }
}
