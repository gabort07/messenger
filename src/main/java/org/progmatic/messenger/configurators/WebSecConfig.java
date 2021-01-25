package org.progmatic.messenger.configurators;

import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("admin").
                password("password").roles("ADMIN").build());
        return manager;
    }


    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/error").permitAll()
                .and()
                .formLogin()
//                .defaultSuccessUrl("/main", true)
                .loginPage("/loginpage").permitAll()
                .loginProcessingUrl("/loginpage/login")
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/loginpage")
                .and()
                .authorizeRequests()
                .antMatchers("/registration").permitAll()
//                .antMatchers("messages/delete").hasRole("ADMIN")
//                .antMatchers("messages/statistic").hasRole("OFFICE")
                .anyRequest().authenticated();
    }

}