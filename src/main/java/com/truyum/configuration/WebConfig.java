package com.truyum.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.truyum.filter.JwtAuthenticationEntryPoint;
import com.truyum.filter.JwtAuthenticationFilter;
import com.truyum.services.UserService;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer 
  {
	@Autowired
	UserService userService;
	@Autowired 
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	JwtAuthenticationEntryPoint jwtAuthEntryPoint;
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	/*@Bean
    public WebMvcConfigurer addCorsMappings(CorsRegistry registry) 
      {
		return new WebMvcConfigurer() {
        @Override
		public void addCorsMappings(CorsRegistry registry) {	
		registry.addMapping("/**")
        .allowedMethods("*")
        .allowedOrigins("*")
        .allowedHeaders("*");
		}
      };
		}*/
    /*@Override
    @Bean
    public void addCorsMappings(CorsRegistry registry) {
        registry
        .addMapping("/**")
        .allowedMethods("POST", "DELETE")
        .allowedHeaders("*")
        .exposedHeaders("*")
        .allowCredentials(true).maxAge(3600);
    }*/
	@Bean
	SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
	/*	http
		.csrf()
		.disable()
		.cors();
		//http.formLogin();
		http.httpBasic()
		.and().authorizeHttpRequests().requestMatchers("/**").permitAll()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.and().authorizeRequests().requestMatchers("/**").permitAll()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
*/
	    System.out.println("inside securityFilter chain");	
		http
		.csrf()
		.disable()
		.cors()
		.and()
		.authorizeHttpRequests().requestMatchers("/**").permitAll()
		.anyRequest().authenticated()
		.and()
		//.headers(headers -> headers
			//	.addHeaderWriter(new StaticHeadersWriter("jwt_token_header","header-value"))
			//)
		.httpBasic()
		.and().exceptionHandling()
		.authenticationEntryPoint(jwtAuthEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		http.cors();
		//http.addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);
		//http.headers().frameOptions().disable();  
		return http.build();
	}

   /* @Bean 
    public AuthenticationManager authenticationManagerTemp(AuthenticationConfiguration authConfig) throws Exception {
        
    	return authConfig.getAuthenticationManager();
    	//rturn authConfig.userService(userService()).passwordEncoder(passwordEncoder()).and().build();
    	
    }*/

    @Bean 
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception
      {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
      }

   /* @Bean 
    protected void configure(AuthenticationManagerBuilder auth)
      {
    	super.configure(auth);
    	try 
    	  {
			auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
		  } 
    	catch (Exception e) 
    	  {
			e.printStackTrace();
		  }
      }*/
  }