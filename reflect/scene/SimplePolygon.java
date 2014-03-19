package reflect.scene;

import reflect.math.*;
import java.awt.Color;
/**
 * Insert the type's description here.
 */
public class SimplePolygon extends Surface {
	Vector points[];

	Color color;
	
	Vector normal;
	
	// circle of inclusion
	protected Circle coi;
/**
 * Insert the method's description here.
 * 
 * @param reflective boolean
 */
public SimplePolygon(boolean reflective, Color color, Vector points[]) {
	super(reflective);
	this.color = color;
	this.points = points;

	init();
}
/**
 * Insert the method's description here.
 * 
 * @param reflective boolean
 */
public SimplePolygon(boolean reflective, Color color, Vector p0, Vector p1, Vector p2) {
	super(reflective);
	this.color = color;
	
	points = new Vector[3];
	points[0] = p0;
	points[1] = p1;
	points[2] = p2;

	init();
}

/**
 * Insert the method's description here.
 * 
 * @param reflective boolean
 */
public SimplePolygon(boolean reflective, Color color, Vector p0, Vector p1, Vector p2, Vector p3) {
	super(reflective);
	this.color = color;
	
	points = new Vector[4];
	points[0] = p0;
	points[1] = p1;
	points[2] = p2;
	points[3] = p3;

	init();
}

/**
 * Insert the method's description here.
 * 
 */
public void calcCircleOfInclusion()
{
	coi = Lib.findCircleOfInclusion(points);
	//System.out.println("coi.center is in plane: " + isInPlane(coi.center));
}
/**
 * Insert the method's description here.
 * 
 */
public void calcNormalVector()
{
	Vector s1, s2;
	s1 = Vector.subtract(points[1], points[0]);
	s2 = Vector.subtract(points[2], points[1]);
	
	normal = Vector.crossProduct(s1, s2);
	normal.normalize();
}
/**
 * Insert the method's description here.
 * 
 */
public void checkAllPointsAreInPlane()
{
	// only makes sense for polygons of 4 or more points
	if (points.length <= 3)
		return;

	for (int i=3; i<points.length; i++)
	{
		if (!isInPlane(points[i]))
			System.out.println("***** WARNING! point #" + (i+1) + " is not in the plane.");
	}
}
/**
 * Insert the method's description here.
 * 
 */
public void clonePoints() {
	for (int i=0; i<points.length; i++)
		points[i] = new Vector(points[i]);
}
/**
 * colorForPoint method comment.
 */
public java.awt.Color colorForPoint(reflect.math.Vector p) {
	return color;
}
/**
 * Insert the method's description here.
 * 
 * @return boolean
 * @param p reflect.math.Vector
 */
public boolean containsPoint(Vector p) {
	return false;
}
public void init()
{
	clonePoints();
	
	calcNormalVector();

	checkAllPointsAreInPlane();
	
	calcCircleOfInclusion();
}
/**
 * Insert the method's description here.
 * 
 * @return boolean
 * @param p reflect.math.Vector
 */
public boolean isInPlane(Vector p) {
	Vector s = points[0];
	double result;
	
	// from equation of plane
	result = normal.x*(p.x-s.x) + normal.y*(p.y-s.y) + normal.z*(p.z-s.z);
	if (result < 0)
		result = -result;

	//System.out.println("isInPlane: " + result);

	if (result < Lib.REASONABLE_ZERO)
		return true;
	else
		return false;
}
/**
 * Insert the method's description here.
 * 
 * @return reflect.math.Vector
 * @param p reflect.math.Vector
 */
public void normalAtPoint(Vector p, Vector normal)
{
	normal.x = this.normal.x;
	normal.y = this.normal.y;
	normal.z = this.normal.z;
}
}
