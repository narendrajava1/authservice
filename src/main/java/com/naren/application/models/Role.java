//package com.naren.application.models;
//
//import java.util.List;
//
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//@Document("Role")
//public class Role  {
//	private static final long serialVersionUID = 1L;
//	
//	@Field(name="name")
//	private String name;
//	
//	private List<Permission> permissions;
//	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "permission_role", joinColumns = {
//			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
//					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
//	private List<Permission> permissions;
//	
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public List<Permission> getPermissions() {
//		return permissions;
//	}
//
//	public void setPermissions(List<Permission> permissions) {
//		this.permissions = permissions;
//	}
//}