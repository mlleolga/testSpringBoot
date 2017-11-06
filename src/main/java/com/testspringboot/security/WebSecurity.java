package com.testspringboot.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


/*We have annotated this class with @EnableWebSecurity and made it extend WebSecurityConfigurerAdapter
* to take advantage of the default web security configuration provided by Spring Security.
*   This allows us to fine-tune the framework to our needs by defining three methods:
*       configure(HttpSecurity http)
*       configure(AuthenticationManagerBuilder auth)
*       corsConfigurationSource()*/

/*WebSecurityConfigurerAdapter - щоб скорист. конфіг.  веб-безпеки за замовч.*/
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /*configure(HttpSecurity http): a method where we can define which resources are public
    * and which are secured.
    *       http.cors() - configure Cross-Origin Resource Sharing */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                //.antMatchers("/users/findAll").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                // We filter the api/login requests
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /*configure(AuthenticationManagerBuilder auth): a method where we defined a custom implementation of UserDetailsService
    * to load user-specific data in the security framework.
    * we have also used this method to set the encrypt method used by our application (BCryptPasswordEncoder).*/
/*userDetailsService - для завант. корист. данних в інфрастр.безпеки і для встановл. методу шифрування*/
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    /*corsConfigurationSource(): a method where we can allow/restrict our CORS support.
    * In our case we left it wide open by permitting requests from any source (/**).*/

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }


}
