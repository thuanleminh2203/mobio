package com.venesa.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.venesa.entity.LogEntity;
import com.venesa.service.LogService;
import com.venesa.utils.ConstantsUtil;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private static Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Autowired
	private LogService logService;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth != null ? auth.getName() : "unknow";
		String remoteAddr = "";
		remoteAddr = request.getHeader("X-FORWARDED-FOR");
		if (remoteAddr == null || "".equals(remoteAddr)) {
			remoteAddr = request.getRemoteAddr();
		}
		try {

			log.info("Request method = {}, url = {}, user= {},  remoteAddr = {} ", request.getMethod(),
					request.getRequestURL().toString(), auth.getName(), remoteAddr);
			if (username != null) {
				LogEntity logEntity = new LogEntity();
				logEntity.setMethod(request.getMethod());
				logEntity.setUrl(request.getRequestURL().toString());
				logEntity.setUsername(username);
				logEntity.setRemoteAddr(remoteAddr);
				logEntity.setTime(new Date());
				logEntity.setMessage(accessDeniedException.getMessage());
				logEntity.setTypeErr(ConstantsUtil.FORBIDDEN);
				logService.save(logEntity);
			}
		} catch (Exception e) {
			log.info("=====errrr =======" + e.getMessage());
		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, ConstantsUtil.FORBIDDEN);

	}
}
