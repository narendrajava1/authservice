//package com.naren.application.models;
//
//import java.util.Collection;
//
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//@Document("User")
//public class User  implements UserDetails {
//
//	private static final long serialVersionUID = 1L;
//	@Field(name = "user_name")
//	private String username;
//	@Field(name = "password")
//	private String password;
//	@Field(name = "email")
//	private String email;
//	@Field(name = "enabled")
//	private boolean enabled;
//
//	@Field(name = "account_locked")
//	private boolean accountNonLocked;
//
//	@Field(name = "account_expired")
//	private boolean accountNonExpired;
//
//	@Field(name = "credentials_expired")
//	private boolean credentialsNonExpired;
//	
//	private List<Role> roles;
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return !accountNonExpired;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return !accountNonLocked;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return !credentialsNonExpired;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return enabled;
//	}
//	
//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//	
//}
