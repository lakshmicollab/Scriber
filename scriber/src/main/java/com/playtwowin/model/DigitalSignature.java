package com.playtwowin.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DigitalSignature")
public class DigitalSignature {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "signatureId")
	private int signatureId;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "title")
	private String title;

	@Column(name = "affiliation")
	private String affiliation;

	@Column(name = "phonenumber")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "website")
	private String website;

	@Column(name = "socialmediaHandle")
	private String socialmediaHandle;

	@Column(name = "address")
	private String address;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "advisorId")
	private Advisor advisor;

	public int getSignatureId() {
		return signatureId;
	}

	public DigitalSignature(int signatureId, String fullName, String title, String affiliation, String phoneNumber,
			String email, String website, String socialmediaHandle, String address, Advisor advisor) {
		super();
		this.signatureId = signatureId;
		this.fullName = fullName;
		this.title = title;
		this.affiliation = affiliation;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.website = website;
		this.socialmediaHandle = socialmediaHandle;
		this.address = address;
		this.advisor = advisor;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setSignatureId(int signatureId) {
		this.signatureId = signatureId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAffiliation() {
		return affiliation;
	}

	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getSocialmediaHandle() {
		return socialmediaHandle;
	}

	public void setSocialmediaHandle(String socialmediaHandle) {
		this.socialmediaHandle = socialmediaHandle;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	public DigitalSignature() {
		super();
	}

	@Override
	public String toString() {
		return "DigitalSignature [signatureId=" + signatureId + ", fullName=" + fullName + ", title=" + title
				+ ", affiliation=" + affiliation + ", phoneNumber=" + phoneNumber + ", email=" + email + ", website="
				+ website + ", socialmediaHandle=" + socialmediaHandle + ", address=" + address + ", advisor=" + advisor
				+ "]";
	}

}
