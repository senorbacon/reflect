package reflect.scene;

import reflect.math.*;
import java.awt.Color;

/**
 * Insert the type's description here.
 */
public class SimpleSphere extends Surface {
	public Vector center;
	public double radius;

	Color color;
/**
 * Insert the method's description here.
 * 
 * @param reflective boolean
 * @param color java.awt.Color
 */
public SimpleSphere(boolean reflective, Color color) {
	super(reflective);

	// meaningless if reflective
	this.color = color;
}
/**
 * colorForPoint method comment.
 */
public Color colorForPoint(Vector p) {
	return color;
}
/**
 * normalAtPoint method comment.
 */
public void normalAtPoint(reflect.math.Vector p, reflect.math.Vector normal) {
	normal.set(p.x - center.x, p.y - center.y, p.z - center.z);
	normal.normalize();
}
}
