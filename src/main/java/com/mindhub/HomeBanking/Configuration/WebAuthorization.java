package com.mindhub.HomeBanking.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@EnableWebSecurity

@Configuration

class WebAuthorization extends WebSecurityConfigurerAdapter {// hereda Del configurerAdapter
    // para sobrescribir el método configure indicando la nueva configuración de autorización

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/web/index.html","/styles/style.css","/scripts/**").permitAll() //STYLE SOLO INDEX PARA PERMITALL
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.PATCH, "/api/cardTransaction").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/transactions").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clientLoans").hasAuthority("CLIENT")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .antMatchers("/manager.html").hasAuthority("ADMIN")
                .antMatchers("/h2-console","/rest/**").hasAuthority("ADMIN")
                .antMatchers("/api/clients/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/accounts/{id}").hasAuthority("CLIENT")
                .antMatchers("/api/**").hasAuthority("ADMIN")
                .antMatchers("/web/account.html","/web/accounts.html","/web/cards.html").hasAuthority("CLIENT");



        http.formLogin()

                .usernameParameter("email")

                .passwordParameter("password")

                .loginPage("/api/login");



        http.logout().logoutUrl("/api/logout");

        // turn off checking for CSRF tokens
        http.csrf().disable();



        //disabling frameOptions so h2-console can be accessed

        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response

        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication

        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response

        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response

        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        }

    }
    }

