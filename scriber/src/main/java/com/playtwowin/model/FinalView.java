package com.playtwowin.model;

public class FinalView {

	int id;
	String fileName;
	String fullName;
	String address;
	String email;
	String company;
	String phoneNumber;
	String Status;
	String comments;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public FinalView(int id, String fileName, String fullName, String address, String email, String company,
			String phoneNumber, String status, String comments) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fullName = fullName;
		this.address = address;
		this.email = email;
		this.company = company;
		this.phoneNumber = phoneNumber;
		Status = status;
		this.comments = comments;
	}

	public FinalView() {
		super();
	}

	@Override
	public String toString() {
		return "FinalView [id=" + id + ", fileName=" + fileName + ", fullName=" + fullName + ", address=" + address
				+ ", email=" + email + ", company=" + company + ", phoneNumber=" + phoneNumber + ", Status=" + Status
				+ ", comments=" + comments + "]";
	}

}
