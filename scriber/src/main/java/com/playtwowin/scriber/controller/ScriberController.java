package com.playtwowin.scriber.controller;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;
import com.playtwowin.scriber.services.DocumentTextService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ScriberController {

	@GetMapping("/scribe")
	public String scribeApi() {
		return "scriber";
	}

//    curl -k -X POST -F 'image=@/Pictures/running_cheetah.jpg' -v  http://localhost:8080/upload/
	@PostMapping("/upload")
	public String fileUploader(@RequestParam("file") MultipartFile multipartFile) {
		String fileName = multipartFile.getOriginalFilename();
		return fileName;
	}

	// testing out AWS Textract
	@PostMapping("/doc-upload-complex")
	public void documentUploader(@RequestParam("file") MultipartFile multipartFile) throws Exception {

		InputStream inputStream = multipartFile.getInputStream();
		BufferedImage image = ImageIO.read(inputStream);

		// Call DetectDocumentText
		AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
				"https://textract.us-east-1.amazonaws.com", "us-east-1");
		AmazonTextract client = AmazonTextractClientBuilder.standard().withEndpointConfiguration(endpoint).build();

		DetectDocumentTextRequest request = new DetectDocumentTextRequest().withDocument(new Document());

		DetectDocumentTextResult result = client.detectDocumentText(request);

		// Create frame and panel.
		JFrame frame = new JFrame("RotateImage");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DocumentTextService panel = new DocumentTextService(result, image);
		panel.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);

	}

	@PostMapping("/doc-upload")
	public ResponseEntity<ArrayList<String>> documentUploaderSimple(@RequestParam("file") MultipartFile multipartFile)
			throws Exception {

		ByteBuffer imageBytes;
		BufferedImage image;

		try (InputStream inputStream = multipartFile.getInputStream()) {
			imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
			image = ImageIO.read(inputStream);
		}

		// Call DetectDocumentText
		AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
				"https://textract.us-east-1.amazonaws.com", "us-east-1");
		AmazonTextract client = AmazonTextractClientBuilder.standard().withEndpointConfiguration(endpoint).build();

		DetectDocumentTextRequest request = new DetectDocumentTextRequest()
				.withDocument(new Document().withBytes(imageBytes));

		DetectDocumentTextResult result = client.detectDocumentText(request);

		System.out.println("[This Content was Extracted from the Document]");

		List<Block> resultBlockList = result.getBlocks();

		ArrayList<String> output = new ArrayList<String>();
		// prints the documents content to the console
		for (Block b : resultBlockList) {
			if (b.getText() != null && b.getBlockType().equals("LINE")) {
				System.out.println(b.getText());
				
				output.add(b.getText());
			}
		}

		System.out.println("[Document Text Detection Complete]");

		return new ResponseEntity<ArrayList<String>>(output, HttpStatus.OK);
	}

}
