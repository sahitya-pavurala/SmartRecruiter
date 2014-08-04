package smartRecruiter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.ClasspathPropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class S3Actions {
	
	public static String upload(List<String> questions, List<String> solutions, ServletContext context, String bucketName) throws IOException{
		String root = context.getRealPath("/");
		AWSCredentialsProvider credentialsProvider = new ClasspathPropertiesFileCredentialsProvider();
        AmazonS3 s3client = new AmazonS3Client(credentialsProvider);
        File fil = new File(root + "/Questions.txt");
        FileWriter fileWriter  = new FileWriter(fil);
        for(int i=0; i< questions.size(); i++){
        	fileWriter.write(i+1 + ". " +questions.get(i) + "\n\n");
        	fileWriter.write("Ans. " +solutions.get(i) + "\n\n");
        }
        fileWriter.close();
        bucketName = bucketName.replace(' ', '.');
        s3client.createBucket(new CreateBucketRequest(bucketName).withCannedAcl(CannedAccessControlList.PublicRead));
        s3client.putObject(new PutObjectRequest(bucketName, fil.getName(), fil).withCannedAcl(CannedAccessControlList.PublicRead));
        uploadImages(s3client, bucketName);
		return "http://"+bucketName+".s3.amazonaws.com/";
	}

	private static void uploadImages(AmazonS3 s3client, String bucketName) {
		String directory = "C:\\Upload_CloudRecruiter";
		File startDirectory = new File(directory);
	    if(startDirectory.isDirectory()){
        	File[] filesInDirectory = startDirectory.listFiles();
            if(filesInDirectory != null){
                for(File fileInDirectory : filesInDirectory){
                	s3client.putObject(new PutObjectRequest(bucketName, fileInDirectory.getName(), fileInDirectory).withCannedAcl(CannedAccessControlList.PublicRead));
                }
            }
	    }
	}

}
