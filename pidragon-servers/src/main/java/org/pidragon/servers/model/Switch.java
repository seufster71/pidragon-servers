package org.pidragon.servers.model;

import java.io.Serializable;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

public class Switch implements Serializable {

	private static final long serialVersionUID = 1L;
	public final static String HIGH = "HIGH";
	public final static String LOW = "LOW";
	
	private String n;
	private String p;
	
	public Switch(String n, String p) {
		this.n = n;
		this.p = p;
	}
	
	@JsonView({View.Member.class})
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	
	@JsonView({View.Member.class})
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	
	public String getState() {
		return getP();
	}
	public void setState(String v){
		if (HIGH.equals(v)){
			setP("ON");
		} else {
			setP("OFF");
		}
		
	}
	
}
