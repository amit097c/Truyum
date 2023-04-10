package com.truyum.model;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class HttpResponse 
  {
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String reason;
	private String message;
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="MM-dd-yyyy hh:mm:ss",timezone="Asia/kolkata")
	private Date timeStamp;
  }
