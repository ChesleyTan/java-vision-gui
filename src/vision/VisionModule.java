package vision;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class VisionModule {
    public void run() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("OpenCV version: " + Core.VERSION);
                System.out
                        .println("Native library path: " + System.getProperty("java.library.path"));
                System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
                Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
                System.out.println("m = " + m.dump());
            }
        }, "Vision Module Thread");
        t.setDaemon(true);
        t.start();
    }
}
