package modules;

import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import gui.Main;
import vision.VisionModule;

public class VisionModule1 extends VisionModule {

    Random rand = new Random();

    public void run(Main app, Mat frame) {
        Mat m = Mat.zeros(300, 300, CvType.CV_8UC3);
        for (int r = 0; r < m.rows(); ++r) {
            for (int c = 0; c < m.cols(); ++c) {
                m.put(r, c, randomPixel());
            }
        }
        app.postImage(m, "Master", this);
    }

    private double[] randomPixel() {
        return new double[] { rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) };
    }
}
