package com.techlabs.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.techlabs.app.security.JwtAuthenticationEntryPoint;
import com.techlabs.app.security.JwtAuthenticationFilter;

@Configuration
//@EnableMethodSecurity
public class SecurityConfig {

	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	private JwtAuthenticationFilter authenticationFilter;

	public SecurityConfig(JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authorize -> authorize
				// Admin endpoints
				.requestMatchers(HttpMethod.POST, "/api/accounts/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/accounts/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/accounts/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.POST, "/api/banks/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/banks/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.PUT, "/api/banks/**").hasRole("ADMIN")
				.requestMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")

				// Customer endpoints
				.requestMatchers(HttpMethod.GET, "/api/transactions/{id}").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.POST, "/api/transactions").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.PUT, "/api/transactions/{id}").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.DELETE, "/api/transactions/{id}").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.POST, "/api/banks/deposit").hasRole("CUSTOMER")
				.requestMatchers(HttpMethod.POST, "/api/banks/withdraw").hasRole("CUSTOMER")

				// Public endpoints
				.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated())
				.exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}
