package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService {
	@Autowired
	private MemberRepository memberRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memberRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		return User.builder().username(member.getUsername())
							.password(member.getPassword())
							.authorities(AuthorityUtils.createAuthorityList(member.getRole().toString()))
							.disabled(!member.isEnabled())
							.build();
	}

}