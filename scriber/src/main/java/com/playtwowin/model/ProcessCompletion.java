package com.playtwowin.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="processCompletion")
public class ProcessCompletion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="processId")
	private int processId;
	
	@Column(name="confidencePercent")
	private double confidencePercent;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="submissionId")
	private SubmittedFile submittedFile;
	
}
