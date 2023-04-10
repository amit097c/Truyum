package com.truyum.filter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.truyum.model.HttpResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint//extends Http403ForbiddenEntryPoint 
  {
	@Override
	public void commence(HttpServletRequest reuqest,HttpServletResponse response,AuthenticationException exception) throws IOException 
	  {
		/*Calendar cal=Calendar.getInstance();
		Date date=cal.getTime();
		//DateFormat dateFormat=new SimpleDateFormat("MM-dd-yyyy hh:mm:ss");
		//String formatedDate=dateFormat.format(date);
		HttpResponse httpResponse=new HttpResponse(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN,HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(),"Forbidden Message",date);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		OutputStream outputStream=response.getOutputStream();
		ObjectMapper mapper=new ObjectMapper();
		mapper.writeValue(outputStream,httpResponse);
		outputStream.flush();*/ 
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
	  }
  }
 