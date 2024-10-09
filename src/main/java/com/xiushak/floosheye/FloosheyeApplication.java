package com.xiushak.floosheye;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FloosheyeApplication {

    public static void main(String[] args) {
        try {
            OpenCV.loadLocally();
            SpringApplication.run(FloosheyeApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
