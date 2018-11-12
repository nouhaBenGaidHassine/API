package nouha.bgh.api.bean;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import nouha.bgh.api.sec.SecurityConstants;

@Component
public class Tokens {
	private HashMap<String, String> tokens = new HashMap<String, String>();
	private HashMap<String, Integer> words_count = new HashMap<String, Integer>();

	public String getTokenFor(String user) {
		if (tokens.containsKey(user)) {
			return tokens.get(user);
		} else {
			ZonedDateTime expirationTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstants.EXPIRATION_TIME,
					ChronoUnit.MILLIS);
			String token = Jwts.builder().setSubject(user).setExpiration(Date.from(expirationTimeUTC.toInstant()))
					.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();
			tokens.put(user, token);
			words_count.put(user, 0);
			return token;
		}
	}

	public int getWordsCount(String user) {
		return words_count.get(user);
	}

	public void setWordsCount(String user, int value) {
		words_count.put(user, value);
	}
}
