package nouha.bgh.api.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nouha.bgh.api.bean.Justifier;

@RestController
@RequestMapping("/api")
public class JustifyController {
	@Autowired
	Justifier justifier;

	@PostMapping("/justify")
	@ResponseBody
	String justifyText(@RequestBody String text, HttpServletResponse response) {
		response.setContentType("text/plain");
		return justifier.justify(text);
	}
}
