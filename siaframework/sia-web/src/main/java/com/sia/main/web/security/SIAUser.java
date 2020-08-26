package com.sia.main.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.sia.main.domain.Pengguna;

@SuppressWarnings("serial")
public class SIAUser extends User{
	
	private Pengguna userDetail;

	public SIAUser(String username, String password, boolean enabled, 
			boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, 
			Collection<? extends GrantedAuthority> authorities, Pengguna userDetail) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.userDetail = userDetail;
	}

	public SIAUser(String username, String password, Collection<? extends GrantedAuthority> authorities, Pengguna userDetail) {
		super(username, password, authorities);
		this.userDetail = userDetail;
	}

	public Pengguna getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(Pengguna userDetail) {
		this.userDetail = userDetail;
	}
	
}
