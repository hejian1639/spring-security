package com.didispace;

import org.apache.catalina.servlets.DefaultServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/**/*.js", "/favicon.ico", "/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(customAuthenticationFilter(), CustomAuthenticationFilter.class)
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf().disable();

    }

//    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setFilterProcessesUrl("/session/login");

        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password=encoder.encode("p");
        System.out.println(password);
        auth.inMemoryAuthentication().withUser("u").password(password).roles("USER")
                .and().withUser("user").password(password).roles("USER")
                .and().passwordEncoder(encoder);
    }


    @Bean
    public ServletRegistrationBean defaultServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new DefaultServlet(), "*.gif", "*.ttf",
                "*.gif", "*.woff", "*.svg", "*.js", "*.jpg", "*.html", "*.css", "*.png", "*.mp4", "*.m4a", "*.ogg");
        registration.setLoadOnStartup(1);
        Map<String, String> params = new HashMap<String, String>();
        params.put("debug", "0");
        params.put("listings", "false");
        registration.setInitParameters(params);
        return registration;
    }


}