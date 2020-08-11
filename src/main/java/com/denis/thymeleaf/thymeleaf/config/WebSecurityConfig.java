package com.denis.thymeleaf.thymeleaf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.persistence.OneToOne;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    //css를 추가한 이유는 시큐리티 적용 시 설정하지 않은 리소스는 가져오지 못하기 때문에 /css를 가져오기 위해 추가
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()
                .antMatchers("/", "/css/**", "/account/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/account/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("SELECT USERNAME, PASSWORD, ENABLED "
                                    + "FROM USER "
                                    + "WHERE USERNAME = ?")
                .authoritiesByUsernameQuery("SELECT U.USERNAME, R.NAME "
                                          + "FROM USER_ROLE UR INNER JOIN USER U "
                                          + "ON UR.USER_ID =  U.ID "
                                          + "INNER JOIN ROLE R ON UR.ROLE_ID = R.ID "
                                          + "WHERE U.USERNAME = ?");
    }

    //Authentication 로그인
    //Authroization 권한

    //테이블조인방법
    //@OneToOne Ex)user - user_detail
    //@OneToMany Ex)user - board
    //ManyToOne Ex)board - user
    //ManyToMany Ex)user - role

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService() {
//        UserDetails user =
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("password")
//                        .roles("USER")
//                        .build();
//
//        return new InMemoryUserDetailsManager(user);
//    }
}