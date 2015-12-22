package vision;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import gui.Main;

public class VisionModule2 extends VisionModule {
    public IntegerSliderVariable minSat = new IntegerSliderVariable("Min Saturation", 50,  0,  255);
    public IntegerSliderVariable maxSat = new IntegerSliderVariable("Max Saturation", 255,  0,  255);
    public DoubleSliderVariable dummyDouble = new DoubleSliderVariable("Dummy Double", 10.0,  0.0,  100.0);
    public void run(Main app, Mat frame) {
        app.postImage(frame, "Camera", VisionModule2.this);
        Mat hsv = new Mat();
        Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
        ArrayList<Mat> channels = new ArrayList<Mat>();
        Core.split(hsv, channels);
        Imgproc.GaussianBlur(channels.get(1), channels.get(1), new Size(5, 5), 5);
        Core.inRange(channels.get(1), new Scalar(minSat.value()), new Scalar(maxSat.value()),
                channels.get(1));
        Mat dilateKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
        Imgproc.dilate(channels.get(1), channels.get(1), dilateKernel);
        app.postImage(channels.get(1), "Saturation thresholded", VisionModule2.this);
    }
}