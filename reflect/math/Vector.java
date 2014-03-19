package reflect.math;

/**
 * This class is not immutable!
 */
public class Vector {
	public double x;
	public double y;
	public double z;

	private double saved_x;
	private double saved_y;
	private double saved_z;
/**
 * Insert the method's description here.
 * 
 */
public Vector() {
	this.x = 0;
	this.y = 0;
	this.z = 0;
}
/**
 * Insert the method's description here.
 * 
 * @param x double
 * @param y double
 * @param z double
 */
public Vector(double x, double y, double z) {
	this.x = x;
	this.y = y;
	this.z = z;
}
/**
 * Insert the method's description here.
 * 
 */
public Vector(Vector b) {
	this.x = b.x;
	this.y = b.y;
	this.z = b.z;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public void add(Vector v) {
	x += v.x;
	y += v.y;
	z += v.z;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static Vector add(Vector a, Vector b) {
	return new Vector(a.x+b.x, a.y+b.y, a.z+b.z);
}
/**
 * Insert the method's description here.
 * 
 * @param b reflect.math.Vector
 */
public void crossProduct(Vector b) {
	double xnew =   y*b.z - z*b.y;
	double ynew = -(x*b.z - z*b.x);
	double znew =   x*b.y - y*b.x;

	x = xnew;
	y = ynew;
	z = znew;
}
/**
 * Insert the method's description here.
 * 
 * @return float
 * @param a reflect.math.Vector
 * @param b reflect.math.Vector
 */
public static Vector crossProduct(Vector a, Vector b) {
	return new Vector( (a.y*b.z - a.z*b.y),
	                  -(a.x*b.z - a.z*b.x),
	                   (a.x*b.y - a.y*b.x) );
}
/**
 * Insert the method's description here.
 * 
 * @return double
 * @param p reflect.math.Vector
 */
public double distanceFrom(Vector p) {
	double temp_x = x;
	double temp_y = y;
	double temp_z = z;
	double dist;
	
	subtract(p);
	dist = length();

	x = temp_x;
	y = temp_y;
	z = temp_z;
	
	return dist;
}
/**
 * Insert the method's description here.
 * 
 * @param c double
 */
public void divideByScalar(double c) {
	x /= c;
	y /= c;
	z /= c;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static Vector divideByScalar(Vector a, double c) {
	return new Vector(a.x/c, a.y/c, a.z/c);
}
/**
 * Insert the method's description here.
 * 
 * @return double
 * @param b reflect.math.Vector
 */
public double dotProduct(Vector b) 
{
	return x*b.x + y*b.y + z*b.z;
}
/**
 * Insert the method's description here.
 * 
 * @return double
 * @param a reflect.math.Vector
 * @param b reflect.math.Vector
 */
public static double dotProduct(Vector a, Vector b) {
	return a.x*b.x + a.y*b.y + a.z*b.z;
}
/**
 * Insert the method's description here.
 * 
 */
public double length() {
	double lengthSquared = dotProduct(this);
	return Math.sqrt(lengthSquared);
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static double length(Vector v) {
	double lengthSquared = Vector.dotProduct(v, v);
	return Math.sqrt(lengthSquared);
}
/**
 * Insert the method's description here.
 * 
 */
public void normalize() {
	divideByScalar(length());
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static Vector normalize(Vector a) {
	Vector r = new Vector(a);
	r.divideByScalar(r.length());
	return r;
}
/**
 * Insert the method's description here.
 * 
 */
public void restore() {
	x = saved_x;
	y = saved_y;
	z = saved_z;
}
/**
 * Insert the method's description here.
 * 
 */
public void save() {
	saved_x = x;
	saved_y = y;
	saved_z = z;
}
/**
 * Insert the method's description here.
 * 
 * @param c double
 */
public void scale(double c) {
	x *= c;
	y *= c;
	z *= c;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static Vector scale(Vector a, double c) {
	return new Vector(a.x*c, a.y*c, a.z*c);
}
/**
 * Insert the method's description here.
 * 
 * @param x double
 * @param y double
 * @param z double
 */
public void set(double x, double y, double z) {
	this.x = x;
	this.y = y;
	this.z = z;
}
/**
 * Insert the method's description here.
 * 
 * @param x double
 * @param y double
 * @param z double
 */
public void set(Vector a) {
	this.x = a.x;
	this.y = a.y;
	this.z = a.z;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public void subtract(Vector v) {
	x -= v.x;
	y -= v.y;
	z -= v.z;
}
/**
 * Insert the method's description here.
 * 
 * @param v reflect.math.Vector
 */
public static Vector subtract(Vector a, Vector b) {
	return new Vector(a.x-b.x, a.y-b.y, a.z-b.z);
}
/**
 * Insert the method's description here.
 * 
 * @return java.lang.String
 */
public String toString() {
	StringBuffer buf = new StringBuffer();
	buf.append('{');
	buf.append(x).append(',').append(' ');
	buf.append(y).append(',').append(' ');
	buf.append(z).append('}');
	return buf.toString();
}
}
