package com.nnk.springboot.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private DataSource datasource;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().passwordEncoder(passwordEncoder()).dataSource(datasource)
//				.usersByUsernameQuery("select username,password,'true' as enabled from users where username=?")
//				.authoritiesByUsernameQuery("select username,password from users where username=?");
//	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authz) -> authz.antMatchers("/").permitAll()// .antMatchers("/user/list").hasAnyRole("USER",
																												// "ADMIN")
				.anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).formLogin();
		return http.build();
	}

	@Bean
	public UserDetailsService jdbcUserDetailsService(DataSource dataSource) {
		String usersByUsernameQuery = "select username,password,'true' as enabled from users where username=?";
		String authsByUserQuery = "select username,password from users where username=?";

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

		users.setUsersByUsernameQuery(usersByUsernameQuery);
		users.setAuthoritiesByUsernameQuery(authsByUserQuery);

		return users;
	}

//	@Bean
//	public InMemoryUserDetailsManager userDetailsService() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("password").roles("USER")
//				.build();
//		return new InMemoryUserDetailsManager(user);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
