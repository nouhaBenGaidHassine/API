package nouha.bgh.api.sec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import nouha.bgh.api.bean.Tokens;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private String jsonUsername;
	private String jsonPassword;

	private AuthenticationManager authenticationManager;
	private Tokens tokens;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {

		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String password = null;

		if ("application/json".equals(request.getHeader("Content-Type"))) {
			password = this.jsonPassword;
		} else {
			password = super.obtainPassword(request);
		}

		return password;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		String username = null;

		if ("application/json".equals(request.getHeader("Content-Type"))) {
			username = this.jsonUsername;
		} else {
			username = super.obtainUsername(request);
		}

		return username;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Map<String, String> myMap = new HashMap<String, String>();
		try {
			byte[] mapData = IOUtils.toByteArray(request.getReader());

			ObjectMapper objectMapper = new ObjectMapper();
			myMap = objectMapper.readValue(mapData, HashMap.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(myMap.get("username"), myMap.get("password")));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		if (tokens == null) {
			ServletContext servletContext = request.getServletContext();
			WebApplicationContext webApplicationContext = WebApplicationContextUtils
					.getWebApplicationContext(servletContext);
			tokens = webApplicationContext.getBean(Tokens.class);
		}
		String token = tokens.getTokenFor(authResult.getName());
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.getWriter().write("{\"email\": \"" + authResult.getName() + "\"}");
	}

}
