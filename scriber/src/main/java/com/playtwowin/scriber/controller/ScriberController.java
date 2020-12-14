package com.playtwowin.scriber.controller;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;
import com.amazonaws.services.comprehend.model.DetectEntitiesRequest;
import com.amazonaws.services.comprehend.model.DetectEntitiesResult;
import com.amazonaws.services.comprehend.model.DetectSentimentRequest;
import com.amazonaws.services.comprehend.model.DetectSentimentResult;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;
import com.playtwowin.scriber.services.DocumentTextService;
import org.springframework.beans.factory.annotation.Autowired;

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
import java.awt.image.ImageObserver;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ScriberController {

  
    // Create credentials using a provider chain. For more information, see
    // https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
    private static AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();

    @Autowired
    DocumentTextService documentTextService;

//    curl -k -X POST -F 'image=@/Pictures/running_cheetah.jpg' -v  http://localhost:8080/upload/
    @PostMapping("/upload")
    public String fileUploader(@RequestParam("file") MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }

    // testing out AWS Textract
    @PostMapping("/doc-upload")
    public void documentUploaderSimple(@RequestParam("file") MultipartFile multipartFile) throws Exception {

        // Testing color recognition
//        colorRecognition(multipartFile);


        // Serialize the uploaded file
        ByteBuffer imageBytes = documentTextService.uploadFile(multipartFile);

        // extract the text
        DetectDocumentTextResult result = documentTextService.textDetector(imageBytes);

        System.out.println("[This Content was Extracted from the Document]");

        List<Block> resultBlockList = result.getBlocks();

        // prints the documents content to the console
        for( Block b : resultBlockList)
            if(b.getText() != null && b.getBlockType().equals("LINE"))
                System.out.println(b.getText());

        System.out.println("[Document Text Detection Complete]");

        // Testing out aws comprehend
//        System.out.println("Start: Sentiment Analysis Test 1");
//        sentimentAnalysis(resultBlockList.get(1).getText());
//        System.out.println("End: Sentiment Analysis Test 1\n");


        System.out.println("*********************************");

        System.out.println("Start: Sentiment Analysis Test 1");
        System.out.println("[LINE Entity Detection Start]");
        for( Block b : resultBlockList)
            if(b.getText() != null && b.getBlockType().equals("LINE")) {
                System.out.println(b.getText());
                sentimentAnalysis(b.getText());
                entityDetection(b.getText());}
        System.out.println("[LINE Entity Detection End]");
        System.out.println("End: Sentiment Analysis Test 1\n");

        System.out.println("*********************************");

        System.out.println("Start: Sentiment Analysis Test 2");
        System.out.println("\n\n[WORD Entity Detection Start]");
        for( Block b : resultBlockList)
            if(b.getText() != null && b.getBlockType().equals("WORD")) {
                System.out.println(b.getText());
                sentimentAnalysis(b.getText());
                entityDetection(b.getText());}
        System.out.println("[WORD Entity Detection End]");
        System.out.println("End: Sentiment Analysis Test 2\n");

    }

    public void colorRecognition(MultipartFile multipartFile) throws IOException {
        // MultipartFile to File
        File file = multipartToFile(multipartFile, multipartFile.getOriginalFilename());

        // Create BufferImage
        BufferedImage bufferedImage = ImageIO.read(file);
        System.out.println("This is the Width: " + bufferedImage.getWidth());
        // testing rbg
        int something = bufferedImage.getRGB(10, 10);
        System.out.println("Something: " + something);

        // Get width and Height
//        Image image = bufferedImage.;
//        ImageObserver imageObserver = image.;
        System.out.println(bufferedImage.toString());
        int imgWidth = bufferedImage.getWidth() == 0 ? -1: bufferedImage.getWidth();
        int imgHeight = bufferedImage.getHeight() == 0 ? -1: bufferedImage.getHeight();
        System.out.println("This is the width: " + imgWidth);
        System.out.println("This is the Height: " + imgHeight);
        System.out.println("This is the minX" + bufferedImage.getMinX());
        System.out.println("This is the minY" + bufferedImage.getMinY());
//        bufferedImage.s

        // crack the code
        int[] dataBuffInt = bufferedImage.getRGB(0, 0, imgWidth, imgHeight, null, 0, imgWidth);

        Color c = new Color(dataBuffInt[100]);

        // prints out Individual RGB value
        System.out.println(c.getRed());   // = (dataBuffInt[100] >> 16) & 0xFF
        System.out.println(c.getGreen()); // = (dataBuffInt[100] >> 8)  & 0xFF
        System.out.println(c.getBlue());  // = (dataBuffInt[100] >> 0)  & 0xFF
        System.out.println(c.getAlpha()); // = (dataBuffInt[100] >> 24) & 0xFF

        Color c2 = new Color(bufferedImage.getRGB(0,0));
        System.out.println("RBG for c2 object: " + c2.getRGB());
        System.out.println("Red: " + c2.getRed());
        System.out.println("Green: " + c2.getGreen());
        System.out.println("Blue: " + c2.getBlue());

        System.out.println("The RGB int value: " + bufferedImage.getRGB(imgWidth, imgHeight));
        // create color model passing bits
        Color mycolor = new Color(bufferedImage.getRGB(imgHeight, imgWidth));
//        System.out.println("This is the Color: " + mycolor.);
        int Red, Green, Blue = 0;

        for (int y = 0; y < imgHeight; y++)
        {
            for (int x = 0; x < imgWidth; x++)
            {
//                int = imgWidth.getRGB(x,y);

            }
        }


    }

    public File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        multipart.transferTo(convFile);
        return convFile;
    }

    String toBinary( byte[] bytes )
    {
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public static AmazonComprehend amazonComprehend(){
        AmazonComprehend comprehendClient =
                AmazonComprehendClientBuilder.standard()
                        .withCredentials(awsCreds)
                        .withRegion("us-east-1")
                        .build();
        return comprehendClient;
    }


    public void sentimentAnalysis(String text){
        List<String> noNoWords = new ArrayList<>();
        noNoWords.add("not");
        noNoWords.add("whether");
//        Map<String, String > noNoMap = new HashMap<>();
//        noNoMap..add("not", )
        /*
        whether or not - “or not” is usually unnecessary - to decide if it is needed,
substitute “if”for “whether,” and if the “if” results in a different
meaning, “or not” is needed
         */
        String recommendText = "\"not\" is usually unnecessary - to decide if it is needed,\n" +
                "substitute \"if\" for \"whether,\" and if the \"if\" results in a different\n" +
                "\"meaning, \"or not\" is needed";

        AmazonComprehend comprehendClient = amazonComprehend();

        // Call detectSentiment API
        System.out.println("Calling DetectSentiment");
        DetectSentimentRequest detectSentimentRequest = new DetectSentimentRequest().withText(text)
                .withLanguageCode("en");
        DetectSentimentResult detectSentimentResult = comprehendClient.detectSentiment(detectSentimentRequest);
        System.out.println(detectSentimentResult);
        System.out.println(detectSentimentResult.getSentiment().equals("NEGATIVE") ? "THE SENTIMENT WAS: " + detectSentimentResult.getSentiment() + ", \nTHE NEGATIVE TEXT READS: " + text : "");
        System.out.println(noNoWords.contains(text) ? "Recommendation: " + recommendText: "");
        System.out.println("End of DetectSentiment\n");
        System.out.println( "Done" );
    }

    public void entityDetection(String text){

        AmazonComprehend comprehendClient = amazonComprehend();

        // Call detectEntities API
        System.out.println("\nCalling DetectEntities");
        DetectEntitiesRequest detectEntitiesRequest = new DetectEntitiesRequest().withText(text)
                .withLanguageCode("en");
        DetectEntitiesResult detectEntitiesResult  = comprehendClient.detectEntities(detectEntitiesRequest);
        detectEntitiesResult.getEntities().forEach(System.out::println);
        System.out.println("End of DetectEntities\n");

    }

	@PostMapping("/doc-upload-2")
	public ResponseEntity<ArrayList<String>> documentUploaderSimple2(@RequestParam("file") MultipartFile multipartFile)
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
