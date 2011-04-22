package vooga.collisions.intersections;

import java.awt.geom.Line2D;
import vooga.collisions.shapes.regularShapes.*;

public class PolygonCircleFinder extends IntersectionFinder
{

	@Override
	boolean canApply(Class<? extends IShape> c1, Class<? extends IShape> c2) 
	{
		return (c1.isInstance(Polygon.class) && c2.isInstance(Circle.class));
	}

    @Override
    public Intersection getIntersection (IShape s1, IShape s2)
    {
        Intersection in = new Intersection();
        for(Line2D l: ((Polygon) s1).getSides()){
            if(((Circle)s2).intersects(l))
                in.addIntersectingPoints(((Circle)s2).findIntersections(l));
        }
        return in;
    }

    @Override
    public boolean areIntersecting (IShape s1, IShape s2)
    {
        for(Line2D l: ((Polygon) s1).getSides()){
            if(((Circle)s2).intersects(l))
                return true;
        }
        return false;
    }

	@Override
	public int compareTo(Object o) 
	{
		System.out.println("compare");
		return this.hashCode() - o.hashCode();
	}

}
