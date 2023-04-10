package com.truyum.jwtUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
//import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.truyum.constants.SecurityConstant;
import com.truyum.model.SecurityUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
@Component
public class JWTTokenProvider
 {
	@Value("$jwt.secret")
	private String secret;
    private Logger LOGGER=LoggerFactory.getLogger(getClass());
	public String generatorJWTToken(SecurityUser userPrincipal) 
	  {
		LOGGER.info("Inside generateJWTToken method of JWTTokenProvide class");
		String[] claims=getClaimsFromUser(userPrincipal);
		LOGGER.info("Get Claims from userPrincipal claims: "+claims);
		LOGGER.info("token will generated by: JWT.create().withIssuer(\"Truyum\").withAudience(\"Truyum Portal\")\r\n"
				+ "				  .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())\r\n"
				+ "				  .withArrayClaim(\"Authorities\", claims).withExpiresAt(new Date(System.currentTimeMillis()+432_000_000))\r\n"
				+ "				  .sign(Algorithm.HMAC512(secret.getBytes()))");
		
		return JWT.create().withIssuer("Truyum").withAudience("Truyum Portal")
				  .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
				  .withArrayClaim("Authorities", claims).withExpiresAt(new Date(System.currentTimeMillis()+432_000_000))
				  .sign(Algorithm.HMAC512(secret.getBytes()));
	  }
	public String[] getClaimsFromUser(SecurityUser userPrincipal) 
	  {
		List<String> authorities=new ArrayList<String>();
		for(GrantedAuthority grantedAuthority:userPrincipal.getAuthorities()) 
		  {
			authorities.add(grantedAuthority.getAuthority());
		  }
		return authorities.toArray(new String[0]);
	  }
	public String getUsernameFromToken(String token) 
	  {
		 JWTVerifier jwtVerifier=getJWTVerifier();
		 return jwtVerifier.verify(token).getSubject();
	  }
	public JWTVerifier getJWTVerifier() 
	  {
		JWTVerifier jwtVerifier;
		try 
		  {
			Algorithm algorithm=Algorithm.HMAC512(secret);
			jwtVerifier=JWT.require(algorithm).withIssuer(SecurityConstant.TRUYUM_APP).build();
		  }
		catch(JWTVerificationException exception) 
		  {
			System.out.println(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
			throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);
		  }
		return jwtVerifier;
	  }
	public boolean isTokenValid(String username,String token) 
	  {
		JWTVerifier verifier=getJWTVerifier();
		return StringUtils.isNotEmpty(username)&&isTokenExpired(verifier,token);
	  }
	public boolean isTokenExpired(JWTVerifier verifier, String token) 
	  {
		Date expiration=verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	  }
	public List<GrantedAuthority> getAuthorities(String token)
	  {
		String[] claims=getClaimFromToken(token);
		return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	  }
	public String[] getClaimFromToken(String token)
	  {
		JWTVerifier verifier=getJWTVerifier();
		return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);		
	  }
	public Authentication getAuthentication(String username,List<GrantedAuthority> authorities,HttpServletRequest request) 
	  {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username,authorities);
		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return usernamePasswordAuthenticationToken;
	  }
 }
