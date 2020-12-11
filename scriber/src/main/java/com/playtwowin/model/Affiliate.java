package com.playtwowin.model;

import java.io.File;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Affiliate")
public class Affiliate {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "affiliateId")
	private int affiliateId;

	@Column(name = "logo")
	private File logo; // may need different data type

	@Column(name = "affiliateInstitute")
	private String affiliateInstitute;

	@ManyToOne(fetch = FetchType.LAZY)
	private Institution institution;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "affiliate")
	private List<Advisor> advisor;

	public int getAffiliateId() {
		return affiliateId;
	}

	public void setAffiliateId(int affiliateId) {
		this.affiliateId = affiliateId;
	}

	public File getLogo() {
		return logo;
	}

	public void setLogo(File logo) {
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

	public List<Advisor> getAdvisor() {
		return advisor;
	}

	public void setAdvisor(List<Advisor> advisor) {
		this.advisor = advisor;
	}

	public Affiliate(int affiliateId, File logo, String affiliateInstitute, Institution institution,
			List<Advisor> advisor) {
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
