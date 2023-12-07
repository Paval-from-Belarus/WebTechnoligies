package by.bsuir.poit.servlets;

import by.bsuir.poit.servlets.filters.PrincipalFilter;
import jakarta.servlet.http.HttpFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @author Paval Shlyk
 * @since 07/12/2023
 */
@Configuration
public class WebSecurityConfig {
@Bean
public HttpFilter authFilter() {
      return new PrincipalFilter();
}

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      final List<String> UNAUTHORIZED_ACCESS_PAGES = List.of(
	  "/lobby", "/api/reg", "/api/auth", "/error", "/js", "/css"
      );
      return http.csrf(AbstractHttpConfigurer::disable)
		 .authorizeHttpRequests(auth -> auth
						    .requestMatchers(UNAUTHORIZED_ACCESS_PAGES.toArray(String[]::new)).permitAll()
						    .anyRequest().authenticated()
		 )
		 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .addFilterBefore(authFilter(), PrincipalFilter.class)
		 .http
		 .build();

}
}
