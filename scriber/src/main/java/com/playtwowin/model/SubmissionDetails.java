package com.playtwowin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="submissionDetails")
public class SubmissionDetails {
	
	@Id
	@GeneratedValue
	@Column(name="detailId")
	private int detailId;
	
	@Column(name="wordCount")
	private int wordCount;
	
	@Column(name="compliantWordCount")
	private int compliantWordCount;
	
	@Column(name="nonCompliantWordCount")
	private int nonCompliantWordCount;
	
	@OneToOne
	@JoinColumn(name="submissionId")
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

	public SubmissionDetails(int detailId, int wordCount, int compliantWordCount, int nonCompliantWordCount,
			SubmittedFile submittedFile) {
		super();
		this.detailId = detailId;
		this.wordCount = wordCount;
		this.compliantWordCount = compliantWordCount;
		this.nonCompliantWordCount = nonCompliantWordCount;
		this.submittedFile = submittedFile;
	}

	@Override
	public String toString() {
		return "SubmissionDetails [detailId=" + detailId + ", wordCount=" + wordCount + ", compliantWordCount="
				+ compliantWordCount + ", nonCompliantWordCount=" + nonCompliantWordCount + ", submittedFile="
				+ submittedFile + "]";
	}
}
