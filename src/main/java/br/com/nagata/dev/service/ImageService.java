package br.com.nagata.dev.service;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.InputStream;

public interface ImageService {

  BufferedImage getJpgImageFromFile(MultipartFile uploadedFile);

  InputStream getInputStream(BufferedImage image, String extension);

  BufferedImage cropSquare(BufferedImage sourceImg);

  BufferedImage resize(BufferedImage sourceImg, int size);
}
