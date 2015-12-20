package org.opencv.sample.boxGame;

import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class Ball extends Shape implements changeToOPencv{
	int R;
	private BoxGame game;
	
	public Ball() {
		// TODO Auto-generated constructor stub
	}
	public Ball(int x,int y,int r,BoxGame g){
		pos.x=x;
		pos.y=y;
		R=r;
		game=g;
		
	}
	public void move(){
		int speed = FrameWork.getInstance().getSpeed();
		pos.x+=vec.x*speed;
		pos.y+=vec.y*speed;
		
		int W=FrameWork.getInstance().getW();
		int H=FrameWork.getInstance().getH();
		if(pos.x>W) vec.x=-1;
		if(pos.x<0) vec.x=1;
		if(pos.y>H) vec.y=-1;
		
		boolean vis=false;
		for (Box b : game.getBoxs()) {
			if(!b.isExist()) continue;
			if(BoxIn(b)){
				vis=true;
				b.setExist(false);
			}
		}
		if(vis) {
			vec.y=-1;
		}
		
		if(BoxIn(game.getBlank())){
			vec.y=1;
			
		}
		
	}
	@Override
	public List<MatOfPoint> toOpenCv() {
		// TODO Auto-generated method stub
		ls.clear();
		lp.clear();
		int wid=FrameWork.getInstance().getPicture_width();
		for(int k=0;k<wid;k++)
			for(int i=0;i<4;i++){
					int xx=(int)pos.x+(dx[i]*R/2-k);
					int yy=(int)pos.y+(dy[i]*R/2-k);
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
		return p.x>pos.x-R/2 && p.x<pos.x+R/2 && p.y>pos.y-R/2 && p.y<pos.y+R/2;
	}

	public boolean BoxIn(Box box){
		for(int i=0;i<4;i++){
			int xx=(int)pos.x+(dx[i]*R/2);
			int yy=(int)pos.y+(dy[i]*R/2);
			if(box.pointIn3(new Point(xx,yy))) return true;
			
		}
		return false;
	}
	
}
