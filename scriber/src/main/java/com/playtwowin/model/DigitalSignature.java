package com.playtwowin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DigitalSignature")
public class DigitalSignature {

	@Id
	@GeneratedValue
	@Column(name = "signatureId")
	private int signatureId;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "title")
	private String title;
	
	@Column(name = "affiliation")
	private String affiliation;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "website")
	private String website;
	
	@Column(name = "socialmediaHandle")
	private String socialmediaHandle;

	@OneToOne
	@JoinColumn(name = "advisorId")
	private Advisor advisor;

	public int getSignatureId() {
		return signatureId;
	}

	public void setSignatureId(int signatureId) {
		this.signatureId = signatureId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	public DigitalSignature(int signatureId, String lastName, String firstName, String title, String affiliation,
			String email, String website, String socialmediaHandle, Advisor advisor) {
		super();
		this.signatureId = signatureId;
		this.lastName = lastName;
		this.firstName = firstName;
		this.title = title;
		this.affiliation = affiliation;
		this.email = email;
		this.website = website;
		this.socialmediaHandle = socialmediaHandle;
		this.advisor = advisor;
	}

	@Override
	public String toString() {
		return "DigitalSignature [signatureId=" + signatureId + ", lastName=" + lastName + ", firstName=" + firstName
				+ ", title=" + title + ", affiliation=" + affiliation + ", email=" + email + ", website=" + website
				+ ", socialmediaHandle=" + socialmediaHandle + ", advisor=" + advisor + "]";
	}
}
