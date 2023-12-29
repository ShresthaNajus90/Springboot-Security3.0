package com.springsecurity30.config;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfig{
    UserDetails User;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails normalUser = User
                .withUserDetails(User)
                .password(passwordEncoder().encode("password"))
                .roles("NORMAL")
                .build();

        UserDetails adminUser = User
                .withUserDetails(User)
                .password(passwordEncoder().encode("Admin"))
                .roles("ADMIN")
                .build();
      InMemoryUserDetailsManager inMemoryUserDetailsManager =  new InMemoryUserDetailsManager(normalUser, adminUser);

      return inMemoryUserDetailsManager;
    }


    @Bean
    public SecurityFilterChain fiterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/home/public/**").hasRole("PUBLIC")
                        .anyRequest().authenticated()

                );
        return httpSecurity.build();
    }


}
