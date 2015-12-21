package vision;

import org.opencv.core.Mat;

import gui.Main;

public class VisionModule2 implements VisionModule {
    public void run(Main app, Mat frame) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                app.postImage(frame, "Camera", VisionModule2.this);
            }
        }, "Vision Module 2 Thread");
        t.setDaemon(true);
        t.start();
    }
}