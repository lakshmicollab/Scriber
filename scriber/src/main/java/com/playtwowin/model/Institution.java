package com.playtwowin.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Institution")
public class Institution {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "institutionId")
	private int institutionId;

	@Column(name = "instituteName")
	private String instituteName;

	@Column(name = "certification")
	private String certification;

	@Column(name = "localAddress")
	private String localAddress;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "zipCode")
	private String zipCode;

	@Column(name = "country")
	private String country;

	@Column(name = "email")
	private String email;

	@Column(name = "instituteWebsite")
	private String instituteWebsite;

	@Column(name = "notes")
	private String notes;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
	private List<Affiliate> affiliate;

	public int getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(int institutionId) {
		this.institutionId = institutionId;
	}

	public String getInstituteName() {
		return instituteName;
	}

	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstituteWebsite() {
		return instituteWebsite;
	}

	public void setInstituteWebsite(String instituteWebsite) {
		this.instituteWebsite = instituteWebsite;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Affiliate> getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(List<Affiliate> affiliate) {
		this.affiliate = affiliate;
	}

	public Institution(int institutionId, String instituteName, String certification, String localAddress, String city,
			String state, String zipCode, String country, String email, String instituteWebsite, String notes,
			List<Affiliate> affiliate) {
		super();
		this.institutionId = institutionId;
		this.instituteName = instituteName;
		this.certification = certification;
		this.localAddress = localAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.country = country;
		this.email = email;
		this.instituteWebsite = instituteWebsite;
		this.notes = notes;
		this.affiliate = affiliate;
	}

	@Override
	public String toString() {
		return "Institution [institutionId=" + institutionId + ", instituteName=" + instituteName + ", certification="
				+ certification + ", localAddress=" + localAddress + ", city=" + city + ", state=" + state
				+ ", zipCode=" + zipCode + ", country=" + country + ", email=" + email + ", instituteWebsite="
				+ instituteWebsite + ", notes=" + notes + ", affiliate=" + affiliate + "]";
	}

}
