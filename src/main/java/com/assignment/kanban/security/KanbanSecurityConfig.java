package com.assignment.kanban.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class KanbanSecurityConfig {

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	KanbanJwAuthFilter kanbanJwAuthFilter;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable);
		http.cors(Customizer.withDefaults());
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(AntPathRequestMatcher.antMatcher("/login"),
				AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/user"), AntPathRequestMatcher.antMatcher("/error"))
				.permitAll().anyRequest().authenticated());
		http.authenticationProvider(kanbanAuthenticationProvider());
		http.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.addFilterBefore(kanbanJwAuthFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationProvider kanbanAuthenticationProvider() {

		DaoAuthenticationProvider kanbanDaoAuthenticationProvider = new DaoAuthenticationProvider();
		kanbanDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
		kanbanDaoAuthenticationProvider.setPasswordEncoder(kanbanPasswordEncoder());
		return kanbanDaoAuthenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder kanbanPasswordEncoder() {

		return NoOpPasswordEncoder.getInstance();
	}

}
