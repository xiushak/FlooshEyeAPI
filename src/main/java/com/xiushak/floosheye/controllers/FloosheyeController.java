package com.xiushak.floosheye.controllers;

import com.xiushak.floosheye.services.Filter;
import com.xiushak.floosheye.services.FisheyeFilterImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

@RestController
public class FloosheyeController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    /**
     * given an image, fisheye the image with face mapping
     *
     * @return image
     */
    @PostMapping("/fish")
    public ResponseEntity<byte[]> fish(@RequestParam("file") MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            Filter filter = new FisheyeFilterImpl(image);

            filter.processImage(image.getWidth() / 2, image.getHeight() / 2);

            BufferedImage filteredImage = filter.getImage();

            System.out.println("Image processed: " + filteredImage.getWidth() + "x" + filteredImage.getHeight());

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(filteredImage, "jpg", baos); // You can change "jpg" to "png" as needed

            byte[] imageBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            System.out.println("Image converted to byte array: " + imageBytes.length);
            System.out.println(Arrays.toString(imageBytes));

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * given an image, brainiac the image with face mapping
     *
     * @return image
     */
    @PostMapping("/brain")
    public ResponseEntity<byte[]> brain() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }
}
