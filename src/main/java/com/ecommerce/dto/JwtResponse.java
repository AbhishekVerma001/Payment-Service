package com.ecommerce.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	@JsonProperty("token")
	private  String jwttoken;
	
	public JwtResponse() {
		
	}

//	public JwtResponse(String jwttoken) {
//		this.jwttoken = jwttoken;
//	}
	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	

	public String getToken() {
		return this.jwttoken;
	}
}