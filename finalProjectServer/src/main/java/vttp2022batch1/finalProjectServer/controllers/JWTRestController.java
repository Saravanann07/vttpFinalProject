package vttp2022batch1.finalProjectServer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vttp2022batch1.finalProjectServer.models.AuthenticationRequest;
import vttp2022batch1.finalProjectServer.models.AuthenticationResponse;
import vttp2022batch1.finalProjectServer.services.AppUserDetailsService;
import vttp2022batch1.finalProjectServer.utils.JwtUtil;

@RestController
@RequestMapping("")
public class JWTRestController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService userDetailsService;



    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping(path="/authenticate", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
      
        try {
            System.out.println(">>>> in /authenticate");
            System.out.println(">>>>> password: " + authenticationRequest.getPassword());
            System.out.println(">>>> email: " + authenticationRequest.getEmail());
            System.out.println(">>>> username: " + authenticationRequest.getUsername());
            
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
	
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	
		final String token = jwtTokenUtil.generateToken(userDetails);

        final Integer userId = userDetailsService.getUserId(authenticationRequest.getUsername());
	
		return ResponseEntity.ok(new AuthenticationResponse("Bearer "+ token, userId, authenticationRequest.getUsername()));
    }

    private void authenticate(String username, String password) throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encryptedPassword = encoder.encode(password);
       System.out.println(encryptedPassword);
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e); 
        }
    }


    
}
