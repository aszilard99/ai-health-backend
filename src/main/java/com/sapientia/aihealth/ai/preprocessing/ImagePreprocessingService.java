package com.sapientia.aihealth.ai.preprocessing;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class ImagePreprocessingService {

    public Mat cropBrainContour(Mat image) {
        // Convert the image to grayscale
        Mat gray = new Mat();
        Imgproc.cvtColor(image, gray, Imgproc.COLOR_BGR2GRAY);

        // Apply Gaussian blur
        Imgproc.GaussianBlur(gray, gray, new Size(5, 5), 0);

        // Threshold the image
        Mat thresh = new Mat();
        Imgproc.threshold(gray, thresh, 45, 255, Imgproc.THRESH_BINARY);

        // Perform erosions and dilations to remove noise
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.erode(thresh, thresh, kernel, new Point(-1, -1), 2);
        Imgproc.dilate(thresh, thresh, kernel, new Point(-1, -1), 2);

        // Find contours
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(thresh, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Find the largest contour
        double maxArea = 0;
        MatOfPoint largestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }

        if (largestContour == null) {
            return image; // Return the original image if no contours found
        }

        // Find extreme points
        Rect boundingRect = Imgproc.boundingRect(largestContour);
        int x = boundingRect.x;
        int y = boundingRect.y;
        int w = boundingRect.width;
        int h = boundingRect.height;

        // Crop the image
        Mat croppedImage = new Mat(image, boundingRect);
        return croppedImage;
    }

    public void resizeImage(Mat image, Integer xSize, Integer ySize) {
        Imgproc.resize(image, image, new Size(xSize, ySize),0,0, Imgproc.INTER_CUBIC);
    }
}
