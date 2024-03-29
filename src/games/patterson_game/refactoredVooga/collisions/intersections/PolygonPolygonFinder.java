package games.patterson_game.refactoredVooga.collisions.intersections;

import games.patterson_game.refactoredVooga.collisions.shapes.regularShapes.*;
import games.patterson_game.refactoredVooga.util.math.LineMath;
import java.awt.geom.Line2D;

public class PolygonPolygonFinder extends IntersectionFinder<Polygon, Polygon>
{

	public PolygonPolygonFinder()
	{
		c1 = Polygon.class;
		c2 = Polygon.class;
	}
	

    @Override
    public Intersection getIntersection (Polygon s1, Polygon s2)
    {
        Intersection in = new Intersection();
        for(Line2D L1: s1.getSides()){
            for(Line2D L2: s2.getSides()){
                if (L1.intersectsLine(L2))
                    in.addIntersectingPoints(LineMath.findIntersection(L1, L2));
            }
        }
        return in;
    }

    @Override
    public boolean areIntersecting (Polygon s1, Polygon s2)
    {
        for(Line2D L1: s1.getSides()){
            for(Line2D L2: s2.getSides()){
                if (L1.intersectsLine(L2)){
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public int compareTo(Object arg0) {
		return this.hashCode() - arg0.hashCode();
	}


}
