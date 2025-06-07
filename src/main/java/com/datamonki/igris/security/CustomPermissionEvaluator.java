package com.datamonki.igris.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class CustomPermissionEvaluator  implements PermissionEvaluator{
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (authentication == null || permission == null) {
			return false;
		}
		return authentication.getAuthorities().contains(new SimpleGrantedAuthority(permission.toString()));
	}
	
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
    
}
