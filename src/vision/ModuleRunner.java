package vision;

import java.util.HashMap;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import gui.Main;
import util.DebugPrinter;

public class ModuleRunner {
    private HashMap<CaptureSource, VisionModule[]> sourceDestMap = new HashMap<CaptureSource, VisionModule[]>();
    private int FPS = 10;

    static {
        DebugPrinter.println("OpenCV version: " + Core.VERSION);
        DebugPrinter.println("Native library path: " + System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    {
        // NOTE: Select which CaptureSources and VisionModules you want to run by adding them to the sourceDestMap
        sourceDestMap.put(new DeviceCaptureSource(0, 300), new VisionModule[] { new VisionModule1(), new VisionModule2() });
    }

    public void run(Main app) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (CaptureSource captureSource : sourceDestMap.keySet()) {
                        if (captureSource.isOpened()) {
                            Mat frame = captureSource.read();
                            if (frame == null) {
                                // FIXME: We're reinitializing the capture source so we can loop it when we've reached
                                // the end of the stream. The proper method would be to set the frame pointer for the
                                // source to point back to the beginning of the stream, but this method does not
                                // reliably work.
                                captureSource.reinitializeCaptureSource();
                                DebugPrinter.println("Looping capture source");
                            }
                            else {
                                for (VisionModule module : sourceDestMap.get(captureSource)) {
                                    Thread t = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            module.run(app, frame);
                                        }
                                    }, module.getName() + " Thread");
                                    t.setDaemon(true);
                                    t.start();
                                }
                            }
                        }
                    }
                    try {
                        Thread.sleep(1000 / FPS);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }, "Module Runner Thread");
        t.setDaemon(true);
        t.start();
    }

}
