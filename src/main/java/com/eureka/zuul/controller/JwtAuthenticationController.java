package com.eureka.zuul.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eureka.zuul.dto.JwtResponse;
import com.eureka.zuul.dto.ResponseData;
import com.eureka.zuul.dto.UserDTO;
import com.eureka.zuul.security.JwtTokenUtil;
import com.eureka.zuul.service.JwtUserDetailsService;

@RestController
@CrossOrigin
//@RequestMapping(")
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

	@Value("${jwt.timetoken}")
	private String jwt_token_validity;

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody UserDTO rq, HttpServletRequest request)
			throws Exception {
		ResponseEntity<?> responseEntity;
		authenticate(rq.getUsername(), rq.getPassword());
		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		try {
			final UserDetails userDetails = userDetailService.loadUserByUsername(rq.getUsername());
			Date date = jwtUserDetailsService.getTimeToken(rq.getUsername());
			if (date != null && date.after((new Date()))) {
				responseEntity = new ResponseEntity<>(new ResponseData(null, "Tai khoan dang duoc dang nhap", null),
						HttpStatus.BAD_REQUEST);
			} else {
				long timeToken = new Date().getTime() + Long.parseLong(jwt_token_validity) * 1000;
				jwtUserDetailsService.updateTimeTokenByUsername(rq.getUsername(), new Date(timeToken));
				final String token = jwtTokenUtil.generateToken(userDetails);
				responseEntity = new ResponseEntity<>(
						new ResponseData(null, "susscess", new JwtResponse(token)), HttpStatus.OK);
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>(new ResponseData(null, e.getMessage(), null), HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	@PostMapping("/signout")
	public ResponseEntity<?> logoutUser(Principal principal) throws Exception {
		ResponseEntity<?> responseEntity;
		try {
			Date date = jwtUserDetailsService.getTimeToken(principal.getName());
			if (date != null) {
				jwtUserDetailsService.updateTimeTokenByUsername(principal.getName(), null);
				responseEntity = new ResponseEntity<>(new ResponseData(null, "susscess", null), HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<>(new ResponseData(null, "err", null),
						HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			log.info("===== err ==" + e.getMessage());
			responseEntity = new ResponseEntity<>(new ResponseData(null, e.getMessage(), null), HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS" + e.getMessage(), e);
		}
	}
}
