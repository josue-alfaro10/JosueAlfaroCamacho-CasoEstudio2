/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Caso2_JosueAlfaroCamacho;

import Caso2_JosueAlfaroCamacho.service.UsuarioDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author Alfaro
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filtroSeguridad(HttpSecurity http,
            @Lazy UsuarioDetailsService detallesService) throws Exception {
        http.authorizeHttpRequests(rutas -> rutas
            .requestMatchers("/", "/login", "/webjars/**", "/css/**", "/js/**").permitAll()
            .requestMatchers("/evento/listado", "/evento/listado/**", "/consultas", "/consultas/**").permitAll()
            .requestMatchers("/usuario/**", "/rol/**").hasRole("ADMIN")
            .requestMatchers("/evento/nuevo", "/evento/editar/**", "/evento/eliminar/**").hasAnyRole("ADMIN", "ORGANIZADOR")
            .requestMatchers("/evento/**").hasAnyRole("ADMIN", "ORGANIZADOR", "CLIENTE")
            .requestMatchers("/consultas/**").hasAnyRole("ADMIN", "ORGANIZADOR", "CLIENTE")
            .anyRequest().authenticated()
        )
                .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
                )
                .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/accesonegado"));

        return http.build();
    }

    @Bean
    public PasswordEncoder codificadorPassword() {
        return new BCryptPasswordEncoder();
    }

  

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UserDetailsService userDetailsService) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
