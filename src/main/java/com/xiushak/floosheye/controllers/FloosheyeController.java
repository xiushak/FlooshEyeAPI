package com.xiushak.floosheye.controllers;

import com.xiushak.floosheye.services.Filter;
import com.xiushak.floosheye.services.FisheyeFilterImpl;
import com.xiushak.floosheye.utils.FaceRecognition;
import org.opencv.core.Rect;
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
import java.util.List;
import java.util.Random;

@RestController
public class FloosheyeController {

    private final Random random = new Random();

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    // TODO: abstract these things out and actually use proper spring concepts
    /**
     * given an image, fisheye the image with face mapping
     *
     * @return image
     */
    @PostMapping("/fish")
    public ResponseEntity<Object> fish(@RequestParam("file") MultipartFile file) {
        System.out.println("Fish called with file: " + file.getOriginalFilename());
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());

            Filter filter = new FisheyeFilterImpl(image);

            // detect a face
            List<Rect> faces = FaceRecognition.detectFaces(image);
            if (faces.isEmpty()) {
                filter.processImage(image.getWidth() / 2, image.getHeight() / 2);
            } else {
                Rect face = faces.get(random.nextInt(0, faces.size()));
                filter.processImage(face.x + face.width / 2, face.y + face.height / 2);
            }

            filter.processImage(image.getWidth() / 2, image.getHeight() / 2);

            BufferedImage filteredImage = filter.getImage();

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(filteredImage, "png", baos);

            byte[] imageBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            System.out.println("Fish filter applied to image: " + file.getOriginalFilename());

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * given an image, brainiac the image with face mapping
     *
     * @return image
     */
    @PostMapping("/brain")
    public ResponseEntity<Object> brain(@RequestParam("file") MultipartFile file) {
        System.out.println("Brain called with file: " + file.getOriginalFilename());
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());

            Filter filter = new FisheyeFilterImpl(image);

            // detect a face
            List<Rect> faces = FaceRecognition.detectFaces(image);
            if (faces.isEmpty()) {
                filter.processImage(image.getWidth() / 2, image.getHeight() / 2);
            } else {
                Rect face = faces.get(random.nextInt(0, faces.size()));
                filter.processImage(face.x + face.width / 2, face.y + face.height / 5);
            }

            BufferedImage filteredImage = filter.getImage();

            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(filteredImage, "png", baos);

            byte[] imageBytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);

            System.out.println("Brain filter applied to image: " + file.getOriginalFilename());

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

