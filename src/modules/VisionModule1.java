package modules;
import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import gui.Main;
import vision.VisionModule;

public class VisionModule1 extends VisionModule {
    public void run(Main app, Mat frame) {
        Mat m = Mat.zeros(300, 300, CvType.CV_8UC3);
        Random rand = new Random();
        for (int r = 0; r < m.rows(); ++r) {
            for (int c = 0; c < m.cols(); ++c) {
                m.put(r, c,
                        new double[] { rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) });
            }
        }
        app.postImage(m, "Master", VisionModule1.this);
    }
}
