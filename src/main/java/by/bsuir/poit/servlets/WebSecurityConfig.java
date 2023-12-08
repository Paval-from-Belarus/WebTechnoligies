package by.bsuir.poit.servlets;

import by.bsuir.poit.services.AuthorizationService;
import by.bsuir.poit.servlets.interceptors.PrincipalFilter;
import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 07/12/2023
 */
@Configuration
public class WebSecurityConfig {
@Autowired
private PrincipalFilter principalFilter;
@Autowired
private AuthorizationService authorizationService;

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      final List<String> UNAUTHORIZED_ACCESS_PAGES = List.of(
	  "/lobby", "/api/reg", "/api/auth", "/error", "/js", "/css"
      );
      return http.csrf(AbstractHttpConfigurer::disable)
		 .authorizeHttpRequests(auth -> auth
						    .requestMatchers(UNAUTHORIZED_ACCESS_PAGES.toArray(String[]::new)).permitAll()
						    .requestMatchers("/api/admin").hasRole("ADMIN")
						    .requestMatchers("/api/client").hasRole("CLIENT")
						    .anyRequest().authenticated()
		 )
		 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .addFilterBefore(principalFilter, PrincipalFilter.class)
		 .formLogin(form -> form
					.loginPage("/lobby")
					.permitAll())
		 .build();
}

@Bean
public UserDetailsService userDetailsService() {

      return null;
//      return username -> authorizationService.
}

@Bean
public AuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setUserDetailsService();
      return provider;
}
}
