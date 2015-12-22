package modules;

import vision.DeviceCaptureSource;
import vision.ModuleRunner;

public class VisionModuleSuite {

    /*
     * Add any mappings here from capture sources to vision modules
     * Available capture sources:
     *   - DeviceCaptureSource
     *   - VideoCaptureSource
     *   - ImageCaptureSource
     */
    static {
        ModuleRunner.addMapping(new DeviceCaptureSource(0, 300), new VisionModule1(), new VisionModule2());
    }

}
