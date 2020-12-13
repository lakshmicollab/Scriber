package com.playtwowin.scriber.controller;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.Entity;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.playtowin.repository.DigitalSignatureRepo;
import com.playtwowin.model.DigitalSignature;
import com.playtwowin.scriber.services.DocumentTextService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScriberController {

	// Create credentials using a provider chain. For more information, see
	// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
	private static AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

	@Autowired
	DocumentTextService documentTextService;

	@Autowired
	DigitalSignatureRepo digitalSignatureRepo;

//    curl -k -X POST -F 'image=@/Pictures/running_cheetah.jpg' -v  http://localhost:8080/upload/
	@PostMapping("/upload")
	public String fileUploader(@RequestParam("file") MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		return fileName;
	}

	// testing out AWS Textract
	/*
	 * @PostMapping("/doc-upload") public ResponseEntity<ArrayList<Entity>>
	 * documentUploaderSimple(@RequestParam("file") MultipartFile multipartFile)
	 * throws Exception {
	 * 
	 * // Serialize the uploaded file ByteBuffer imageBytes =
	 * documentTextService.uploadFile(multipartFile);
	 * 
	 * // extract the text DetectDocumentTextResult result =
	 * documentTextService.textDetector(imageBytes);
	 * 
	 * List<Block> resultBlockList = result.getBlocks(); ArrayList<Entity>
	 * entityList = new ArrayList<Entity>();
	 * 
	 * System.out.println("[This Content was Extracted from the Document]");
	 * 
	 * // prints the documents content to the console for (Block b :
	 * resultBlockList) if (b.getText() != null && b.getBlockType().equals("LINE"))
	 * System.out.println(b.getText());
	 * 
	 * System.out.println("[Document Text Detection Complete]");
	 * 
	 * // Testing out aws comprehend
	 * 
	 * System.out.println("Start: Sentiment Analysis Test 1");
	 * sentimentAnalysis(resultBlockList.get(1).getText());
	 * System.out.println("End: Sentiment Analysis Test 1\n");
	 * 
	 * 
	 * System.out.println("[LINE Entity Detection Start]"); for (Block b :
	 * resultBlockList) { if (b.getText() != null &&
	 * b.getBlockType().equals("LINE")) { // System.out.println(b.getText()); //
	 * entityDetection(b.getText()); //entityList.add(b.getText());
	 * entityDetection(b.getText(), entityList); //adds the entities to the list
	 * initialized above } } System.out.println("[LINE Entity Detection End]");
	 * 
	 * 
	 * 
	 * System.out.println("\n\n[WORD Entity Detection Start]"); for (Block b :
	 * resultBlockList) if (b.getText() != null && b.getBlockType().equals("WORD"))
	 * { System.out.println(b.getText()); entityDetection(b.getText()); }
	 * System.out.println("[WORD Entity Detection End]");
	 * 
	 * 
	 * return new ResponseEntity<ArrayList<Entity>>(entityList, HttpStatus.OK);
	 * 
	 * }
	 */
	@PostMapping("/doc-upload")
	public ResponseEntity<DigitalSignature> documentUploaderSimple(@RequestParam("file") MultipartFile multipartFile)
			throws Exception {

		// Serialize the uploaded file
		ByteBuffer imageBytes = documentTextService.uploadFile(multipartFile);

		// extract the text
		DetectDocumentTextResult result = documentTextService.textDetector(imageBytes);

		List<Block> resultBlockList = result.getBlocks();
		ArrayList<Entity> entityList = new ArrayList<Entity>();
		DigitalSignature ds = new DigitalSignature();

		/*
		 * System.out.println("[This Content was Extracted from the Document]"); //
		 * prints the documents content to the console for (Block b : resultBlockList) {
		 * if (b.getText() != null && b.getBlockType().equals("LINE"))
		 * System.out.println(b.getText()); }
		 * System.out.println("[Document Text Detection Complete]");
		 */

		// Testing out aws comprehend
		/*
		 * System.out.println("Start: Sentiment Analysis Test 1");
		 * sentimentAnalysis(resultBlockList.get(1).getText());
		 * System.out.println("End: Sentiment Analysis Test 1\n");
		 */

		System.out.println("[LINE Entity Detection Start]");
		for (Block b : resultBlockList) {
			if (b.getText() != null && b.getBlockType().equals("LINE")) {
				//System.out.println(b.getText());
				entityDetection(b.getText(), entityList); // adds the entities to the list initialized above
			}
		}
		System.out.println("[LINE Entity Detection End]");

		ds = documentTextService.BuildaSignature(entityList, ds);
		
		/*
		 * System.out.println("\n\n[WORD Entity Detection Start]"); for (Block b :
		 * resultBlockList) if (b.getText() != null && b.getBlockType().equals("WORD"))
		 * { System.out.println(b.getText()); entityDetection(b.getText()); }
		 * System.out.println("[WORD Entity Detection End]");
		 */
		
		digitalSignatureRepo.save(ds);
		return new ResponseEntity<DigitalSignature>(ds, HttpStatus.OK);

	}

	public static AmazonComprehend amazonComprehend() {
		AmazonComprehend comprehendClient = AmazonComprehendClientBuilder.standard().withCredentials(awsCreds)
				.withRegion("us-west-2").build();
		return comprehendClient;
	}

	public void sentimentAnalysis(String text) {

		AmazonComprehend comprehendClient = amazonComprehend();

		// Call detectSentiment API
		System.out.println("Calling DetectSentiment");
		DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text)
				.withLanguageCode("en");
		DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
		System.out.println(detectSentimentResult);
		System.out.println("End of DetectSentiment\n");
		System.out.println("Done");
	}

	// original method of entityDetection(String text)
	/*
	 * public void entityDetection(String text) {
	 * 
	 * AmazonComprehend comprehendClient = amazonComprehend();
	 * 
	 * // Call detectEntities API DetectEntitiesRequest detectEntitiesRequest = new
	 * DetectEntitiesRequest().withText(text).withLanguageCode("en");
	 * DetectEntitiesResult detectEntitiesResult =
	 * comprehendClient.detectEntities(detectEntitiesRequest);
	 * detectEntitiesResult.getEntities().forEach(System.out::println);
	 * 
	 * }
	 */

	public void entityDetection(String text, ArrayList<Entity> list) {

		AmazonComprehend comprehendClient = amazonComprehend();

		// These Arn(s) are used to connect to endpoints issued through Comprehend
		// final String fullnameArn = "<insert Arn here>";

		// Call detectEntities API
		DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(text).withLanguageCode("en");// .withEndpointArn(fullnameArn);
		DetectEntitiesResult detectEntitiesResult = comprehendClient.detectEntities(detectEntitiesRequest);
		detectEntitiesResult.getEntities().forEach(System.out::println);
		list.addAll(detectEntitiesResult.getEntities());
	}

}
