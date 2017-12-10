package jp.co.ack.crossword.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import jp.co.ack.crossword.application.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/login", "/images/**", "/css/**", "/js/**").permitAll()
//				.antMatchers("/", "/**").hasRole("APP_USER").and().formLogin().loginPage("/login")
//				.failureUrl("/login?error=true").and().csrf().disable().logout().logoutUrl("/logout");
//	}


    private static String ROLE_USER = "USER";
    private static String ROLE_ADMIN = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user").hasAnyRole(ROLE_USER, ROLE_ADMIN)
                .antMatchers("/admin").hasRole(ROLE_ADMIN)
                .and()

                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/user")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()

                .logout()
                .permitAll()
                .and()

                .csrf();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/*.html", "/*.css")
                .antMatchers("/bootstrap/**");
    }

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//
//		// userService.registerUser("admin", "testpass", "admin@localhost",
//		// true);
//	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

}
