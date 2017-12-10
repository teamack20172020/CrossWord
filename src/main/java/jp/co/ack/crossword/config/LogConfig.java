package jp.co.ack.crossword.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {
	@Bean
	public FilterRegistrationBean accessLoggingFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new jp.co.ack.crossword.infrastructure.log.AccessLoggingFilter());
		registration.addUrlPatterns("/*");
		registration.setName("accessLoggingFilter");
		registration.setOrder(Integer.MIN_VALUE + 1);
		return registration;
	}
}
