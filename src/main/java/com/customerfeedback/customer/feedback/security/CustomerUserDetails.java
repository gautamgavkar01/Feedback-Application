package com.customerfeedback.customer.feedback.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.customerfeedback.customer.feedback.entity.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomerUserDetails implements UserDetails{
	
	private final UserEntity userEntity;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().toString()));
				
	}

	@Override
	public String getPassword() {
		return userEntity.getPassword();
	}

	@Override
	public String getUsername() {
		return userEntity.getUsername();
	}
	
	@Override public boolean isAccountNonExpired() { 
		return true; 
	}
	
    @Override public boolean isAccountNonLocked() { 
    	return true; 
    }
    
    @Override public boolean isCredentialsNonExpired() { 
    	return true; 
    }
    
    @Override public boolean isEnabled() { 
    	return true; 
    }

}

















/*
 * 
 * 
 * 
 * 
 * 
 * package com.example.feedbacksystem.security;

import com.example.feedbacksystem.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class CustomerUserDetails implements UserDetails {

    private UserEntity user;

    public CustomerUserDetails(UserEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().toString())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

 * 
 * 
 * */
