package com.example.jwt1;


import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {


	@Autowired
	private MyUserDetailService userService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
//	@Autowired
//	private JwtUserDetailsService userDetailsService;
//
    @Autowired
    private AuthenticationManager authenticationManager;
	
	@GetMapping("/hello")
	public String getHello() {
		return "Hello Chandu";
	}
	
//	@PostMapping("/authenticate")
//	public ResponseEntity<?> authenticateToken(@RequestBody UserDto user) throws Exception {
//		authenticate(user.getUsername(), user.getPassword());
//
//		final UserDetails userDetails = userDetailsService
//				.loadUserByUsername(user.getUsername());
//
//		final String token = jwtTokenUtil.generateToken(userDetails);
//
//		return ResponseEntity.ok(new JwtResponse(token));
//	
//}
//	private void authenticate(String username, String password) throws Exception {
//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//		} catch (DisabledException e) {
//			throw new Exception("USER_DISABLED", e);
//		} catch (BadCredentialsException e) {
//			throw new Exception("INVALID_CREDENTIALS", e);
//		}
//	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateToken(@RequestBody UserDto user) throws Exception {
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				user.getUsername(),user.getPassword()));
}
		catch(BadCredentialsException e) {
			throw new Exception("Incorrect password");
		}
		
		final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
		final String jwt = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(jwt));
	}
	
	
	
}
