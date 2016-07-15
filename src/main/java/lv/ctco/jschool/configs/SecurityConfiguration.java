package lv.ctco.jschool.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//
//        httpSecurity.authorizeRequests()
//                .antMatchers("/meals**").authenticated().and().formLogin()
//                .loginPage("/login.html").and()
////
////                .antMatcher("/login.html").anonymous().and().antMatcher("registration.html").anonymous().and()
//                .httpBasic().and()
//                .logout().logoutSuccessUrl("/login?logout").and()
//                .csrf().disable();
//        httpSecurity.headers().frameOptions().disable();

        httpSecurity.authorizeRequests()
                .antMatchers("/meals**").authenticated().and().formLogin()
                .loginPage("/login.html").permitAll().and()
                .httpBasic().and()
                .logout().logoutSuccessUrl("/login?logout").and()
                .csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("blablabla");
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery(
                        "select email, pass, true from users where email = ?")
                .authoritiesByUsernameQuery(
                        "select email, user_role from users u INNER JOIN user_roles ur ON ur.userfk = u.id where u.email=?");
    }

}