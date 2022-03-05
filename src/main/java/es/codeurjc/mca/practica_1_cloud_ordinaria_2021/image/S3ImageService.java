package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.HeadBucketResponse;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;

@Component
@Service("storageService")
@Profile("production")
public class S3ImageService implements ImageService {
	
	public static S3Client s3;
	
	@Value("${amazon.s3.bucket-name}")
	private String bucketName;	
	

	public S3ImageService() {
		s3 = S3Client.builder()
	            .region(Region.EU_WEST_1)
	            .build(); 
	}
	
//	//Function to check if a bucket already exists
//	public static boolean bucketExists(S3Client s3, String bucketName) {
//		HeadBucketRequest headBucketRequest = HeadBucketRequest.builder()
//	            .bucket(bucketName)
//	            .build();
//
//	    try {
//	        s3.headBucket(headBucketRequest);
//	        return true;
//	    } catch (NoSuchBucketException e) {
//	        return false;
//	    }
//	}
	
//	// Create a bucket by using a S3Waiter object
//    public static void createBucket(S3Client s3Client, String bucketName) {
//
//        try {
//        	
//            S3Waiter s3Waiter = s3Client.waiter();
//             CreateBucketRequest bucketRequest = CreateBucketRequest.builder()
//                    .bucket(bucketName)
//                    .build();
//
//            s3Client.createBucket(bucketRequest);
//            HeadBucketRequest bucketRequestWait = HeadBucketRequest.builder()
//                    .bucket(bucketName)
//                    .build();
//
//            // Wait until the bucket is created and print out the response
//            WaiterResponse<HeadBucketResponse> waiterResponse = s3Waiter.waitUntilBucketExists(bucketRequestWait);
//            waiterResponse.matched().response().ifPresent(System.out::println);
//            System.out.println(bucketName +" is ready");
//
//        } catch (S3Exception e) {
//            System.err.println(e.awsErrorDetails().errorMessage());
//            System.exit(1);
//        }
//    }

    @Override
    public String createImage(MultipartFile multiPartFile) {
//    	// Check if bucket already exists
//    	boolean be = bucketExists(s3,bucketName);
//    	// If bucket doesn't exist, create the bucket
//    	if (be == false) {
//	    	createBucket(s3,bucketName);
//    	}
    	// Upload the file
    	String fileName = multiPartFile.getOriginalFilename();
        File file = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
        
        try {
            multiPartFile.transferTo(file);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't upload image to bucket", ex);
        } 
       
        s3.putObject(
            PutObjectRequest.builder()
                .bucket(bucketName)
                .acl(ObjectCannedACL.PUBLIC_READ)
                .key(fileName)
                .build(), 
            RequestBody.fromFile(file)
        );
        String s3Url = s3.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
        return s3Url;
    }

    @Override
    public void deleteImage(String image) {
    	String imageName = image.substring(image.lastIndexOf("/")+1);
    	s3.deleteObject(
    			DeleteObjectRequest.builder()
    	        	.bucket(bucketName)
    	            .key(imageName)
    	            .build()
    	);
    }

}
