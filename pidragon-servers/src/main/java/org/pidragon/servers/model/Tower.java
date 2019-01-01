package org.pidragon.servers.model;

import java.io.Serializable;
import java.util.List;

import org.toasthub.core.general.api.View;

import com.fasterxml.jackson.annotation.JsonView;

public class Tower implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String n;
	private List<Switch> switches;
	
	public Tower(String n) {
		this.setN(n);
	}

	@JsonView({View.Member.class})
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}

	@JsonView({View.Member.class})
	public List<Switch> getSwitches() {
		return switches;
	}
	public void setSwitches(List<Switch> switches) {
		this.switches = switches;
	}
	
	
}
