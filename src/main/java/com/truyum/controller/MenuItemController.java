package com.truyum.controller;


import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.truyum.constants.SecurityConstant;
import com.truyum.exception.domain.UsernameExistException;
import com.truyum.jwtUtility.JWTTokenProvider;
import com.truyum.jwtUtility.JwtAuthRequest;
import com.truyum.jwtUtility.JwtAuthResponse;
//import com.returnOrder.rest.webservices.returnOrderrestfulwebservices.domain.Customer;
import com.truyum.model.MenuItem;
import com.truyum.model.OrderDetails;
import com.truyum.model.SecurityUser;
import com.truyum.model.User;
import com.truyum.services.MenuItemService;
import com.truyum.services.UserService;


@RestController
public class MenuItemController
 { 
	private MenuItemService service; 
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	private SecurityUser userPrincipal;
	private JWTTokenProvider jwtToken;
	private Logger LOGGER=LoggerFactory.getLogger(getClass());
	@Autowired
	public MenuItemController(MenuItemService service,UserService userService,AuthenticationManager authenticationManager,SecurityUser userPrincipal,JWTTokenProvider jwtToken) 
	  {
		this.authenticationManager=authenticationManager;
		this.userService=userService;
		this.service=service;
		this.userPrincipal=userPrincipal;
		this.jwtToken=jwtToken;
	  }
	@GetMapping("/t")	
	public String check () 
	  {
		System.out.println("hello");
		return "Hello  xx";
      }

	//@Autowired
	//MenuItemService service;
	//@Autowired
	//UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<String> modifyMenuItem(@RequestBody String s )
	 {	
		Gson gson=new Gson();
		MenuItem mi=gson.fromJson(s,MenuItem.class);
		service.modifyMenuItem(mi);
		System.out.println(mi.toString());
		return ResponseEntity.status(HttpStatus.OK).body(mi.toString());
		//return s;
	 }

	@GetMapping("/MenuItems")
	public String getMenuItems() 
	  {
		List<MenuItem> menuItems=service.getMenuItemListAdmin();
		JsonElement jsonEle=new Gson().toJsonTree(menuItems,new TypeToken<List<MenuItem>>() {}.getType());
		JsonArray jsonArray=jsonEle.getAsJsonArray();
		System.out.println(jsonArray);
		return jsonArray.toString();
	  }
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody  JwtAuthRequest  credentials) 
	 {
		System.out.println("This is credentials-*********** "+credentials.getUsername()+" "+credentials.getPassword());
	    if(!authenticate(credentials.getUsername(),credentials.getUsername()))
	      {
	    	HttpHeaders header=new HttpHeaders();
	    	header.add(SecurityConstant.JWT_TOKEN_HEADER,"UNAUTHORIZED");
	    	
	    	ResponseEntity resEntity=new ResponseEntity("ACCESS DENIED! Invalid Username or password.",header,HttpStatus.UNAUTHORIZED);
	    	resEntity.status(UNAUTHORIZED);
	    	resEntity.notFound();
	    	LOGGER.info("UNAUTHORIZED ACCESS");
	    	return resEntity;
	      }
		
	    User loginUser=userService.findUserByUsername(credentials.getUsername());
		SecurityUser userPrincipal= new SecurityUser(loginUser);
		HttpHeaders jwtHeader=getJwtHeader(userPrincipal);
		System.out.println(jwtHeader);
		System.out.println(userPrincipal);
		JwtAuthResponse jwtResponse=new JwtAuthResponse();
		jwtResponse.setId(loginUser.getId());
		jwtResponse.setUsername(loginUser.getUsername());
		jwtResponse.setJwtToken((jwtHeader.get(SecurityConstant.JWT_TOKEN_HEADER)).get(0));
		jwtResponse.setExpiration_time(SecurityConstant.EXPIRATION_TIME);
		
		Map<String, String> bodyParamMap = new HashMap<String, String>();

		//Set your request body params
		bodyParamMap.put("UserId",jwtResponse.getId()+"");
		bodyParamMap.put("Username",jwtResponse.getUsername());
		bodyParamMap.put("JwtToken",jwtResponse.getJwtToken());
		bodyParamMap.put("Expiry",jwtResponse.getExpiration_time()+"");
		
		//bodyParamMap.put("header", jwtHeader.toString());
		
		ResponseEntity resEntity=new ResponseEntity(bodyParamMap,jwtHeader,OK);
		resEntity.ok(jwtResponse);
		LOGGER.info("ACCESS GRANTED");
		System.out.println(resEntity);
		return resEntity;
	 }

	@PostMapping("/register")
	public ResponseEntity<JwtAuthResponse> register(@RequestBody JwtAuthRequest  user)
	  {
		
		System.out.println("**"+user+"**");
		
		User user1=new User();
		JwtAuthResponse jwtResponse=new JwtAuthResponse();
		try 
		  {
			user1=userService.register(user.getUsername(),user.getPassword());
			jwtResponse.setId(user1.getId());
			jwtResponse.setUsername(user1.getUsername());
		  } 
		catch (UsernameExistException e)
		  {
			System.out.println(e.getMessage());
			e.printStackTrace();
		  }
		return  new ResponseEntity<JwtAuthResponse>(jwtResponse,OK);
	  }
	@PostMapping("/order")
	 public ResponseEntity<Integer> storeOrderDeatils(@RequestBody List<MenuItem> orderDetails)
	    {		  
		  LOGGER.info("inside OrderDetails method");
		  LOGGER.info("orderDetails[0]: "+orderDetails);
		  
		  return new ResponseEntity<Integer>(123,OK);
		}
	  
	public boolean authenticate(String username,String password) //throws AuthenticationException
	  {
		
		try 
		  {
			System.out.println("Authentication method called");
			UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(username,password);
			System.out.println(authToken);
		    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
		  }
		catch(NoSuchElementException e)
		  {
			LOGGER.info(e.getMessage());
			return false;
		  }
		catch(Exception e) 
		  {
			System.out.println(e.getMessage());
			System.out.println("Exception: "+e.getClass());
			return false;
			
		  } 
		return true;
		 
      }
	private HttpHeaders getJwtHeader(SecurityUser userPrincipal) 
	  {
		System.out.println("*************************generating JWT Headers*******************");
		HttpHeaders header=new HttpHeaders();
		//String token=jwtToken.generatorJWTToken(userPrincipal);
		header.add(SecurityConstant.JWT_TOKEN_HEADER,jwtToken.generatorJWTToken(userPrincipal));
		header.setExpires(300000);
		/*To add set httpOnlycookie
		 * ResponseCookie tokenCookie = ResponseCookie.from("jwt_token",token)
				    .httpOnly(true)
				    .secure(true)
				    .maxAge(SecurityConstant.EXPIRATION_TIME)

				    .build();
		header.add(HttpHeaders.SET_COOKIE, tokenCookie.toString());*/
		return header;
	  }
 }

