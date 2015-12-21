package org.opencv.samples.boxGame;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.samples.framework.FrameWork;

public abstract class Shape {
	protected Point pos=new Point();//postion 
	protected Point vec=new Point();//speed direction
	protected boolean exist;
	protected List<MatOfPoint> ls=new ArrayList<MatOfPoint>();
	protected List<Point> lp=new ArrayList<Point>();
	protected int dx[]={-1,-1,1,1};
	protected int dy[]={-1,1,1,-1};
	public Point getPos() {
		return pos;
	}
	public  void change(Point p){
    	int tx=(int)p.x;
    	p.x=FrameWork.getInstance().getH()-p.y;
    	p.y=FrameWork.getInstance().getW()-tx;
    	
    }
	public  void change2(Point p){
    	int tx=(int)p.x;
    	p.x=FrameWork.getInstance().getW()-p.y;
    	p.y=FrameWork.getInstance().getH()-tx;
    	
    }
	public int getX(){
			return (int)pos.x;
	}
	public int getY(){
			return (int)pos.y;
		
	}
	
	public Point getVec() {
		return vec;
	}

	public void setVec(Point vec) {
		this.vec = vec;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}
	public void Move(){
		pos.x+=vec.x;
		pos.y+=vec.y;
	}
}
