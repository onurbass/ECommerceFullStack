package com.ecommerce.configuration;

import com.ecommerce.util.security.JWTFilter;
import com.ecommerce.util.security.SecurityService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  private static final String[] AUTH_WHITELIST = {
		  "/auth/**",
		  "/swagger-ui/**",
		  "v3/api-docs/**",
		  "/configuration/security",
		  "/swagger-ui.html",
		  "/user-api/**",
		  "/webjars/**",
		  "/v3/api-docs/**",
		  "/api/public/**",
		  "/api/public/authenticate",
		  "/actuator/*",
		  "/swagger-ui/**",
		  "/category/**",
		  "/entry/**",
		  "/entry-type/**",
		  "/graduate/**",

  };

  private final JWTFilter filter;
  private final SecurityService uds;

  public SecurityConfiguration(JWTFilter filter,SecurityService uds) {
	this.filter = filter;
	this.uds = uds;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http.headers().frameOptions().disable();
	http.csrf().disable()
		.httpBasic().disable()
		.cors()
		.configurationSource(request -> {
		  CorsConfiguration configuration = new CorsConfiguration();
		  configuration.setAllowedOrigins(List.of("*"));
		  configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE"));
		  configuration.setAllowedHeaders(List.of("*"));
		  configuration.setExposedHeaders(List.of("Content-Disposition"));
		  return configuration;
		}).and()
		.authorizeHttpRequests()
		.requestMatchers(AUTH_WHITELIST).permitAll()
		.requestMatchers("/deneme/**").hasRole("USER")
		.and()

		.userDetailsService(uds)
		.exceptionHandling()
		.authenticationEntryPoint(
				(request,response,authException) ->
						response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized")
								 )
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	http.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);

	return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	return authenticationConfiguration.getAuthenticationManager();
  }
}
