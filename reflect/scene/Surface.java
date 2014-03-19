package reflect.scene;

import reflect.math.*;
import java.awt.Color;

/**
 * Insert the type's description here.
 */
public abstract class Surface {
	boolean isReflective;
/**
 * Insert the method's description here.
 * 
 * @param reflective boolean
 */
public Surface(boolean reflective) {
	isReflective = reflective;
}
/**
 * Insert the method's description here.
 * 
 * @return java.awt.Color
 * @param p reflect.math.Vector
 */
public abstract Color colorForPoint(Vector p);
public boolean isReflective() {
	return isReflective;
}
/**
 * Insert the method's description here.
 * 
 * @return reflect.math.Vector
 * @param p reflect.math.Vector
 */
public abstract void normalAtPoint(Vector p, Vector normal);
}
