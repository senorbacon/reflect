package reflect;

import java.awt.*;

/**
 * Insert the type's description here.
 */
public class GraphicsAccess {
	int pixelsX, pixelsY;
	double aspectRatio = 1.234;
/**
 * Insert the method's description here.
 * 
 * @return double
 */
public double getAspectRatio() {
	return aspectRatio;
}
/**
 * Insert the method's description here.
 * 
 * @return int
 */
public int getPixelsX() {
	return pixelsX;
}
/**
 * Insert the method's description here.
 * 
 * @return int
 */
public int getPixelsY() {
	return pixelsY;
}
/**
 * Insert the method's description here.
 * 
 * @param x int
 * @param y int
 * @param color java.awt.Color
 */
public void setColor(int x, int y, Color color) {}
}
