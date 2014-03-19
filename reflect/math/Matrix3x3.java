package reflect.math;

/**
 * Insert the type's description here.
 */
public class Matrix3x3 {
	double elements[][] = { {0,0,0}, {0,0,0}, {0,0,0} };
/**
 * Insert the method's description here.
 * 
 * @param r1 reflect.math.Vector
 * @param r2 reflect.math.Vector
 * @param r3 reflect.math.Vector
 */
public Matrix3x3(Vector r0, Vector r1, Vector r2) {
	set(r0, r1, r2);
}
/**
 * Insert the method's description here.
 * 
 * @return double
 */
public double determinant() {
	double p1, p2, p3;

	p1 = elements[0][0] * (elements[1][1]*elements[2][2] - elements[1][2]*elements[2][1]);
	p2 = elements[0][1] * (elements[1][0]*elements[2][2] - elements[1][2]*elements[2][0]);
	p3 = elements[0][2] * (elements[1][0]*elements[2][1] - elements[1][1]*elements[2][0]);
	return p1 - p2 + p3;
}
/**
 * Insert the method's description here.
 * 
 */
public void set(Vector r0, Vector r1, Vector r2) {
	elements[0][0] = r0.x;
	elements[0][1] = r0.y;
	elements[0][2] = r0.z;
	elements[1][0] = r1.x;
	elements[1][1] = r1.y;
	elements[1][2] = r1.z;
	elements[2][0] = r2.x;
	elements[2][1] = r2.y;
	elements[2][2] = r2.z;
}
}
