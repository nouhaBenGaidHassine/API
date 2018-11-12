package nouha.bgh.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nouha.bgh.api.bean.Justifier;
import nouha.bgh.api.bean.Tokens;

@RestController
@RequestMapping("/api")
public class JustifyController {
	@Autowired
	Justifier justifier;

	@Autowired
	Tokens tokens;

	@PostMapping("/justify")
	@ResponseBody
	String justifyText(@RequestBody String text, HttpServletResponse response) {
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		justifier.setNumberOfWords(tokens.getWordsCount(user));
		String output = justifier.justify(text);
		if (justifier.getNumberOfWords() > Justifier.max_words) {
			response.setStatus(HttpStatus.PAYMENT_REQUIRED.value());
		} else {
			response.setContentType("text/plain");
		}
		tokens.setWordsCount(user, justifier.getNumberOfWords());
		return output;
	}
}
