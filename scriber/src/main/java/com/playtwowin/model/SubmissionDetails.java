package com.playtwowin.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "submissionDetails")
public class SubmissionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "detailId")
	private int detailId;

	@Column(name = "wordCount")
	private int wordCount;

	@Column(name = "compliantWordCount")
	private int compliantWordCount;

	@Column(name = "nonCompliantWordCount")
	private int nonCompliantWordCount;

	@Column(name = "sentimentScore")
	private String sentimentScore;

	@Column(name = "confidencePercent")
	private double confidencePercent;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "submissionId")
	private SubmittedFile submittedFile;

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public int getCompliantWordCount() {
		return compliantWordCount;
	}

	public void setCompliantWordCount(int compliantWordCount) {
		this.compliantWordCount = compliantWordCount;
	}

	public int getNonCompliantWordCount() {
		return nonCompliantWordCount;
	}

	public void setNonCompliantWordCount(int nonCompliantWordCount) {
		this.nonCompliantWordCount = nonCompliantWordCount;
	}

	public String getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(String sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	public double getConfidencePercent() {
		return confidencePercent;
	}

	public void setConfidencePercent(double confidencePercent) {
		this.confidencePercent = confidencePercent;
	}

	public SubmittedFile getSubmittedFile() {
		return submittedFile;
	}

	public void setSubmittedFile(SubmittedFile submittedFile) {
		this.submittedFile = submittedFile;
	}

	public SubmissionDetails() {
		super();
	}

	@Override
	public String toString() {
		return "SubmissionDetails [detailId=" + detailId + ", wordCount=" + wordCount + ", compliantWordCount="
				+ compliantWordCount + ", nonCompliantWordCount=" + nonCompliantWordCount + ", sentimentScore="
				+ sentimentScore + ", confidencePercent=" + confidencePercent + ", submittedFile=" + submittedFile
				+ "]";
	}

	
}
