package vision;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import gui.Main;

public class VisionModule2 extends VisionModule {
    public void run(Main app, Mat frame) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                app.postImage(frame, "Camera", VisionModule2.this);
                Mat hsv = new Mat();
                Imgproc.cvtColor(frame, hsv, Imgproc.COLOR_BGR2HSV);
                ArrayList<Mat> channels = new ArrayList<Mat>();
                Core.split(hsv, channels);
                Imgproc.GaussianBlur(channels.get(1), channels.get(1), new Size(5, 5), 5);
                Imgproc.threshold(channels.get(1), channels.get(1), 40, 80, Imgproc.THRESH_BINARY);
                Imgproc.threshold(channels.get(1), channels.get(1), 0, 255, Imgproc.THRESH_BINARY);
                Mat dilateKernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
                Imgproc.dilate(channels.get(1), channels.get(1), dilateKernel);
                app.postImage(channels.get(1), "Saturation thresholded", VisionModule2.this);
            }
        }, "Vision Module 2 Thread");
        t.setDaemon(true);
        t.start();
    }
}