package com.antares.kawalpemilu.model;

public class DetailTPSModel {
	private String id;
	private String candidate1;
	private String candidate2;
	private String valid;
	private String invalid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCandidate1() {
		return candidate1;
	}
	public void setCandidate1(String candidate1) {
		this.candidate1 = candidate1;
	}
	public String getCandidate2() {
		return candidate2;
	}
	public void setCandidate2(String candidate2) {
		this.candidate2 = candidate2;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public String getInvalid() {
		return invalid;
	}
	public void setInvalid(String invalid) {
		this.invalid = invalid;
	}
	
	public DetailTPSModel(String [] r) {
		setId(r[0]);
		setCandidate1(r[2]);
		setCandidate2(r[3]);
		setValid(r[4]);
		setInvalid(r[5]);
	}
}
