package com.playtwowin.model;

public class OverViewResponse {
	int id;
	String fileName;
	String fullName;
	String address;

	public OverViewResponse(int id, String fileName, String fullName, String address) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fullName = fullName;
		this.address = address;
	}

	public OverViewResponse() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
