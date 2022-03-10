package br.com.nagata.dev.service.impl;

import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import br.com.nagata.dev.service.S3Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class S3ServiceImpl implements S3Service {

  @Value("${s3.bucket}")
  private String bucketName;

  private AmazonS3 s3client;

  public S3ServiceImpl(AmazonS3 s3client) {
    this.s3client = s3client;
  }

  @Override
  public void uploadFile(String localFilePath) {
    try {
      log.info("Iniciando upload...");
      File file = new File(localFilePath);
      s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
      log.info("Upload finalizado");
    } catch (AmazonServiceException e) {
      log.error("AmazonServiceException: {}", e.getMessage());
      log.error("Status code: {}", e.getErrorCode());
    } catch (AmazonClientException e) {
      log.error("AmazonClientException: {}", e.getMessage());
    }
  }
}
