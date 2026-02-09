package com.enterprise.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.enterprise.entity.User;

public class Localuser implements UserDetails{
	
	   private final User us;

	    public Localuser(User us) {
	        this.us = us;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return List.of(
	            new SimpleGrantedAuthority("ROLE_" + us.getRole().name())
	        );
	    }

	    @Override
	    public String getPassword() {
	        return us.getPassword();   // ✅ MUST return encrypted password
	    }

	    @Override
	    public String getUsername() {
	        return us.getUsername();   // ✅ MUST return username
	    }

	    @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

}
