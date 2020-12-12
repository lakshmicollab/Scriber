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

	private enum sentimentScore {
		POSITIVE, NEGATIVE, NEUTRAL, MIXED
	}

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
	@Enumerated(EnumType.STRING)
	private sentimentScore sentimentScore;
	
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

	public SubmittedFile getSubmittedFile() {
		return submittedFile;
	}

	public void setSubmittedFile(SubmittedFile submittedFile) {
		this.submittedFile = submittedFile;
	}

	public sentimentScore getSentimentScore() {
		return sentimentScore;
	}

	public void setSentimentScore(sentimentScore sentimentScore) {
		this.sentimentScore = sentimentScore;
	}

	public SubmissionDetails(int detailId, int wordCount, int compliantWordCount, int nonCompliantWordCount,
			SubmittedFile submittedFile, com.playtwowin.model.SubmissionDetails.sentimentScore sentimentScore) {
		super();
		this.detailId = detailId;
		this.wordCount = wordCount;
		this.compliantWordCount = compliantWordCount;
		this.nonCompliantWordCount = nonCompliantWordCount;
		this.submittedFile = submittedFile;
		this.sentimentScore = sentimentScore;
	}

	@Override
	public String toString() {
		return "SubmissionDetails [detailId=" + detailId + ", wordCount=" + wordCount + ", compliantWordCount="
				+ compliantWordCount + ", nonCompliantWordCount=" + nonCompliantWordCount + ", submittedFile="
				+ submittedFile + ", sentimentScore=" + sentimentScore + "]";
	}

	public SubmissionDetails() {
		super();
	}

}
