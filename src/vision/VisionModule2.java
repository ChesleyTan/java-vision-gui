package vision;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import gui.Main;

public class VisionModule2 implements VisionModule {
    private VideoCapture camera;
    public VisionModule2() {
        camera = new VideoCapture(0);
    }

    public void run(Main app) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (camera.isOpened()) {
                    Mat m = new Mat();
                    camera.read(m);
                    app.postImage(m, "Webcam");
                }
                else {
                    System.err.println("Camera not opened!");
                }
            }
        }, "Vision Module 2 Thread");
        t.setDaemon(true);
        t.start();
    }
}