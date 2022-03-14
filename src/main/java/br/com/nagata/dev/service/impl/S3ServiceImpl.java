package br.com.nagata.dev.service.impl;

import br.com.nagata.dev.service.S3Service;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Service
public class S3ServiceImpl implements S3Service {

  @Value("${s3.bucket}")
  private String bucketName;

  private final AmazonS3 s3client;

  public S3ServiceImpl(AmazonS3 s3client) {
    this.s3client = s3client;
  }

  @Override
  public URI uploadFile(MultipartFile multipartFile) {
    try {
      String filename = multipartFile.getOriginalFilename();
      InputStream is = multipartFile.getInputStream();
      String contentType = multipartFile.getContentType();
      return uploadFile(is, filename, contentType);
    } catch (IOException e) {
      throw new RuntimeException("Erro de IO: " + e.getMessage());
    }
  }

  @Override
  public URI uploadFile(InputStream is, String fileName, String contentType) {
    try {
      ObjectMetadata meta = new ObjectMetadata();
      meta.setContentType(contentType);
      log.info("Iniciando upload...");
      s3client.putObject(bucketName, fileName, is, meta);
      log.info("Upload finalizado");
      return s3client.getUrl(bucketName, fileName).toURI();
    } catch (URISyntaxException e) {
      throw new RuntimeException("Erro ao converter URL para URI");
    }
  }
}
