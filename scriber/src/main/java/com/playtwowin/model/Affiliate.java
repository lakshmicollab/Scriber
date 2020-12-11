package com.playtwowin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="Afiliate")
public class Affiliate {
	
	@Id
	@GeneratedValue
	@Column(name="affiliateId")
	private int affiliateId;
	
	@Column(name="logo")
	private MultipartFile logo; //may need different data type
	
	@Column(name="affiliateInstitute")
	private String affiliateInstitute;
	
	@ManyToOne
	@Column(name="institutionId")
	private Institution institution;
	
	@OneToMany
	@Column(name="advisorId")
	private Advisor advisor;

	public int getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(int affiliateId) {
		this.affiliateId = affiliateId;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public String getAffiliateInstitute() {
		return affiliateInstitute;
	}

	public void setAffiliateInstitute(String affiliateInstitute) {
		this.affiliateInstitute = affiliateInstitute;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public Advisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Advisor advisor) {
		this.advisor = advisor;
	}

	public Affiliate(int affiliateId, MultipartFile logo, String affiliateInstitute, Institution institution,
			Advisor advisor) {
		super();
		this.affiliateId = affiliateId;
		this.logo = logo;
		this.affiliateInstitute = affiliateInstitute;
		this.institution = institution;
		this.advisor = advisor;
	}

	@Override
	public String toString() {
		return "Affiliate [affiliateId=" + affiliateId + ", logo=" + logo + ", affiliateInstitute=" + affiliateInstitute
				+ ", institution=" + institution + ", advisor=" + advisor + "]";
	}

}
