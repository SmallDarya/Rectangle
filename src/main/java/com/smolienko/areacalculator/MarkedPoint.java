package com.smolienko.areacalculator;


/**
 * The class contains x and y value of point on the plane and can
 * have one of four state: inner point, outer point, intersection point
 * and unknown. This state describe location of the point in space relative
 * to other figures.
 *
 * @author d_smolienko
 *
 */
public class MarkedPoint extends Point {

    public enum Type {INTERSECTION, INNER, OUTER, UNKNOWN}

    public Type type = Type.UNKNOWN;

    public MarkedPoint(double x, double y) {
        super(x, y);
    }

    public Type getType() {
        return type;
    }

    public void markLikeIntersection() {
        this.type = Type.INTERSECTION;
    }

    public void markLikeInner() {
        this.type = Type.INNER;
    }

    public void markLikeOuter() {
        this.type = Type.OUTER;
    }

    public boolean isIntersectionPoint() {
        return type==Type.INTERSECTION;
    }

    public boolean isInnerPoint() {
        return type==Type.INNER;
    }

    public boolean isOuterPoint() {
        return type==Type.OUTER;
    }


}
