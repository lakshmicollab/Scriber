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
@Table(name = "SubmittedFiles")
public class SubmittedFile {

	private enum complianceStatus {
		APPROVED, PENDING, REJECTED
	};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fileId")
	private int fileId;

	@Column(name = "fileName")
	private String fileName;

	@Column(name = "complianceStatus")
	@Enumerated(EnumType.STRING)
	private complianceStatus status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "detailId")
	private SubmissionDetails submissionDetails;

	public SubmittedFile(int fileId, String fileName, complianceStatus status, SubmissionDetails submissionDetails) {
		super();
		this.fileId = fileId;
		this.fileName = fileName;
		this.status = status;
		this.submissionDetails = submissionDetails;
	}

	public SubmittedFile() {
		super();
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
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

	@Override
	public String toString() {
		return "SubmittedFile [fileId=" + fileId + ", fileName=" + fileName + ", status=" + status
				+ ", submissionDetails=" + submissionDetails + "]";
	}
	
}
