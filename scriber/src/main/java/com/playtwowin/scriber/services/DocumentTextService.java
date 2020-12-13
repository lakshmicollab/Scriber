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
import com.playtwowin.model.DigitalSignature;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//Calls DetectDocumentText.
//Loads document from S3 bucket. Displays the document and bounding boxes around detected lines/words of text.



@Service
public class DocumentTextService {

    private static final long serialVersionUID = 1L;

    BufferedImage image;
    DetectDocumentTextResult result;

    public DocumentTextService(DetectDocumentTextResult documentResult, BufferedImage bufImage) throws Exception {
//        super();

        result = documentResult; // Results of text detection.
        image = bufImage; // The image containing the document.

    }

    public DocumentTextService(){}

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
    public DetectDocumentTextResult textDetector(ByteBuffer imageBytes){

        // Call DetectDocumentText
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                "https://textract.us-east-1.amazonaws.com", "us-east-1");
        AmazonTextract client = AmazonTextractClientBuilder.standard()
                .withEndpointConfiguration(endpoint).build();

        DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                .withDocument(new Document()
                        .withBytes(imageBytes));


        DetectDocumentTextResult result = client.detectDocumentText(request);
//        DocumentTextService result;
        return result;
    }
    
    //used to create a digital signature and return through the API
    public DigitalSignature BuildaSignature(ArrayList<Entity> list, DigitalSignature ds) {
    	
		/*
		 * for the list of Types, refer to the training sheets or aws comprehend
		 */
    	for(Entity e : list) {
    		//type to be changed for "FULLNAME"
			if(e.getType().equals("PERSON")) {
				ds.setFullName(e.getText());
			}
			if(e.getType().equals("TITLE")) {
				ds.setTitle(e.getText());
			}
			//train for "AFFILIATE"
			//to be used in a search to match affiliation and then get an ID
			if(e.getType().equals("OTHER")) {
				ds.setAffiliation(e.getText());
			}
			//to change into "PHONENUMBER"
			if(e.getType().equals("QUANTITY")) {
				ds.setPhoneNumber(e.getText());
			}
			if(e.getType().equals("EMAIL")) {
				ds.setEmail(e.getText());
			}
			if(e.getType().equals("WEBSITE")) {
				ds.setWebsite(e.getText());
			}
			if(e.getType().equals("SOCIALMEDIAHANDLE")) {
				ds.setSocialmediaHandle(e.getText());
			}
			if(e.getType().equals("LOCATION")) {
				ds.setAddress(e.getText());
			}
		}
    	return ds;
    }
    
}
