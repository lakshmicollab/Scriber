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
@Table(name="SubmittedFiles")
public class SubmittedFile {
	
	private enum complianceStatus{
		APPROVED, PENDING, REJECTED
	};
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="userId")
	private int userId;
	
	@Column(name="fileName")
	private String fileName;
	
	@Column(name="complianceStatus")
	@Enumerated(EnumType.STRING)
	private complianceStatus status;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="detailId")
	private SubmissionDetails submissionDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="processId")
	private ProcessCompletion processCompletion;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public complianceStatus getStatus() {
		return status;
	}

	public void setStatus(complianceStatus status) {
		this.status = status;
	}

	public SubmissionDetails getSubmissionDetails() {
		return submissionDetails;
	}

	public void setSubmissionDetails(SubmissionDetails submissionDetails) {
		this.submissionDetails = submissionDetails;
	}

	public ProcessCompletion getProcessCompletion() {
		return processCompletion;
	}

	public void setProcessCompletion(ProcessCompletion processCompletion) {
		this.processCompletion = processCompletion;
	}

	@Override
	public String toString() {
		return "SubmittedFile [userId=" + userId + ", fileName=" + fileName + ", status=" + status
				+ ", submissionDetails=" + submissionDetails + ", processCompletion=" + processCompletion + "]";
	}

	
	
}
