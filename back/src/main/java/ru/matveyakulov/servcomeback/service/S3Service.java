package ru.matveyakulov.servcomeback.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.matveyakulov.servcomeback.utils.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class S3Service {

    private final String bucketName = "bucketqwe1";

    @Value("${application.s3.key}")
    private String accessStaticKeyId;

    @Value("${application.s3.key-id}")
    private String accessStaticKey;

    public void uploadFile(String path) {
        try {
            AmazonS3 s3Client = getClient();
            PutObjectRequest request = new PutObjectRequest(bucketName, Constant.EXCEL_FILE_NAME, new File(path));
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            request.setMetadata(metadata);
            s3Client.putObject(request);
        } catch (SdkClientException e) {
            e.printStackTrace();
        }
    }

    public void getFile() {
        try {
            AmazonS3 s3Client = getClient();

            GetObjectRequest request = new GetObjectRequest(bucketName, Constant.EXCEL_FILE_NAME);

            S3Object o = s3Client.getObject(request);
            S3ObjectInputStream s3is = o.getObjectContent();
            FileOutputStream fos = new FileOutputStream(Constant.EXCEL_FILE_NAME);
            byte[] read_buf = new byte[1024];
            int read_len;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        } catch (SdkClientException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private AmazonS3 getClient(){
        AWSCredentials credentials = new BasicAWSCredentials(accessStaticKeyId, accessStaticKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(
                                "storage.yandexcloud.net", "ru-central1"
                        )
                )
                .build();
    }
}
