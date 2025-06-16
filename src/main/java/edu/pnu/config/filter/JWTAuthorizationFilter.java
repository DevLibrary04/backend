package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import edu.pnu.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter{
	private final MemberRepository memberRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(token == null || !token.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String jwtTok = token.replace("Bearer ", "");
		String username = JWTUtil.getClaim(jwtTok, "username");
		
		Optional<Member> opt = memberRepo.findByUsername(username);
		if(!opt.isPresent()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		Member findMember = opt.get();
		
		User user = new User(findMember.getUsername(), findMember.getPassword(), AuthorityUtils.createAuthorityList(findMember.getRole().toString()));
		Authentication authen = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authen);
		filterChain.doFilter(request, response);
	}
}