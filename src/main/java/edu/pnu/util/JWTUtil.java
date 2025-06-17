package edu.pnu.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {
	public static final String JWT_KEY = "edu.pnu.jwtkey";
	private static final long ACCESS_TOKEN_MSEC = 100*(1000*60);
	private static final String claimName = "username";
	private static final String prefix = "Bearer ";
	
	// prefix 뺴고 클린 업
	private static String getJWTSource(String token) {
		if(token.startsWith(prefix)) return token.replace("Bearer ", "");
		return token;
	}
	
	// JWT 생성
	public static String getJWT(String username) {
		String src = JWT.create()
				.withClaim(claimName, username)
				.withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_MSEC))
				.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix + src;
		
		
	}
	
	// authorize 된 토큰으로부터 key가 username인 데이터 가져오기
	public static String getClaim(String token, String claimName) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY))
				.build()
				.verify(tok)
				.getClaim(claimName)
				.asString();
	}
	
	// 만료기간 검증 - 만료되지 않았다면 true 반환
	public static boolean isExpired(String token) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build().verify(tok).getExpiresAt().before(new Date());
	}
}
