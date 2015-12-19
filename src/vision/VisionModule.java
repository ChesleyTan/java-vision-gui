package vision;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class VisionModule {
    public static void main(String[] args) {
        System.out.println("OpenCV version: " + Core.VERSION);
        System.out.println("Native library path: " + System.getProperty("java.library.path"));
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("m = " + m.dump());
    }
}
