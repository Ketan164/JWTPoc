package com.poc.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Ketan
 * @Description This util class created for generating and validatig JWT Token
 *
 */
@Service
public class JWTUtils 
{
	//this variable is use for generate token using above keyword
	private String SECRET_KEY = "avnee";
	
	
	//
	public String generateToken(UserDetails userDetails)
	{
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims, userDetails.getUsername());
		
	}
	
	public String createToken(Map<String, Object> claims, String subject)
	{
		return Jwts.builder().
				setClaims(claims).
					setSubject(subject).
						setIssuedAt(new Date(System.currentTimeMillis())).
							setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 10)).
								signWith(SignatureAlgorithm.HS256, SECRET_KEY).
									compact();
		
	}
	
	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String userName = extractUserame(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
	
	public String extractUserame(String token)
	{
		return extractClaim(token, Claims::getSubject);
		
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver ) 
	{
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
		
	}
	
	private Claims extractAllClaims(String token)
	{
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
	public Date extractExpiration(String token)
	{
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
