package br.edu.utfpr.cp.espjava.crudcidades.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().csrf().ignoringAntMatchers("/h2-console/**")
                .and().headers().frameOptions().sameOrigin();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/").hasAnyAuthority("listar", "admin")
                .antMatchers("/criar").hasAnyAuthority("admin")
                .antMatchers("/excluir").hasAnyAuthority("admin")
                .antMatchers("/preparaAlterar").hasAnyAuthority("admin")
                .antMatchers("/alterar").hasAnyAuthority("admin")
                .antMatchers("/mostrar").authenticated()
                .anyRequest().denyAll()
                .and()
                .formLogin().permitAll()
                .loginPage("/login.html").permitAll()
                .and()
                .logout().permitAll();

    }

    @Bean
    public PasswordEncoder cifrador(){
        return new BCryptPasswordEncoder();
    }

    @EventListener(ApplicationReadyEvent.class)
    public void printSenhas(){
        System.out.println(cifrador().encode("test123"));
    }

    @EventListener(InteractiveAuthenticationSuccessEvent.class)
    public void printUsuarioAtual(InteractiveAuthenticationSuccessEvent event){
        var usuario = event.getAuthentication().getName();
        System.out.println(usuario);
    }
}
