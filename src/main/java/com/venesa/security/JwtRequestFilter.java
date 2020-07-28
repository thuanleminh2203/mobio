package com.venesa.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.venesa.component.BodyRequestComponent;
import com.venesa.entity.LogEntity;
import com.venesa.service.JwtUserDetailsService;
import com.venesa.service.LogService;
import com.venesa.utils.ConstantsUtil;

//import com.eureka.zuul.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	private static Logger log = LoggerFactory.getLogger(JwtRequestFilter.class);

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private LogService logService;
	
	@Autowired
	private BodyRequestComponent bodyRequestComponent; 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				
				String remoteAddr = "";
				remoteAddr = request.getHeader("X-FORWARDED-FOR");
				if (remoteAddr == null || "".equals(remoteAddr)) {
					remoteAddr = request.getRemoteAddr();
				}
				
				log.info("Request method = {}, url = {}, user= {},  remoteAddr = {} ",
						request.getMethod(), request.getRequestURL().toString(), username, remoteAddr);
				String bodyRequest = null;
				if(request.getMethod().equals(ConstantsUtil.HTTP_POST) || request.getMethod().equals(ConstantsUtil.HTTP_PUT) ) {
					bodyRequest = bodyRequestComponent.getBody(request);
				}
//				System.out.println("======i'm hereeeee=====" + request.getHeader("User-Agent"));
//				String body = bodyRequestComponent.getBody(request);
//				log.info("=======body ======= body = {} " , body);
//				request.get
				if(username != null) {
					LogEntity logEntity = new LogEntity();
					logEntity.setMethod(request.getMethod());
					logEntity.setUrl(request.getRequestURL().toString());
					logEntity.setUsername(username);
					logEntity.setRemoteAddr(remoteAddr);
					logEntity.setTime(new Date());
					logEntity.setTypeErr(ConstantsUtil.OK);
					logEntity.setBody(bodyRequest);
					logEntity.setUserAgent(request.getHeader("User-Agent"));
					logService.save(logEntity);
					
				}

			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			} catch (Exception e) {
				System.out.println("=== err token === :" + e.getMessage());
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security
				// Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
