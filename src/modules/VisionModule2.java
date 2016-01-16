package modules;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import gui.BooleanVariable;
import gui.DoubleSliderVariable;
import gui.IntegerSliderVariable;
import vision.VisionModule;

public class VisionModule2 extends VisionModule {
    public IntegerSliderVariable minHue = new IntegerSliderVariable("Min Hue", 13, 0, 255);
    public IntegerSliderVariable maxHue = new IntegerSliderVariable("Max Hue", 59, 0, 255);
    public IntegerSliderVariable minSat = new IntegerSliderVariable("Min Saturation", 96, 0, 255);
    public IntegerSliderVariable maxSat = new IntegerSliderVariable("Max Saturation", 255, 0, 255);
    public DoubleSliderVariable dummyDouble = new DoubleSliderVariable("Dummy Double", 10.0, 0.0, 100.0);
    public BooleanVariable dummyBoolean = new BooleanVariable("Dummy Boolean", true);

    public void run(Mat frame) {
        postImage(frame, "Master");

        // blur
        Mat blurred = new Mat();
        Imgproc.medianBlur(frame, blurred, 5);
        postImage(blurred, "Blurred");

        // split into hsv channels
        Mat hsv = new Mat();
        Imgproc.cvtColor(blurred, hsv, Imgproc.COLOR_BGR2HSV);
        ArrayList<Mat> channels = new ArrayList<Mat>();
        Core.split(hsv, channels);

        // hue is the 0th index in hsv
        Core.inRange(channels.get(0), new Scalar(minHue.value()), new Scalar(maxHue.value()), channels.get(0));
        postImage(channels.get(0), "Hue thresholded");
        // saturation is the 1st index in hsv
        Core.inRange(channels.get(1), new Scalar(minSat.value()), new Scalar(maxSat.value()), channels.get(1));
        postImage(channels.get(1), "Saturation thresholded");
        // combine thresholded images
        Mat thresholdedImage = new Mat();
        Core.bitwise_and(channels.get(0), channels.get(1), thresholdedImage);
        postImage(thresholdedImage, "Final threshed");

        // erode and dilate
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
        Imgproc.erode(thresholdedImage, thresholdedImage, kernel);
        Imgproc.dilate(thresholdedImage, thresholdedImage, kernel);
        postImage(thresholdedImage, "Erode and dilate");

        // find all outer contours
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(thresholdedImage, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat frameCopy = frame.clone();
        Imgproc.drawContours(frameCopy, contours, -1, new Scalar(255, 0, 255), 2);
        postImage(frameCopy, "Contours");
        postTag("Contours", "contourCount", "Contours: " + contours.size());
    }
}
