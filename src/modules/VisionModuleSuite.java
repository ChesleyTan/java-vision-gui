package modules;

import vision.DeviceCaptureSource;
import vision.ImageCaptureSource;
import vision.ModuleRunner;

public class VisionModuleSuite {

    /**
     * Add any mappings here from capture sources to vision modules
     * Available capture sources:
     *   - DeviceCaptureSource
     *   - VideoCaptureSource
     *   - ImageCaptureSource
     */
    static {
        String imageDirectory = VisionModuleSuite.class.getResource("").getPath() + "../../images/";
        //ModuleRunner.addMapping(new DeviceCaptureSource(0, 300), new VisionModule1());
        ModuleRunner.addMapping(new DeviceCaptureSource(0), new VisionModule2());
        ModuleRunner.addMapping(new ImageCaptureSource(imageDirectory + "minions-banana.jpg", 230), new VisionModule2());
    }
}
