package vision;

import java.util.HashMap;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import gui.Main;

public class ModuleRunner {
    private HashMap<VideoCapture, VisionModule[]> sourceDestMap;
    private int FPS = 10;
    static {
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("Native library path: " + System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    {
        sourceDestMap = new HashMap<VideoCapture, VisionModule[]>();
        sourceDestMap.put(new VideoCapture(0), new VisionModule[] { new VisionModule1(), new VisionModule2() });
    }

    public void run(Main app) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (VideoCapture captureSource : sourceDestMap.keySet()) {
                        if (captureSource.isOpened()) {
                            Mat frame = new Mat();
                            captureSource.read(frame);
                            for (VisionModule module : sourceDestMap.get(captureSource)) {
                                module.run(app, frame);
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000 / FPS);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }, "Module Runner Thread");
        t.setDaemon(true);
        t.start();
    }
}
