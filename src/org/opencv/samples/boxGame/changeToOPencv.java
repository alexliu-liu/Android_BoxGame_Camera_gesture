package org.opencv.samples.boxGame;

import java.util.List;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public interface changeToOPencv {
	public List<MatOfPoint> toOpenCv();
	boolean pointIn(Point p);
}
