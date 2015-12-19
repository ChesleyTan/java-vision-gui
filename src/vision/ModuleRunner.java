package vision;

import org.opencv.core.Core;

import gui.Main;

public class ModuleRunner {
    static {
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("Native library path: " + System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    private VisionModule[] modules = { new VisionModule1(), new VisionModule2() };
    private int FPS = 10;

    public void run(Main app) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    for (VisionModule module : modules) {
                        module.run(app);
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
