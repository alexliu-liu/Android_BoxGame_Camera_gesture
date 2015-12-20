package org.opencv.sample.boxGame;

public class FrameWork {
    private static int W,H;
    private static int row,col;
    private static int speed;
    private static int hand_speed;
    private static int picture_width;
	public static int getPicture_width() {
		return picture_width;
	}
	public static void setPicture_width(int picture_width) {
		FrameWork.picture_width = picture_width;
	}
	public static int getHand_speed() {
		return hand_speed;
	}
	public static void setHand_speed(int hand_speed) {
		FrameWork.hand_speed = hand_speed;
	}
	public static int getSpeed() {
		return speed;
	}
	public static void setSpeed(int speed) {
		FrameWork.speed = speed;
	}
	public static int getRow() {
		return row;
	}
	public static void setRow(int row) {
		FrameWork.row = row;
	}
	public static int getCol() {
		return col;
	}
	public static void setCol(int col) {
		FrameWork.col = col;
	}
	private static FrameWork framework; 
	public static FrameWork getInstance(){
		if(framework==null) {
			framework=new FrameWork();
			setW(1080);
			setH(1920);
			setRow(5);
			setCol(6);
			setSpeed(60);
			setHand_speed(30);
			setPicture_width(5);
		}
		return framework;
	}
	public static int getW() {
		return W;
	}
	public static void setW(int w) {
		W = w;
	}
	public static int getH() {
		return H;
	}
	public static void setH(int h) {
		H = h;
	}
	
	
}
