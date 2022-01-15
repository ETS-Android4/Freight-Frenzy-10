package org.firstinspires.ftc.teamcode.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Rect;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class BarcodeDetector {
    private OpenCvCamera camera;
    private String cameraName;
    private HardwareMap hardwareMap;
    private BarcodePipeline pipeline;
//    private Servo servo;

    public BarcodeDetector(HardwareMap hardwareMap, String cameraName){
        this.hardwareMap = hardwareMap;
        this.cameraName = cameraName;

        pipeline = new BarcodePipeline();
    }

    public void init(){
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, cameraName), cameraMonitorViewId);

        camera.setPipeline(pipeline = new BarcodePipeline());
        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            public void onError(int errorCode)
            {
                /*
                 * hope it works!
                 */
            }
        });
    }

    public Rect getBestRect() {
        return pipeline.getBestRect();
    }

    public void close(){
        camera.closeCameraDevice();
    }

    public void pause(){
        camera.pauseViewport();
    }
}
