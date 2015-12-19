package vision;
import java.util.Random;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import gui.Main;

public class VisionModule1 implements VisionModule {
    public void run(Main app) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Mat m = Mat.zeros(300, 300, CvType.CV_8UC3);
                Random rand = new Random();
                for (int r = 0; r < m.rows(); ++r) {
                    for (int c = 0; c < m.cols(); ++c) {
                        m.put(r, c, new double[] {rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)});
                    }
                }
                Mat m2, m3;
                m2 = m.clone();
                m3 = m.clone();
                app.postImage(m, "Master");
                app.postImage(m2, "Master 2");
                app.postImage(m3, "Master 3");
            }
        }, "Vision Module Thread");
        t.setDaemon(true);
        t.start();
    }
}
