package vision;

import org.opencv.core.Mat;

import gui.Main;

public abstract class VisionModule {

    private Main app = null;
    /**
     * Processes an image contained in a Mat
     * @param frame the Mat containing the image
     */
    public abstract void run(Mat frame);

    /**
     * @return the name of this VisionModule class
     */
    public String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Associates this VisionModule with a GUI where information may be posted
     * <br>
     * <b>Note:</b> The Main class will automatically associate all known
     * VisionModules with it upon its initialization. Clients implementing
     * VisionModules need not call this method.
     * @param app
     *            the Main GUI application
     */
    public void setMainApp(Main app) {
        this.app = app;
    }

    /**
     * Posts an image to the GUI under the given {@code imageLabel}
     * @param m the Mat containing the image
     * @param label a unique label for this image
     */
    public void postImage(Mat m, String label) {
        app.postImage(m, label, this);
    }

    /**
     * Posts an image tag (arbitrary text) to the GUI under the given {@code imageLabel}
     * @param imageLabel the label of the image to which to add a tag
     * @param tagKey a unique key for this tag
     * @param tagValue the value to display for this tag
     * @return true if a tag was successfully posted, false otherwise
     */
    public boolean postTag(String imageLabel, String tagKey, String tagValue) {
        return app.postTag(imageLabel, tagKey, tagValue, this);
    }
}
