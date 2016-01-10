package vision;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageCaptureSource extends CaptureSource {
    private final String filename;
    private Mat mat = null;

    public ImageCaptureSource(String filename) {
        this.filename = filename;
        reinitializeCaptureSource();
    }

    @Override
    public void reinitializeCaptureSource() {
        mat = Imgcodecs.imread(filename);
    }

    @Override
    public boolean isOpened() {
        return mat != null;
    }

    @Override
    public boolean readFrame(Mat mat) {
        this.mat.copyTo(mat);
        return true;
    }
}
