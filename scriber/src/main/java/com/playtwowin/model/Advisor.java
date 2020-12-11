package com.playtwowin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="Advisor")
public class Advisor {
	
	private enum advisorType{
		ADMIN, BASIC
	};
	
	@Id
	@GeneratedValue
	@Column(name="advisorId")
	private int advisorId;
	
	@Column(name="approvedName")
	private String approvedName;
	
	@Column(name="advisorType")
	@Enumerated(EnumType.STRING)
	private advisorType type;
	
	@Column(name="partyAffiliation")
	private String partyAffiliation;
	
	@Column(name="website")
	private String website;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phoneNumber")
	private String phoneNumber;
	
	@Column(name="businessAddress")
	private String businessAddress;

	@ManyToOne
	@JoinColumn(name="affiliateId")
	private Affiliate affiliate;
	
	@OneToOne
	@JoinColumn(name="signatureId")
	private DigitalSignature digitalSignature;

	public int getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(int advisorId) {
		this.advisorId = advisorId;
	}

	public String getApprovedName() {
		return approvedName;
	}

	public void setApprovedName(String approvedName) {
		this.approvedName = approvedName;
	}

	public advisorType getType() {
		return type;
	}

	public void setType(advisorType type) {
		this.type = type;
	}

	public String getPartyAffiliation() {
		return partyAffiliation;
	}

	public void setPartyAffiliation(String partyAffiliation) {
		this.partyAffiliation = partyAffiliation;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public Affiliate getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(Affiliate affiliate) {
		this.affiliate = affiliate;
	}

	public DigitalSignature getDigitalSignature() {
		return digitalSignature;
	}

	public void setDigitalSignature(DigitalSignature digitalSignature) {
		this.digitalSignature = digitalSignature;
	}

	public Advisor(int advisorId, String approvedName, advisorType type, String partyAffiliation, String website,
			String email, String phoneNumber, String businessAddress, Affiliate affiliate,
			DigitalSignature digitalSignature) {
		super();
		this.advisorId = advisorId;
		this.approvedName = approvedName;
		this.type = type;
		this.partyAffiliation = partyAffiliation;
		this.website = website;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.businessAddress = businessAddress;
		this.affiliate = affiliate;
		this.digitalSignature = digitalSignature;
	}

	@Override
	public String toString() {
		return "Advisor [advisorId=" + advisorId + ", approvedName=" + approvedName + ", type=" + type
				+ ", partyAffiliation=" + partyAffiliation + ", website=" + website + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", businessAddress=" + businessAddress + ", affiliate=" + affiliate
				+ ", digitalSignature=" + digitalSignature + "]";
	}
	
}
