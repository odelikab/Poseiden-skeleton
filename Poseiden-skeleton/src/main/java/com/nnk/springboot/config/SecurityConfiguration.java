package com.nnk.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Autowired
	private DataSource datasource;

	/**
	 * Authorizations Filter chain
	 * 
	 * @return filter chain built
	 * @throws Exception
	 */

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests((authz) -> authz.antMatchers("/").permitAll().antMatchers("/user/list")
						.hasRole("ADMIN").antMatchers("/user/delete/*").hasRole("ADMIN").antMatchers("/user/update/*")
						.hasRole("ADMIN").anyRequest().authenticated().and())
				.formLogin().defaultSuccessUrl("/bidList/list");
		return http.build();
	}

	/**
	 * jdbc driver authentication setup
	 */

	@Bean
	public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
		String usersByUsernameQuery = "select username,password,'true' as enabled from users where username=?";
		String authsByUserQuery = "select username,authority from authorities where username=?";

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery(usersByUsernameQuery);

		return users;
	}

	/**
	 * Password encoder for user login
	 * 
	 * @return type of password encoded used
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
