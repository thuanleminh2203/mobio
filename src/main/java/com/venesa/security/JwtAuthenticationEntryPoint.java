package com.venesa.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.venesa.component.BodyRequestComponent;
import com.venesa.entity.LogEntity;
import com.venesa.service.LogService;
import com.venesa.utils.ConstantsUtil;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -7858869558953243875L;

	private static Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Autowired
	private LogService logService;
	
	@Autowired
	private BodyRequestComponent bodyRequestComponent; 

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth != null ? auth.getName() : "unknow";
		String remoteAddr = "";
		remoteAddr = request.getHeader("X-FORWARDED-FOR");
		if (remoteAddr == null || "".equals(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		String bodyRequest = null;
		if(request.getMethod().equals(ConstantsUtil.HTTP_POST) || request.getMethod().equals(ConstantsUtil.HTTP_PUT) ) {
			bodyRequest = bodyRequestComponent.getBody(request);
		}
		try {

			log.info("Request method = {}, url = {}, user= {},  remoteAddr = {} ", request.getMethod(),
					request.getRequestURL().toString(), username, remoteAddr);
			if (username != null) {
				LogEntity logEntity = new LogEntity();
				logEntity.setMethod(request.getMethod());
				logEntity.setUrl(request.getRequestURL().toString());
				logEntity.setUsername(username);
				logEntity.setRemoteAddr(remoteAddr);
				logEntity.setTime(new Date());
				logEntity.setMessage(authException.getMessage());
				logEntity.setTypeErr(ConstantsUtil.UNAUTHORIZED);
				logEntity.setUserAgent(request.getHeader("User-Agent"));
				logEntity.setBody(bodyRequest);
				logService.save(logEntity);
			}
		} catch (Exception e) {
			log.info("=====errrr =======" + e.getMessage());
		}
		log.info("=====errrr =======" + authException.getMessage());
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConstantsUtil.ERR_UNAUTHORIZED);
	}

}
