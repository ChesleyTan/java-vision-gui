package vision;

import org.opencv.core.Mat;

import gui.Main;

public interface VisionModule {
    public void run(Main app, Mat frame);
}
