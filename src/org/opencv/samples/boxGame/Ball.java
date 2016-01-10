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
//		double prex=pos.x;
//		double prey=pos.y;
		pos.x+=vec.x*speed;
		pos.y+=vec.y*speed;
		
		int W=FrameWork.getInstance().getW();
		int H=FrameWork.getInstance().getH();
		if(pos.x>W) {vec.x=-1;return;}
		if(pos.x<0) {vec.x=1;return;}
		if(pos.y>H) {vec.y=-1;return;}
		
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
			double bL,bR,bU,bD;
			bL=b.getX()-b.getWidth()/2;
			bR=b.getX()+b.getWidth()/2;
			bU=b.getY()+b.getHeight()/2;
			bD=b.getY()-b.getHeight()/2;
			double maxW=Math.max(bR,r)-Math.min(bL,l);
			double maxH=Math.max(bU,u)-Math.min(bD,d);
			
			if(maxW>=b.getWidth()+R || maxH>=b.getHeight()+R) continue;
//			if(prey<=b.getY() && pos.y>b.getY() && pos.x<){ up=true;b.setExist(false);}
//			if(prey>=b.getY() && pos.y<b.getY()) {down=true;b.setExist(false);}
//			if(prex<=b.getX() && pos.x>b.getX()) {right=true;b.setExist(false);}
//			if(prex>=b.getX() && pos.x<b.getX()) {left=true;b.setExist(false);}
//			if(BoxIn(b)){
//				touch=true;
//			Log.i("maxW","maxWpos.x: "+pos.x+"  pos.y: "+pos.y+"  R :"+R);
//			Log.i("maxW"," maxW bX: "+b.getX()+"  bY: "+b.getY()+"  W :"+b.getWidth()+" H: "+b.getHeight());
//			Log.i("maxW"," maxW  jiange W: "+maxW+" jiange H: "+maxH);
				if(vec.y==-1 && pos.y>=b.getY()) {down=true;b.setExist(false);}//touchDownBox(b)
				if(vec.y==1 && pos.y<=b.getY()) {up=true;b.setExist(false);}//touchUpBox(b)
				if(vec.x==-1 && pos.x>=b.getX()) {left=true;b.setExist(false);}//touchLeftBox(b)
				if(vec.x==1 && pos.x<=b.getX()) {right=true;b.setExist(false);}//touchRightBox(b)
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
//	public boolean touchLeftBox(Box b){
//		return BoxIn(b) && vec.x==-1;
//		
//	}
//
//	public boolean touchRightBox(Box b){
//		return BoxIn(b) && vec.x==1;
//		
//	}
//	
//
//	public boolean touchDownBox(Box b){
//		return BoxIn(b) && vec.y==-1;
//		
//	}
//	
//
//	public boolean touchUpBox(Box b){
//		return BoxIn(b) && vec.y==1;
//		
//	}
	
}
