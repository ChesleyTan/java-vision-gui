package vision;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class CaptureSource {
    private String filename = null;
    private Integer device = null;
    private VideoCapture capture = null;
    private int maxImageDimension = 400;
    public CaptureSource(String filename) {
        this.filename = filename;
        reinitializeCaptureSource();
    }
    public CaptureSource(int device) {
        this.device = device;
        reinitializeCaptureSource();
    }
    public VideoCapture getCapture() {
        return capture;
    }
    public void reinitializeCaptureSource() {
        if (capture != null) {
            capture.release();
        }
        if (filename != null) {
            capture = new VideoCapture(filename);
        }
        else if (device != null) {
            capture = new VideoCapture(device);
        }
    }
    public void setMaxImageDimension(int dim) {
        this.maxImageDimension = dim;
    }
    public boolean isOpened() {
        return capture.isOpened();
    }
    public Mat read() {
        Mat frame = new Mat();
        boolean success = capture.read(frame);
        if (success) {
            int frameHeight = frame.height();
            int frameWidth = frame.width();
            double resizeRatio = (double) maxImageDimension / Math.max(frameHeight, frameWidth);
            Size desiredSize = new Size(frameWidth * resizeRatio, frameHeight * resizeRatio);
            Mat resizedFrame = new Mat();
            Imgproc.resize(frame, resizedFrame, desiredSize, 0, 0, Imgproc.INTER_CUBIC);
            return resizedFrame;
        }
        else {
            return null;
        }
    }
}
