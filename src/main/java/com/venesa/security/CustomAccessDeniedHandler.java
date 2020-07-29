package com.venesa.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String username = auth != null ? auth.getName() : "unknow";
//		String remoteAddr = "";
//		String bodyRequest = null;
//		if(request.getMethod().equals(ConstantsUtil.HTTP_POST) || request.getMethod().equals(ConstantsUtil.HTTP_PUT) ) {
//			bodyRequest = getBody(request);
//		}
//		remoteAddr = request.getHeader("X-FORWARDED-FOR");
//		if (remoteAddr == null || "".equals(remoteAddr)) {
//			remoteAddr = request.getRemoteAddr();
//		}
//		try {
//
//			log.info("Request method = {}, url = {}, user= {},  remoteAddr = {} ", request.getMethod(),
//					request.getRequestURL().toString(), auth.getName(), remoteAddr);
//			if (username != null) {
//				LogEntity logEntity = new LogEntity();
//				logEntity.setMethod(request.getMethod());
//				logEntity.setUrl(request.getRequestURL().toString());
//				logEntity.setUsername(username);
//				logEntity.setRemoteAddr(remoteAddr);
//				logEntity.setTime(new Date());
//				logEntity.setMessage(accessDeniedException.getMessage());
//				logEntity.setTypeErr(ConstantsUtil.FORBIDDEN);
//				logEntity.setUserAgent(request.getHeader("User-Agent"));
//				logEntity.setBody(bodyRequest);
//				logService.save(logEntity);
//			}
//		} catch (Exception e) {
//			log.info("=====errrr =======" + e.getMessage());
//		}

		response.sendError(HttpServletResponse.SC_FORBIDDEN, ConstantsUtil.ERR_FORBIDDEM);

	}
	
	public String getBody(HttpServletRequest request) throws IOException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw ex;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		return body;
	}
}
