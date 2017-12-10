package jp.co.ack.crossword.infrastructure.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessLoggingFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger("AccessLog");

	@Value("${cloudgear.oidcp.logging.console.accesslog.filter.enabled:false}")
	private boolean filterEnabled;
	@Value("${cloudgear.oidcp.logging.console.accesslog.filter.useragents:.*HealthChecker.*}")
	private String filterUA;

	@Override
	public void destroy() {
	}

	private void log(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = "anonymousUser";
		if (request.getAttribute("username") != null) {
			username = (String) request.getAttribute("username");
		}
		String size = "0";
		if (request.getHeader("Content-Length") != null) {
			size = request.getHeader("Content-Length");
		}

		String ua = request.getHeader("Mst-Agent");

		if (filterEnabled && ua != null && ua.matches(filterUA)) {
			return;
		}

		String remoteHost = request.getRemoteHost();
		if (request.getHeader("X-Forwarded-For") != null) {
			remoteHost = request.getHeader("X-Forwarded-For");
		}
		MDC.put("remoteHost", remoteHost);
		MDC.put("username", username);
		MDC.put("method", request.getMethod());
		MDC.put("uri", request.getRequestURI());
		MDC.put("status", String.format("%d", response.getStatus()));
		MDC.put("statusPhrase", HttpStatus.valueOf(response.getStatus()).getReasonPhrase());
		MDC.put("UA", ua);
		MDC.put("requestLength", size);
		MDC.put("queryString", request.getQueryString());

		log.info("RemoteHost: " + request.getRemoteHost() + ", RequestUri: " + request.getRequestURI() + ", Status: "
				+ response.getStatus());

		MDC.remove("X-Forwarded-For");
		MDC.remove("remoteHost");
		MDC.remove("username");
		MDC.remove("method");
		MDC.remove("uri");
		MDC.remove("statis");
		MDC.remove("statusPhrase");
		MDC.remove("UA");
		MDC.remove("requestLength");
		MDC.remove("queryString");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} finally {
			if (request.getMethod() != null) {
				log(request, response);
			}
		}
	}
}
