package name.vbraun.view.write;

import java.util.LinkedList;
import java.util.ListIterator;

import junit.framework.Assert;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;



/**
 * Base class for graphics objects that have control points 
 * (everything except pen strokes, really).
 * @author vbraun
 *
 */
public abstract class GraphicsControlpoint extends Graphics {

	public class Controlpoint {
		protected float x,y;   // page coordinates
		public Controlpoint(float x, float y) {
			this.x = transform.inverseX(x);
			this.y = transform.inverseY(y);
		}
		public void move(float x, float y) {
			this.x = transform.inverseX(x);
			this.y = transform.inverseY(y);
			GraphicsControlpoint.this.controlpointMoved(this);
		}
		public GraphicsControlpoint getGraphics() {
			return GraphicsControlpoint.this;
		}
		public String toString() {
			return "("+x+","+y+")";
		}
		public float screenX() { return transform.applyX(x); };
		public float screenY() { return transform.applyY(y); };
	}
	
	
	/**
	 * Derived classes must add their control points to this list
	 */
	protected LinkedList<Controlpoint> controlpoints = new LinkedList<Controlpoint>();
	
	/**
	 * The control point that is active after object creation. 
	 * @return A Controlpoint or null (indicating that there is none active)
	 */
	protected Controlpoint initialControlpoint() {
		return null;
	}
	
	void controlpointMoved(Controlpoint point) {
		recompute_bounding_box = true;
	}
	
	protected GraphicsControlpoint(Tool mTool) {
		super(mTool);
		controlpointPaint = new Paint();
		controlpointPaint.setARGB(0x20, 0xff, 0x0, 0x0);
		controlpointPaint.setStyle(Style.FILL_AND_STROKE);
	}
	
	/**
	 * By default, the bounding box is the box containing the control points
	 * inset by this much (which you can override in a derived class).
	 * @return
	 */
	protected float boundingBoxInset() { 
		return -1;
	}
	
	protected Paint controlpointPaint;
	
	protected void drawControlpoints(Canvas canvas) {
		for (Controlpoint p : controlpoints) {
			float x = p.screenX();
			float y = p.screenY();
			canvas.drawCircle(x, y, 10, controlpointPaint);
		}
	}
	
	@Override
	protected void computeBoundingBox() {
		ListIterator<Controlpoint> iter = controlpoints.listIterator();
		Assert.assertTrue(iter.hasNext()); // must have at least one control point
		Controlpoint p = iter.next();
		float xmin, xmax, ymin, ymax;
		xmin = xmax = transform.applyX(p.x);
		ymin = ymax = transform.applyY(p.y);
		while (iter.hasNext()) {
			p = iter.next();
			float x = p.screenX();
			xmin = Math.min(xmin, x);
			xmax = Math.max(xmax, x);
			float y = p.screenY();
			ymin = Math.min(ymin, y);
			ymax = Math.max(ymax, y);
		}
		bBoxFloat.set(xmin, ymin, xmax, ymax);
		float extra = boundingBoxInset();
		bBoxFloat.inset(extra, extra);		
		bBoxFloat.roundOut(bBoxInt);
		recompute_bounding_box = false;
	}

	@Override
	public float distance(float x_screen, float y_screen) {
		// TODO Auto-generated method stub
		return 0;
	}


}
