package edu.pnu.config;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.CustomMyUtil;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	@Lazy
	private PasswordEncoder encoder;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		log.info("OAuth2SuccessHandler:onAuthenticationSuccess");
		OAuth2User user = (OAuth2User)authentication.getPrincipal();
		String username = CustomMyUtil.getUsernameFromOAuth2User(user);
		if(username == null) {
			log.error("onAuthenticationSuccess:Cannot generate username from oauth2user!");
			throw new ServletException("Cannot generate username from oauth2user!");
		}
		log.info("onAuthenticationSuccess:" + username);
		
		memberRepo.save(Member.builder()
				.username(username)
				.password(encoder.encode("abcd"))
				.role(Role.ROLE_MEMBER)
				.enabled(true)
				.build());
		
		// postman으로 테스트하는 버전
//		String jwtToken = JWTUtil.getJWT(username);
//		response.setHeader("Authorization", jwtToken);
		
		// react와 연동하는 버전
		String jwtToken = JWTUtil.getJWT(username);
		Cookie jwtCookie = new Cookie("jwtToken", URLEncoder.encode(jwtToken, "utf-8"));
		jwtCookie.setHttpOnly(true);
		jwtCookie.setPath("/");
		jwtCookie.setSecure(false);
		jwtCookie.setMaxAge(10);
		response.addCookie(jwtCookie);
		
		response.sendRedirect("http://localhost:5173/oauth2/callback");
	}
}