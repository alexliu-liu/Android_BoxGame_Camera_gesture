package org.opencv.sample.boxGame;

import java.util.List;
import java.util.Random;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class Box extends Shape implements changeToOPencv{
	int width,height;
	Scalar color;
	public Box() {
		// TODO Auto-generated constructor stub
	}
	public Box(int x,int y){
		pos.x=x;
		pos.y=y;
	}
	public Box(int x,int y,int w,int h,boolean e){
		pos.x=x;
		pos.y=y;
		width=w;
		height=h;
		exist=e;
		
		 Random rand=new Random();
    	 int r=rand.nextInt()%256;
    	 int g=rand.nextInt()%256;
    	 int b=rand.nextInt()%256;
    	 color=new Scalar(r,g,b);
		
	}
	public Scalar getColor() {
		return color;
	}
	public void setColor(Scalar color) {
		this.color = color;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	} 
	@Override
	public List<MatOfPoint> toOpenCv() {
		// TODO Auto-generated method stub
		ls.clear();
		lp.clear();
		int wid=FrameWork.getInstance().getPicture_width();
		for(int k=0;k<wid;k++)
			for(int i=0;i<4;i++){
					int xx=(int)pos.x+(dx[i]*width/2-k);
					int yy=(int)pos.y+(dy[i]*height/2-k);
					lp.add(new Point(xx,yy));
					
			}
		
		
		for (Point p : lp) {
			change(p);
		}
		MatOfPoint mp=new MatOfPoint();
		mp.fromList(lp);
		ls.add(mp);
		return ls;
	}
	@Override
	public boolean pointIn(Point p) {
		// TODO Auto-generated method stub
		change2(p);
		return p.x>pos.x-width/2 && p.x<pos.x+width/2 && p.y>pos.y-height/2 && p.y<pos.y+height/2;
		
	}
	public boolean pointIn3(Point p) {
		// TODO Auto-generated method stub
	
		return p.x>pos.x-width/2 && p.x<pos.x+width/2 && p.y>pos.y-height/2 && p.y<pos.y+height/2;
		
	}
}
