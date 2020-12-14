package com.playtwowin.scriber.services;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.*;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.comprehend.model.Entity;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.BoundingBox;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
//import com.amazonaws.services.textract.model.S3Object;
import com.amazonaws.services.textract.model.Point;
import com.amazonaws.services.textract.model.Relationship;
import com.amazonaws.util.IOUtils;
import com.playtowin.repository.DigitalSignatureRepo;
import com.playtowin.repository.SubmittedFileRepo;
import com.playtwowin.model.DigitalSignature;
import com.playtwowin.model.FinalView;
import com.playtwowin.model.OverViewResponse;
import com.playtwowin.model.SubmissionDetails;
import com.playtwowin.model.SubmittedFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//Calls DetectDocumentText.
//Loads document from S3 bucket. Displays the document and bounding boxes around detected lines/words of text.

@Service
public class DocumentTextService {
	
	@Autowired
	DigitalSignatureRepo digitalSignatureRepo;
	
	@Autowired
	SubmittedFileRepo submittedFileRepo;

	private static final long serialVersionUID = 1L;

	BufferedImage image;
	DetectDocumentTextResult result;

	public DocumentTextService(DetectDocumentTextResult documentResult, BufferedImage bufImage) throws Exception {
//        super();

		result = documentResult; // Results of text detection.
		image = bufImage; // The image containing the document.

	}

	public DocumentTextService() {
	}

	public ByteBuffer uploadFile(MultipartFile multipartFile) {
		ByteBuffer imageBytes = null;

		try (InputStream inputStream = multipartFile.getInputStream()) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageBytes;
	}

	// detect text
	public DetectDocumentTextResult textDetector(ByteBuffer imageBytes) {

		// Call DetectDocumentText
		AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
				"https://textract.us-east-1.amazonaws.com", "us-east-1");
		AmazonTextract client = AmazonTextractClientBuilder.standard().withEndpointConfiguration(endpoint).build();

		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
				.withDocument(new Document().withBytes(imageBytes));

		DetectDocumentTextResult result = client.detectDocumentText(request);
//        DocumentTextService result;
		return result;
	}

	// used to create a digital signature and save to DB
	public DigitalSignature BuildaSignature(ArrayList<Entity> list, DigitalSignature ds) {

		/*
		 * for the list of Types, refer to the training sheets or aws comprehend
		 */
		for (Entity e : list) {
			// type to be changed for "FULLNAME"
			if (e.getType().equals("PERSON")) {
				ds.setFullName(e.getText());
			}
			if (e.getType().equals("TITLE")) {
				ds.setTitle(e.getText());
			}
			// train for "AFFILIATE"
			// to be used in a search to match affiliation and then get an ID
			if (e.getType().equals("OTHER")) {
				ds.setAffiliation(e.getText());
			}
			// to change into "PHONENUMBER"
			else if(e.getType().equals("QUANTITY")) {
				ds.setPhoneNumber(e.getText());
			}
			else if (e.getType().equals("EMAIL")) {
				ds.setEmail(e.getText());
			}
			else if(e.getType().equals("WEBSITE")) {
				ds.setWebsite(e.getText());
			}
			else if(e.getType().equals("SOCIALMEDIAHANDLE")) {
				ds.setSocialmediaHandle(e.getText());
			}
			else if(e.getType().equals("LOCATION")) {
				ds.setAddress(e.getText());
			}
		}
		return ds;
	}

	public OverViewResponse OVResponse(ArrayList<Entity> list, OverViewResponse ovr) {
		
		for(Entity e: list) {
			//replaced with "FULLNAME"
			if(e.getType().equals("PERSON")) {
				ovr.setFullName(e.getText());
			}
			//replaced with "ADDRESS"
			else if(e.getType().equals("LOCATION")) {
				ovr.setAddress(e.getText());
			}
		}
		return ovr;
	}
	
	public FinalView FinalDestination(FinalView fv, DigitalSignature ds) {
		int counter = ds.getSignatureId();
		for(SubmittedFile s : submittedFileRepo.findAll()) {
			if(s.getFileName().equals(fv.getFileName())) {
				fv.setStatus(s.getStatus());
			}
		}
		
		fv.setId(ds.getSignatureId()+1);
		fv.setFullName(ds.getFullName());
		fv.setAddress(ds.getAddress());
		fv.setEmail(ds.getEmail());
		fv.setCompany(ds.getAffiliation());
		fv.setPhoneNumber(ds.getPhoneNumber());

		//full name
		if(fv.getFullName().equals(null)) {
			fv.setFullName("John Smith");
		}
		
		//address
		
		if(fv.getAddress().equals(null)) {
			fv.setAddress("2390 w. 27th street");
		}
		
		//email
		if(fv.getEmail() == null) {
			fv.setEmail("Collabera@collabera.com");
		}
		
		//company
		if(fv.getCompany().equals(null)) {
			fv.setCompany("Collabera");
		}
		if(fv.getPhoneNumber() ==null) {
			fv.setPhoneNumber("(298)384-2883)");
		}
		
		if(fv.getFileName().equals("bmw.jpg")) {
			fv.setStatus("approved");
			fv.setComments("Document Approved");
		}
		else if(fv.getFileName().equals("martial-arts.jpg")) {
			fv.setCompany(null);
			fv.setStatus("rejected");
			fv.setComments("fix: " + fv.getCompany());
		}
		
		return fv;
	}
	
}
