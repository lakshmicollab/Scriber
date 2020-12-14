package com.playtwowin.scriber.controller;

import java.awt.image.BufferedImage;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.comprehend.model.Entity;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionRequest;
import com.amazonaws.services.textract.model.StartDocumentTextDetectionResult;
import com.playtowin.repository.DigitalSignatureRepo;
import com.playtowin.repository.SubmissionDetailsRepo;
import com.playtowin.repository.SubmittedFileRepo;
import com.playtwowin.model.DigitalSignature;
import com.playtwowin.model.FinalView;
import com.playtwowin.model.ImageType;
import com.playtwowin.model.SubmittedFile;
import com.playtwowin.model.TextLine;
import com.playtwowin.model.OverViewResponse;
import com.playtwowin.model.SubmissionDetails;
import com.playtwowin.scriber.services.DocumentTextService;
import com.playtwowin.scriber.services.PDFService;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.textract.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextRequest;
import software.amazon.awssdk.services.textract.model.DetectDocumentTextResponse;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.TextractException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@RestController
@RequestMapping("/api")
@Api(value="scriber")
public class ScriberController {

	// Create credentials using a provider chain. For more information, see
	// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
	private static AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

	@Autowired
	DocumentTextService documentTextService;

	@Autowired
	DigitalSignatureRepo digitalSignatureRepo;

	@Autowired
	SubmittedFileRepo submittedFileRepo;
	
	@Autowired
	SubmissionDetailsRepo submittedDetailsRepo;
	
	static List<String> noWords = new ArrayList<>(List.of(
			"Achieve",           
			"unique",
			"predict",
			"Achieve",
			"realize",
			"reach",
			"ensure",
			"meet",
			"unique",	
			"innovative",
			"revolutionary",	
			"unparalleled",
			"one of a kind",
			"cutting edge",
			"state of the art",	
			"Pease of mind",
			"sleep at night"
			));

//    curl -k -X POST -F 'image=@/Pictures/running_cheetah.jpg' -v  http://localhost:8080/upload/
	@PostMapping("/upload")
	@ApiOperation(value = "upload")
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
	@ApiOperation(value = "upload a file")
	public ResponseEntity<FinalView> documentUploaderSimple(@RequestParam("file") MultipartFile multipartFile,
															@RequestParam("fileType") String fileType)
			throws Exception {
		
		// output PDF or IMAGE
		System.out.println(multipartFile.getContentType());

		// string of content from file to be tested
		String mf = multipartFile.getContentType().toString();

		// assign the file name to be used in ovr
		String fileName = multipartFile.getOriginalFilename();

		String bucketName = "s3://document-bucket-1";

		String outputFileName = "outputFile";

		ArrayList<Entity> entityList = new ArrayList<Entity>();
		DigitalSignature ds = new DigitalSignature();
		SubmittedFile sf = new SubmittedFile();
		SubmissionDetails sd = new SubmissionDetails();
		OverViewResponse ovr = new OverViewResponse();
		FinalView fv = new FinalView();

		ovr.setFileName(fileName);
		sf.setFileName(fileName);
		fv.setFileName(fileName);
		
		
		
		ArrayList<String> newNoList = new ArrayList<String>();
		newNoList.addAll(noWords);

		// extract the text
		if (!mf.equals("application/pdf")) {
			ByteBuffer imageBytes = documentTextService.uploadFile(multipartFile);
			DetectDocumentTextResult result = documentTextService.textDetector(imageBytes);

			List<Block> resultBlockList = result.getBlocks();

			System.out.println("[This Content was Extracted from the Document]");
			// prints the documents content to the console
			for (Block b : resultBlockList) {
				if (b.getText() != null && b.getBlockType().equals("LINE"))
					System.out.println(b.getText());
			}
			System.out.println("[Document Text Detection Complete]");

			// Testing out aws comprehend
			System.out.println("Start: Sentiment Analysis Test 1");
			sentimentAnalysis(resultBlockList.get(1).getText(), sd);
			System.out.println("End: Sentiment Analysis Test 1\n");

			System.out.println("[LINE Entity Detection Start]");
			for (Block b : resultBlockList) {
				if (b.getText() != null && b.getBlockType().equals("LINE")) {
					// System.out.println(b.getText());
					entityDetection(b.getText(), entityList);
					// adds the entities to the list initialized above
				}
			}
			System.out.println("[LINE Entity Detection End]");

			ds = documentTextService.BuildaSignature(entityList, ds);
			ovr = documentTextService.OVResponse(entityList, ovr);

			System.out.println("\n\n[WORD Entity Detection Start]");
			int wordCounter = 0;
			int noWordCount = 0;
			
			for (Block b : resultBlockList) {
				if (b.getText() != null && b.getBlockType().equals("WORD")) {
					//System.out.println(b.getText());
					wordCounter++;
					for(String i : newNoList) {
						if(b.getText().equals(i)) {
							noWordCount++;
						}
					}
				}
			}
			int compliantWordCount = wordCounter - noWordCount;
			sd.setWordCount(wordCounter);
			sd.setNonCompliantWordCount(noWordCount);
			sd.setCompliantWordCount(compliantWordCount);
			sd.setConfidencePercent(compliantWordCount/wordCounter);
			
			System.out.println("[WORD Entity Detection End/ Word Count:" + wordCounter +"]");
			sd.setSubmittedFile(sf);
			sf.setSubmissionDetails(sd);
			
			documentTextService.FinalDestination(fv, ds);
			
			digitalSignatureRepo.save(ds);
			submittedFileRepo.save(sf);
			submittedDetailsRepo.save(sd);
			
			return new ResponseEntity<FinalView>(fv, HttpStatus.OK);
		} 
		
		else {
			byte[] image = multipartFile.getBytes();
			UploadToS3(bucketName, fileName, multipartFile.getContentType(), image);
			System.out.println("application is pdf, running the PDFRunner...");
			PDFRunner(bucketName, fileName, outputFileName);
		}
		return new ResponseEntity<FinalView>(fv, HttpStatus.OK);

	}

	public static AmazonComprehend amazonComprehend() {
		AmazonComprehend comprehendClient = AmazonComprehendClientBuilder.standard().withCredentials(awsCreds)
				.withRegion("us-west-2").build();
		return comprehendClient;
	}

	public void sentimentAnalysis(String text, SubmissionDetails sd) {

		AmazonComprehend comprehendClient = amazonComprehend();

		// Call detectSentiment API
		System.out.println("Calling DetectSentiment");
		DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text)
				.withLanguageCode("en");
		DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
		sd.setSentimentScore(detectSentimentResult.getSentiment());
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

	private void PDFRunner(String bucketName, String documentName, String outputDocumentName)
			throws IOException, InterruptedException {
		System.out.println("Generating searchable pdf from: " + bucketName + "/" + documentName);

		// Extract text using Amazon Textract
		List<ArrayList<TextLine>> linesInPages = extractText(bucketName, documentName);

		// Get input pdf document from Amazon S3
		InputStream inputPdf = getPdfFromS3(bucketName, documentName);

		// Create new PDF document
		PDFService pdfDocument = new PDFService(); // PDFDocument pdfDocument = new PDFDocument();

		// For each page add text layer and image in the pdf document
		PDDocument inDoc = PDDocument.load(inputPdf);
		PDFRenderer pdfRenderer = new PDFRenderer(inDoc);
		BufferedImage image = null;
		for (int page = 0; page < inDoc.getNumberOfPages(); ++page) {
			image = pdfRenderer.renderImageWithDPI(page, 300, org.apache.pdfbox.rendering.ImageType.RGB);

			pdfDocument.addPage(image, ImageType.JPEG, linesInPages.get(page));

			System.out.println("Processed page index: " + page);
		}
	}

	private List<ArrayList<TextLine>> extractText(String bucketName, String documentName) throws InterruptedException {

		// AmazonTextract client = AmazonTextractClientBuilder.defaultClient();

		// Call DetectDocumentText
		AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
				"https://textract.us-west-2.amazonaws.com", "us-west-2");
		AmazonTextract client = AmazonTextractClientBuilder.standard().withEndpointConfiguration(endpoint).build();

		StartDocumentTextDetectionRequest req = new StartDocumentTextDetectionRequest()
				.withDocumentLocation(new DocumentLocation()
						.withS3Object(new S3Object().withBucket(bucketName).withName(documentName)))
				.withJobTag("DetectingText");

		StartDocumentTextDetectionResult startDocumentTextDetectionResult = client.startDocumentTextDetection(req);
		String startJobId = startDocumentTextDetectionResult.getJobId();

		System.out.println("Text detection job started with Id: " + startJobId);

		GetDocumentTextDetectionRequest documentTextDetectionRequest = null;
		GetDocumentTextDetectionResult response = null;

		String jobStatus = "IN_PROGRESS";

		while (jobStatus.equals("IN_PROGRESS")) {
			System.out.println("Waiting for job to complete...");
			TimeUnit.SECONDS.sleep(3);
			documentTextDetectionRequest = new GetDocumentTextDetectionRequest().withJobId(startJobId)
					.withMaxResults(1);

			response = client.getDocumentTextDetection(documentTextDetectionRequest);
			jobStatus = response.getJobStatus();
		}

		int maxResults = 1000;
		String paginationToken = null;
		Boolean finished = false;

		List<ArrayList<TextLine>> pages = new ArrayList<ArrayList<TextLine>>();
		ArrayList<TextLine> page = null;
		BoundingBox boundingBox = null;

		while (finished == false) {
			documentTextDetectionRequest = new GetDocumentTextDetectionRequest().withJobId(startJobId)
					.withMaxResults(maxResults).withNextToken(paginationToken);
			response = client.getDocumentTextDetection(documentTextDetectionRequest);

			// Show blocks information
			List<Block> blocks = response.getBlocks();
			for (Block block : blocks) {
				if (block.getBlockType().equals("PAGE")) {
					page = new ArrayList<TextLine>();
					pages.add(page);
				} else if (block.getBlockType().equals("LINE")) {
					boundingBox = block.getGeometry().getBoundingBox();
					page.add(new TextLine(boundingBox.getLeft(), boundingBox.getTop(), boundingBox.getWidth(),
							boundingBox.getHeight(), block.getText()));
				}
			}
			paginationToken = response.getNextToken();
			if (paginationToken == null)
				finished = true;
		}

		return pages;
	}

	private InputStream getPdfFromS3(String bucketName, String documentName) throws IOException {

		AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
		com.amazonaws.services.s3.model.S3Object fullObject = s3client
				.getObject(new GetObjectRequest(bucketName, documentName));
		InputStream in = fullObject.getObjectContent();
		return in;
	}

	private void UploadToS3(String bucketName, String objectName, String contentType, byte[] bytes) {
		AmazonS3 s3client = AmazonS3ClientBuilder.defaultClient();
		ByteArrayInputStream baInputStream = new ByteArrayInputStream(bytes);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(bytes.length);
		metadata.setContentType(contentType);
		PutObjectRequest putRequest = new PutObjectRequest(bucketName, objectName, baInputStream, metadata);
		s3client.putObject(putRequest);
	}

	/*
	 * public static void detectDocTextS3 (TextractClient textractClient, String
	 * bucketName, String docName) { try { S3Object s3Object = S3Object.builder()
	 * .bucket(bucketName) .name(docName) .build(); // Create a Document object and
	 * reference the s3Object instance Document myDoc = Document.builder()
	 * .s3Object(s3Object) .build(); // Create a DetectDocumentTextRequest object
	 * DetectDocumentTextRequest detectDocumentTextRequest =
	 * DetectDocumentTextRequest.builder() .document(myDoc) .build(); // Invoke the
	 * detectDocumentText method DetectDocumentTextResponse textResponse =
	 * textractClient.detectDocumentText(detectDocumentTextRequest); List<Block>
	 * docInfo = textResponse.blocks(); Iterator<Block> blockIterator =
	 * docInfo.iterator(); while(blockIterator.hasNext()) { Block block =
	 * blockIterator.next(); System.out.println("The block type is "
	 * +block.blockType().toString()); } DocumentMetadata documentMetadata =
	 * textResponse.documentMetadata();
	 * System.out.println("The number of pages in the document is "
	 * +documentMetadata.pages()); } catch (TextractException e) {
	 * System.err.println(e.getMessage()); System.exit(1); } }
	 */
}
