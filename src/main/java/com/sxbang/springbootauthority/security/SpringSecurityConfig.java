package com.sxbang.springbootauthority.security;

import com.sxbang.springbootauthority.security.authentication.MyAuthenctiationFailureHandler;
import com.sxbang.springbootauthority.security.authentication.MyAuthenticationSuccessHandler;
import com.sxbang.springbootauthority.security.authentication.MyLogoutSuccessHandler;
import com.sxbang.springbootauthority.security.authentication.RestAuthenticationAccessDeniedhandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedhandler restAuthenticationAccessDeniedhandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        //'X-Frame-Options' to 'deny'.
        http.headers().frameOptions().sameOrigin();
        http.authorizeRequests()
                .antMatchers("/login.html","/xadmin/**","/treetable-lay/**","/ztree/**","/static/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)
        .and().logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler);

        //异常处理
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedhandler);
    }
}
