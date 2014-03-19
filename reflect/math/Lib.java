package reflect.math;

/**
 * Insert the type's description here.
 */
public class Lib {
	public static final double REASONABLE_ZERO = 0.00000000001;


/**
 *
 */
public static Circle findCircleOfInclusion(Vector points[])
{
	Vector temp0 = new Vector();
	Vector temp1 = new Vector();
	Vector temp2 = new Vector();

	Vector p0, p1, p2;

	Vector s0, s1, s2;

	double d0, d1, d2;
	double a, b, c;
	double delta;
	
	Circle coi = new Circle();
	
	int numPoints = points.length;
	
	if (numPoints < 3)
		return null;

	p0 = points[0];
	p1 = points[1];
	p2 = points[2];

	s0 = Vector.subtract(p1, p0);
	s1 = Vector.subtract(p2, p1);
	s2 = Vector.subtract(p0, p2);

	a =   s0.y*s1.z - s0.z*s1.y;
	b = -(s0.x*s1.z - s0.z*s1.x);
	c =   s0.x*s1.y - s0.y*s1.x;

	d0 = .5 * (p2.dotProduct(p2) - p1.dotProduct(p1) );
	d1 = .5 * (p0.dotProduct(p0) - p2.dotProduct(p2) );
	d2 = p0.x*a + p0.y*b + p0.z*c;

	temp0.set(a, b, c);
	
	Matrix3x3 matrix = new Matrix3x3(s1, s2, temp0);
	delta = matrix.determinant();

	if (delta == 0) {
		System.out.println("fuckin delta is zero.");
		return null;
	}
	
	// for finding center.x
	temp0.set(s1);
	temp1.set(s2);
	temp2.set(a, b, c);
	temp0.x = d0;
	temp1.x = d1;
	temp2.x = d2;

	matrix.set(temp0, temp1, temp2);
	coi.center.x = matrix.determinant() / delta;
		
	// for finding center.y
	temp0.set(s1);
	temp1.set(s2);
	temp2.set(a, b, c);
	temp0.y = d0;
	temp1.y = d1;
	temp2.y = d2;

	matrix.set(temp0, temp1, temp2);
	coi.center.y = matrix.determinant() / delta;
		
	// for finding center.z
	temp0.set(s1);
	temp1.set(s2);
	temp2.set(a, b, c);
	temp0.z = d0;
	temp1.z = d1;
	temp2.z = d2;

	matrix.set(temp0, temp1, temp2);
	coi.center.z = matrix.determinant() / delta;

	temp0.set(p0);
	temp0.subtract(coi.center);
	coi.radius = temp0.length();

	System.out.println("p0: " + p0);
	System.out.println("p1: " + p1);
	System.out.println("p2: " + p2);
	System.out.println("center: " + coi.center);
	System.out.println("radius: " + coi.radius);

	// make sure circle encloses the rest of the points
	for (int i=3; i<numPoints; i++)
	{
		temp0.set(points[i]);
		if (!coi.isInCircle(temp0)) {
			coi.radius = temp0.distanceFrom(coi.center) + REASONABLE_ZERO;
			System.out.println("Increasing radius to " + coi.radius);
		}
	}
	
	return coi;	
}
}
