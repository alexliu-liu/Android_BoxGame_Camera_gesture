package org.opencv.samples.boxGame;

import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.samples.framework.FrameWork;

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
		double l,r,u,d;
		l=pos.x-R/2;
		r=pos.x+R/2;
		u=pos.y+R/2;
		d=pos.y-R/2;
		//boolean vis=false;
		boolean up,down,right,left;
		up=down=left=right=false;
		for (Box b:game.getBoxs()) {
			if(!b.isExist()) continue;
			double L,R,U,D;
			L=b.getX()-b.getWidth()/2;
			R=b.getX()+b.getWidth()/2;
			U=b.getY()+b.getHeight()/2;
			D=b.getY()-b.getHeight()/2;
			double maxW=Math.max(R,r)-Math.min(L,l);
			double maxH=Math.max(U,u)-Math.min(D,d);
			
			if(maxW>=b.getWidth()+R && maxH>=b.getHeight()+R) continue;
//			if(prey<=b.getY() && pos.y>b.getY() && pos.x<){ up=true;b.setExist(false);}
//			if(prey>=b.getY() && pos.y<b.getY()) {down=true;b.setExist(false);}
//			if(prex<=b.getX() && pos.x>b.getX()) {right=true;b.setExist(false);}
//			if(prex>=b.getX() && pos.x<b.getX()) {left=true;b.setExist(false);}
//			if(BoxIn(b)){
//				touch=true;
				if(touchDownBox(b)) {down=true;b.setExist(false);}
				if(touchUpBox(b)) {up=true;b.setExist(false);}
				if(touchLeftBox(b)) {left=true;b.setExist(false);}
				if(touchRightBox(b)) {right=true;b.setExist(false);}
//				if(up) vec.y=-1;
//				else if(down) vec.y=1;
//				if(right) vec.x=-1;
//				else if(left) vec.x=1;
				
//			}
		}
		if(up) vec.y=-1;
		else if(down) vec.y=1;
		
		if(left) vec.x=1;
		else if(right) vec.x=-1;
	
		
		
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
	public boolean touchLeftBox(Box b){
		return BoxIn(b) && vec.x==-1;
		
	}

	public boolean touchRightBox(Box b){
		return BoxIn(b) && vec.x==1;
		
	}
	

	public boolean touchDownBox(Box b){
		return BoxIn(b) && vec.y==-1;
		
	}
	

	public boolean touchUpBox(Box b){
		return BoxIn(b) && vec.y==1;
		
	}
	
}
