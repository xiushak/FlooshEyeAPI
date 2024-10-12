package com.xiushak.floosheye.utils;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class FaceRecognition {

    private static CascadeClassifier faceDetector;

    // Load Haar cascade classifier
    private static void loadHaarCascade() {
        try {
            // these dont work if in a jar
            Resource resource = new ClassPathResource("haars/haarcascade_frontalface_default.xml");
            faceDetector = new CascadeClassifier(resource.getFile().getAbsolutePath());

//            File file = ResourceUtils.getFile("classpath:haars/haarcascade_frontalface_default.xml");
//            faceDetector = new CascadeClassifier(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Rect> detectFaces(BufferedImage bi) {
        if (faceDetector == null) {
            loadHaarCascade();
        }
        // Convert BufferedImage to Mat
        Mat image = bufferedImageToMat(bi);
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Detect faces
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faceDetections);

        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        return faceDetections.toList();
    }

    private static Mat bufferedImageToMat(BufferedImage image) {
        // Convert BufferedImage to Mat
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        // Copy the pixel data from BufferedImage to Mat
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                // Convert to BGR format
                mat.put(y, x, new byte[]{
                        (byte) ((rgb >> 16) & 0xFF),
                        (byte) ((rgb >> 8) & 0xFF),
                        (byte) (rgb & 0xFF)
                });
            }
        }
        return mat;
    }
}
