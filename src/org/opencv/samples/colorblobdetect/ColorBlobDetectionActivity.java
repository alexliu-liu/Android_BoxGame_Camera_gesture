package org.opencv.samples.colorblobdetect;

import java.util.List;
import java.util.Random;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.sample.boxGame.Ball;
import org.opencv.sample.boxGame.Box;
import org.opencv.sample.boxGame.BoxGame;
import org.opencv.sample.boxGame.FrameWork;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;

public class ColorBlobDetectionActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG              = "OCVSample::Activity";

    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;
    private Scalar 				 COLOR1,COLOR2,COLOR3,COLOR4;
    private BoxGame game;
    private CameraBridgeViewBase mOpenCvCameraView;
    private double  pre,now;
    static { if (!OpenCVLoader.initDebug()) { // Handle initialization error } 
	}
}
    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(ColorBlobDetectionActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public ColorBlobDetectionActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.color_blob_detection_surface_view);
      
        game =new BoxGame();
       
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
       // OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }


    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
        COLOR1 = new Scalar(0,255,0,255);
        COLOR2= new Scalar(0,0,255,255);
        COLOR3 = new Scalar(255,0,0,30);
        
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public boolean onTouch(View v, MotionEvent event) {
        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int cx=mOpenCvCameraView.getWidth();
        int cy=mOpenCvCameraView.getHeight();
        int xOffset = (cx - cols) / 2;
        int yOffset = (cy- rows) / 2;

        int x = (int)event.getX() - xOffset;
        int y = (int)event.getY() - yOffset;
        Log.i("touching...event ", "Touch rows cols" + (int)event.getX() + ", " + (int)event.getY() + ")");
        Log.i("touching...rows cols", "Touch rows cols" + rows + ", " + cols + ")");
        Log.i("touching...", "Touch image coordinates: (" + x + ", " + y + ")");
        Log.i("touching...", "start???: "+game.isStart());
        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;
        
        if(!game.isStart() && game.getBlank().pointIn(new Point(x,y))) game.starting();
        Rect touchedRect = new Rect();

        touchedRect.x = (x>4) ? x-4 : 0;
        touchedRect.y = (y>4) ? y-4 : 0;

        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width*touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);

        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");

        mDetector.setHsvColor(mBlobColorHsv);

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE);

        mIsColorSelected = true;

        touchedRegionRgba.release();
        touchedRegionHsv.release();

        return false; // don't need subsequent touch events
    }
    static int haha=0;
    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        mRgba = inputFrame.rgba();

        if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            Log.e(TAG, "Contours count: " + contours.size());
            Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);
            
           // int r=mRgba.rows();
           // int c=mRgba.cols();
           // game.set(r, c);
            now=0;
            int count=0;
            for (MatOfPoint mp : contours) {
				List<Point> ls=mp.toList();
				for (Point p : ls) {
					now+=p.y;
					count++;
					
				}
			}
            now/=count;
            Box blank=game.getBlank();
            pre=FrameWork.getInstance().getW()-blank.getX();
            if(now-pre>=FrameWork.getInstance().getHand_speed()) {
            	blank.setPos(new Point(FrameWork.getInstance().getW()-now,blank.getPos().y));
            }
            else if(pre-now>=FrameWork.getInstance().getHand_speed()){
            	blank.setPos(new Point(FrameWork.getInstance().getW()-now,blank.getPos().y));
            }
            Log.i("pre and now","pre :"+pre+"  now: "+now);
            
            
//            Imgproc.drawContours(mRgba, game.getBox(), -1, CONTOUR_COLOR);
           // Point cp=(blank.change(blank.getPos()));
            Log.e("blank","  blank x: "+blank.getX()+"  y: "+blank.getY()+"   wid: "+blank.getWidth()+"   height:  "+blank.getHeight());
           // Log.e("after blank","  blank x: "+cp.x+"  y: "+cp.y+"   wid: "+blank.getWidth()+"   height:  "+blank.getHeight());
            Imgproc.drawContours(mRgba, game.getBlank().toOpenCv(), -1, CONTOUR_COLOR);
            Imgproc.drawContours(mRgba, game.getBall().toOpenCv(), -1, COLOR1);
            List<Box> boxs=game.getBoxs();
            for(int i=0;i<boxs.size();i++){
            	 if(!boxs.get(i).isExist()) continue;
            	
            	 
            	   Imgproc.drawContours(mRgba,boxs.get(i).toOpenCv(), -1,boxs.get(i).getColor() );
            }
            
            if(game.isStart()) game.getBall().move();
           
            
            

            
        
            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
            colorLabel.setTo(mBlobColorRgba);

            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
            mSpectrum.copyTo(spectrumLabel);
        }

        return mRgba;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }
}
