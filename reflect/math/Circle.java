package reflect.math;

/**
 * Insert the type's description here.
 */
public class Circle {
	public double radius;
	public Vector center = new Vector();
/**
 * This method assumes the given point is on the same plane as the circle of inclusion
 * 
 * @return boolean
 * @param p reflect.math.Vector
 */
public boolean isInCircle(Vector p) {
	boolean inside = false;
	double x = p.x;
	double y = p.y;
	double z = p.z;
	
	p.subtract(center);

	if (p.length() < (radius + Lib.REASONABLE_ZERO) )
		inside = true;

	p.x = x;
	p.y = y;
	p.z = z;
	
	return inside;
}
}
