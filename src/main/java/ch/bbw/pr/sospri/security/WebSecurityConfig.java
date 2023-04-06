package ch.bbw.pr.sospri.security;

import ch.bbw.pr.sospri.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * WebSecurityConfig
 *
 * @author Nico Menzi
 * @version 06.04.2023
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
    //public void globalSecurityConfiguration(AuthenticationManagerBuilder auth) throws Exception {
        //auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
        //auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    //}
    @Autowired
    private MemberService memberService;

    //@Bean
    //public BCryptPasswordEncoder passwordEncoder() {
        //return new BCryptPasswordEncoder();
    //}

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder());
        auth.setUserDetailsService(this.memberService);
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String pepper = "sospri";
        int iterations = 1000;
        int hashWidth = 256;
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder(pepper, iterations, hashWidth);
        encoder.setEncodeHashAsBase64(true);
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure");
        http.authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/fragments/*").permitAll()
                .antMatchers("/img/*").permitAll()
                .antMatchers("/css/*").permitAll()
                .antMatchers("/get-channel").hasAnyAuthority("admin", "supervisor", "member")
                .antMatchers("/get-register").permitAll()
                .antMatchers("/get-members").hasAuthority("admin")
                .antMatchers(("/edit-members")).hasAuthority("admin")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/403.html");


        http.csrf().ignoringAntMatchers("/h2-console/**")
                .and()
                .headers().frameOptions().sameOrigin();
    }

}
