package nouha.bgh.api.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("foo1@bar.com").password(bCryptPasswordEncoder.encode("1234"))
				.roles("user");
		auth.inMemoryAuthentication().withUser("foo2@bar.com").password(bCryptPasswordEncoder.encode("1234"))
				.roles("user");
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	@Override

	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		http.authorizeRequests().anyRequest().authenticated();

		http.addFilter(new AuthenticationFilter(authenticationManager()));
		http.addFilterBefore(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

}
