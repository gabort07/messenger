package org.progmatic.messenger.configurators;

import org.progmatic.messenger.modell.MyUser;
import org.progmatic.messenger.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class WebSecConfig extends WebSecurityConfigurerAdapter {

    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity httpSec) throws Exception {
        httpSec.authorizeRequests()
                .antMatchers("/css/**", "/error", "/registration", "/rest/csrf", "/rest/registration").permitAll()
                .and()
                .formLogin()
                .loginPage("/loginpage").permitAll()
                .loginProcessingUrl("/loginpage/login")
                .and()
                .logout().permitAll()
                .logoutSuccessUrl("/loginpage")
                .and()
                .authorizeRequests()
//                .antMatchers("messages/delete").hasRole("ADMIN")
//                .antMatchers("messages/statistic").hasRole("OFFICE")
                .anyRequest().authenticated();
    }

}