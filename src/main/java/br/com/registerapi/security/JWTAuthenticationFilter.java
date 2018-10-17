package br.com.registerapi.security;

import static br.com.registerapi.security.SecurityConstants.EXPIRATION_TIME;
import static br.com.registerapi.security.SecurityConstants.HEADER_STRING;
import static br.com.registerapi.security.SecurityConstants.SECRET;
import static br.com.registerapi.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.registerapi.dto.CredenciaisDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		try {
			
			CredenciaisDTO credenciais = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(credenciais.getEmail(), credenciais.getPassword(), new ArrayList<>()));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
		
		MyUserPrincipal myUserPrincipal = (MyUserPrincipal) auth.getPrincipal();
		
		String token = Jwts.builder()
				.setSubject(myUserPrincipal.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes()).compact();
		
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
	}
}
