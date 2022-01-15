package org.firstinspires.ftc.teamcode.subsystem;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class BarcodePipeline extends OpenCvPipeline {

    //left is high, middle is middle, right is low

    private Mat mat = new Mat();
    private Mat ret = new Mat();

    private Scalar lowerBound = new Scalar(0.0, 0.0, 0.0);
    private Scalar upperBound = new Scalar(255.0, 255.0, 255.0);

    private Rect bestRect = new Rect();

    @Override
    public Mat processFrame(Mat input) {
        ret.release();
        ret = new Mat();
        try {
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2YCrCb);

            Mat mask = new Mat(mat.rows(), mat.cols(), CvType.CV_8UC1);

            Core.inRange(mat, lowerBound, upperBound, mask);
            Core.bitwise_and(input, input, ret, mask);

            Imgproc.GaussianBlur(mask, mask, new Size(5.0, 15.0), 0.00);

            Mat hierarchy = new Mat();

            List<MatOfPoint> contours = new ArrayList<>();

            Imgproc.findContours(mask,contours,hierarchy,Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
            Imgproc.drawContours(ret, contours, -1, new Scalar(0.0, 255.0, 0.0), 3);

            int maxWidth = 0;
            bestRect = new Rect();
            for(MatOfPoint contour : contours){
                Rect rect = Imgproc.boundingRect(contour);

                int width = rect.width;
                if(width > maxWidth && width < 100 /*&& rect.y + rect.height > HORIZON*/){
                    maxWidth = width;
                    bestRect = rect;
                }
                contour.release();
            }

            Imgproc.rectangle(ret, bestRect, new Scalar(0.0, 0.0, 255.0), 2);

            ret = mat;

            mat.release();
            mask.release();
            hierarchy.release();

        } catch (Exception e){
            //hope for the best!
        }
        return ret;
    }

    public Rect getBestRect() {
        return bestRect;
    }
}
