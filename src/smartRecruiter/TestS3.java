package smartRecruiter;

import java.io.File;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class TestS3 {
	
	private static AmazonS3 s3;
	private String LOCAL_DIRECTORY_ROOT = "F:\\test";
	private static AWSCredentials credentials;
	private static String bucketname = "pawan-bucket";
	
	public static void createCredentials() throws Exception
    {
		credentials = new PropertiesCredentials(TestS3.class.getResourceAsStream("AwsCredentials.properties"));
    	
    }
	
	
	public void copyFilesToS3(){
		s3  = new AmazonS3Client(credentials);
		s3.createBucket(bucketname);
		File startDirectory = new File(LOCAL_DIRECTORY_ROOT);
	    if(startDirectory.isDirectory()){
        	File[] filesInDirectory = startDirectory.listFiles();
            if(filesInDirectory != null){
                for(File fileInDirectory : filesInDirectory){
                	System.out.println(fileInDirectory.getName());
                	String destBucketAndPath = bucketname + "/" + startDirectory.getName();
                	s3.putObject(new PutObjectRequest(destBucketAndPath, fileInDirectory.getName(), fileInDirectory));
                	System.out.println("Added files to S3");
                }
            }
        }
    }
}