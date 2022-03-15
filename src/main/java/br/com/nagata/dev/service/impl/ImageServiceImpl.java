package br.com.nagata.dev.service.impl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import br.com.nagata.dev.exception.FileException;
import br.com.nagata.dev.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

  @Override
  public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
    String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());

    if (!"png".equalsIgnoreCase(ext) && !"jpg".equalsIgnoreCase(ext)) {
      throw new FileException("Somente imagens PNG e JPG são permitidas");
    }

    try {
      BufferedImage image = ImageIO.read(uploadedFile.getInputStream());
      if ("png".equalsIgnoreCase(ext)) {
        image = pngToJpg(image);
      }
      return image;
    } catch (IOException e) {
      throw new FileException("Erro ao ler arquivo");
    }
  }

  public BufferedImage pngToJpg(BufferedImage image) {
    BufferedImage jpgImage =
        new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
    jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
    return jpgImage;
  }

  @Override
  public InputStream getInputStream(BufferedImage image, String extension) {
    try {
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      ImageIO.write(image, extension, outputStream);
      return new ByteArrayInputStream(outputStream.toByteArray());
    } catch (IOException e) {
      throw new FileException("Erro ao ler arquivo");
    }
  }

  @Override
  public BufferedImage cropSquare(BufferedImage sourceImg) {
    int min = Math.min(sourceImg.getHeight(), sourceImg.getWidth());
    return Scalr.crop(
        sourceImg,
        (sourceImg.getWidth() / 2) - (min / 2),
        (sourceImg.getHeight() / 2) - (min / 2),
        min,
        min);
  }

  @Override
  public BufferedImage resize(BufferedImage sourceImg, int size) {
    return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
  }
}
