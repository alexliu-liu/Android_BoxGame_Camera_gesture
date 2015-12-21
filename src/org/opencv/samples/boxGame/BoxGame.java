package org.opencv.samples.boxGame;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Point;
import org.opencv.samples.framework.FrameWork;

import android.util.Log;

public class BoxGame {
    //game start ....
	private Ball ball;
	private List<Box> boxs=new ArrayList<Box>();
	private Box blank;
    private int row,col;
    private int W,H;
    private boolean start;
    
    public boolean isStart() {
		return start;
	}
	public void starting(){
		start=true;
		ball.setVec(new Point(1,1));
		
	}
	public BoxGame() {
		// TODO Auto-generated constructor stub
    	W=FrameWork.getInstance().getW();
    	H=FrameWork.getInstance().getH();
    	row=FrameWork.getInstance().getRow();
    	col=FrameWork.getInstance().getCol();
    	start=false;
    	int box_w=W/col;
    	int box_h=H/2/row;
    	boxs.clear();
    	for(int i=0;i<row;i++) 
    		for(int j=0;j<col;j++){
    			int xx=j*box_w+box_w/2;
    			int yy=H-i*box_h-box_h/2;
    			boxs.add(new Box(xx,yy,box_w,box_h,true));
    		}
    	int blank_w=W/3;
    	int blank_h=H/2/5;
    	blank=new Box(W/2,blank_h+blank_h/2,blank_w, blank_h, true);
    	
    	Log.i("game","w: "+W+"  H: "+H);
    	
    	int r=blank_h/2;
    	ball=new Ball(W/2,blank.getY()+blank.getHeight()/2+r/2, r,this);
    	ball.setExist(true);
    	
	}
    public Ball getBall() {
		return ball;
	}
	public List<Box> getBoxs() {
		return boxs;
	}
	public Box getBlank() {
		return blank;
	}
	

 

}
