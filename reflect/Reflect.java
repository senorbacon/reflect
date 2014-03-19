package reflect;

import reflect.math.*;
import reflect.scene.*;

import java.awt.Color;

/**
 * Insert the type's description here.
 */
public class Reflect {
	public static final int MAX_SURFACES=32;

	public static final int REFLECT_LIMIT = 40;
	public static final double REFLECT_BRIGHTNESS[] = new double[REFLECT_LIMIT];
	public static final double REFLECTION_LOSS = .93;

	static {
		double b = 1.0;
		for (int i=0; i<REFLECT_LIMIT; i++)
		{
			REFLECT_BRIGHTNESS[i] = b;
			b *= REFLECTION_LOSS;
		}
	}
	
	Surface scene[];
	int surfaceNum;
	
	GraphicsAccess graphics;


	// things used in calculation
	double d;
	Color color;
	float colorComps[] = new float[3];

	int numReflections;
	int closestSurface;
	double closestDistance;

	Surface hitSurface;

	// point of intersection
	Vector poi = new Vector();
	
	// new position/view due to reflection
	Vector pos = new Vector();
	Vector view = new Vector();

	// used in calculation of reflected view vector
	Vector viewComponent = new Vector();
/**
 * Insert the method's description here.
 * 
 */
public void drawScene() {

	double vx = 1; 
	double vy = 1;
	double vz = 1;

	double fieldAngle = 60.0;
	
	Vector initialPos = new Vector(.1, .1, .1);
	Vector view = new Vector(vx, vy, vz);
	Vector viewXY = new Vector(vy, -vx, 0);
	Vector viewZ = new Vector(-vx*vz, -vy*vz, vx*vx + vy*vy);

	// if view vector is straight up (0, 0, 1), then the orthogonal vector
	// in the XY plane is (0, 0, 0), so we'll arbitrarily set up the orthogonols
	if (viewXY.length() == 0) {
		viewXY.set(1, 0, 0);
		viewZ.set(0, 1, 0);
	}

	view.normalize();
	viewXY.normalize();
	viewZ.normalize();
	
	double viewAngleX = (fieldAngle/360.0) * Math.PI;
	double aspectRatio = graphics.getAspectRatio();
	double viewAngleY = viewAngleX/aspectRatio;

	Vector horizViewComp = Vector.scale(view, Math.cos(viewAngleX));
	Vector horizViewXYComp = Vector.scale(viewXY, Math.sin(viewAngleX));
	Vector vertViewComp = Vector.scale(view, Math.cos(viewAngleY));
	Vector vertViewZComp = Vector.scale(viewZ, Math.sin(viewAngleY));
	
	Vector cornerUL = Vector.add(Vector.subtract(horizViewComp, horizViewXYComp),
		                         Vector.add(vertViewComp, vertViewZComp) );

	viewXY.set(horizViewXYComp);
	viewXY.scale(2.0);
	viewZ.set(vertViewZComp);
	view.scale(-2.0);

	// now we have an upper-left corner, and XY and Z vectors such that if
	// we add cornerUL + viewXY, we get the upper right corner, and
	// cornerUL + viewZ, we get the lower left corner.

	int xPixels = graphics.getPixelsX();
	int yPixels = graphics.getPixelsY();
	Vector temp = new Vector();

	Color color;

	// begin the drawing loop
	for (int x=0; x<xPixels; x++)
	{
		for (int y=0; y<yPixels; y++)
		{
			view.set(cornerUL);

			// horizontal component
			temp.set(viewXY);
			temp.scale( (double)(x/xPixels) );
			view.add(temp);

			// verticle component
			temp.set(viewZ);
			temp.scale( (double)(y/yPixels) );
			view.add(temp);

			view.normalize();

			color = reflect(initialPos, view);
			graphics.setColor(x, y, color);
		}
	}			
}
/**
 * Starts the application.
 * @param args an array of command-line arguments
 */
public static void main(java.lang.String[] args) {

	Reflect reflect = new Reflect();
	reflect.setupGraphics();
	reflect.setScene(reflect.makeCubeRoom());
	reflect.drawScene();
}
/**
 * Insert the method's description here.
 * 
 * @return reflect.scene.Surface[]
 */
public Surface[] makeCubeRoom() {
	Surface surfaces[] = new Surface[MAX_SURFACES];
	int cnt = 0;
	
	Vector p0 = new Vector(0, 0, 0);
	Vector p1 = new Vector(1, 0, 0);
	Vector p2 = new Vector(1, 1, 0);
	Vector p3 = new Vector(0, 1, 0);
	Vector p4 = new Vector(0, 0, 1);
	Vector p5 = new Vector(1, 0, 1);
	Vector p6 = new Vector(1, 1, 1);
	Vector p7 = new Vector(0, 1, 1);

	SimplePolygon base = new SimplePolygon(false, Color.darkGray, p0, p1, p2, p3);
	SimplePolygon side1 = new SimplePolygon(false, Color.orange, p0, p1, p5, p4);
	SimplePolygon side2 = new SimplePolygon(false, Color.blue, p1, p2, p6, p5);
	SimplePolygon side3 = new SimplePolygon(false, Color.yellow, p2, p3, p7, p6);
	SimplePolygon side4 = new SimplePolygon(false, Color.red, p3, p0, p4, p7);
	SimplePolygon top = new SimplePolygon(false, Color.white, p4, p5, p6, p7);

	surfaces[cnt++] = base;
	surfaces[cnt++] = side1;
	surfaces[cnt++] = side2;
	surfaces[cnt++] = side3;
	surfaces[cnt++] = side4;
	surfaces[cnt++] = top;
	
	double d = .1;
	double tiny = .00001;
	
	// bottom mirror
	p0.set(d,   d,   tiny);
	p1.set(1-d, d,   tiny);
	p2.set(1-d, 1-d, tiny);
	p3.set(d,   1-d, tiny);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);
	
	// top mirror
	p0.set(d,   d,   1-tiny);
	p1.set(1-d, d,   1-tiny);
	p2.set(1-d, 1-d, 1-tiny);
	p3.set(d,   1-d, 1-tiny);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);

	// side 1 mirror
	p0.set(d,   tiny, d);
	p1.set(1-d, tiny, d);
	p2.set(1-d, tiny, 1-d);
	p3.set(d  , tiny, 1-d);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);
	
	// side 2 mirror
	p0.set(1-tiny, d,   d);
	p1.set(1-tiny, 1-d, d);
	p2.set(1-tiny, 1-d, 1-d);
	p3.set(1-tiny, d,   1-d);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);

	// side 3 mirror
	p0.set(1-d, 1-tiny, d);
	p1.set(d,   1-tiny, d);
	p2.set(d,   1-tiny, 1-d);
	p3.set(1-d, 1-tiny, 1-d);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);

	// side 4 mirror
	p0.set(tiny, 1-d, d);
	p1.set(tiny, d,   d);
	p2.set(tiny, d,   1-d);
	p3.set(tiny, 1-d, 1-d);
	surfaces[cnt++] = new SimplePolygon(true, null, p0, p1, p2, p3);

	return surfaces;
}
/**
 * Insert the method's description here.
 *
 * @returns java.awt.Color
 * @param pos reflect.math.Vector
 * @param view reflect.math.Vector
 */
public Color reflect(Vector initial_pos, Vector initial_view) {

	d = 0.0;
	numReflections = 0;
	color = Color.black;
	pos.set(initial_pos);
	view.set(initial_view);

	closestSurface = -1;
	closestDistance = -1.0;

	while (true)
	{
		if (numReflections == REFLECT_LIMIT)
			break;
			
		// determine which surface we're hitting
		for (int i=0; i<surfaceNum; i++)
		{
			// do test for negative dot product to determine if surface is behind us?
			// if (...)
			//	continue;
			
			scene[i].intersect(pos, view);
			// now surface itself stores point of intersection, distance, etc.

			// now, in order of closest first, find out if poi is actually on surface (n/a for spheres)
			// the first one that matches is our surface
			// hitSurface = scene[x];
		}

		// if the surface is reflective, keep going
		if (hitSurface.isReflective()) {
			d += hitSurface.intersectionLength;

			// set new position
			pos.set(hitSurface.poi);

			// calculate new view vector
			hitSurface.normalAtPoi(viewComponent);
			viewComponent.scale(2.0*view.dotProduct(viewComponent));
			view.subtract(viewComponent);
			
			continue;
		} else {
			// otherwise, we've got our color
			color = hitSurface.colorForPoi();

			// make the color darker for each reflection
			color.getColorComponents(colorComps);
			colorComps[0] *= REFLECT_BRIGHTNESS[numReflections];
			colorComps[1] *= REFLECT_BRIGHTNESS[numReflections];
			colorComps[2] *= REFLECT_BRIGHTNESS[numReflections];
			color = new Color(colorComps[0], colorComps[1], colorComps[2]);
			break;
		}
	}
	
	return color;
}
/**
 * Insert the method's description here.
 * 
 * @param surfaceDefs reflect.scene.Surface[]
 */
public void setScene(Surface[] surfaceDefs) {
	scene = surfaceDefs;
	surfaceNum = scene.length;
}
/**
 * Insert the method's description here.
 * 
 */
public void setupGraphics() {}
}
