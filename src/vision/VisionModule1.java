package vision;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import java.util.Random;

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
                app.postImage(m, "Master");
            }
        }, "Vision Module Thread");
        t.setDaemon(true);
        t.start();
    }
}
