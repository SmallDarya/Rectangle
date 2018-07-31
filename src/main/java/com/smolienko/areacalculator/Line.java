package com.smolienko.areacalculator;

/**
 * The class contains two points of begin and end of the
 * line segment. Point - is a line, just very small.
 *
 *  @author d_smolienko
 */
public class Line implements Figure {
    private Point begin;
    private Point end;
    boolean isPoint;

    public Line(Point begin, Point end) {
        this.begin = begin;
        this.end = end;

        if (begin.equals(end))
            this.isPoint = true;
    }

    public Point getBegin() {
        return begin;
    }

    public void setBegin(Point begin) {
        this.begin = begin;
        if (begin.equals(end))
            this.isPoint = true;
    }

    public Point getEnd() {
        return end;
    }

    public double getArea() {
        return 0;
    }

    public void setEnd(Point end) {
        this.end = end;
        if (begin.equals(end))
            this.isPoint = true;
    }

    /**
     * Calculate length of line segment by Pythagoras' theorem
     *
     * @return length of line segment
     */
    public double getLength() {
        return Math.sqrt(Math.pow(begin.x - end.x, 2) + Math.pow(begin.y - end.y, 2));
    }

    /**
     * Determine if point is on the line segment.
     *
     * @param point point for check.
     * @return the <tt>true</tt> if point is on line segment and <tt>false</tt>  if it is not
     */
    public boolean isPointOnLine(Point point) {
        return (point.x - this.begin.x) / (this.end.x - this.begin.x) == (point.y - this.begin.y) / (this.end.y - this.begin.y);
    }

    /**
     * Determine intersection point of two lines.
     *
     * @param anotherLine {@code Line} for check.
     * @return {@code MarkedPoint) if two lines have intersection point
     * and null if it doesn't
     */
    public MarkedPoint getCrossPoint(Line anotherLine) {
        double x1 = this.begin.x;
        double y1 = this.begin.y;
        double x2 = this.end.x;
        double y2 = this.end.y;
        double x3 = anotherLine.begin.x;
        double y3 = anotherLine.begin.y;
        double x4 = anotherLine.end.x;
        double y4 = anotherLine.end.y;

        double denuminator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denuminator == 0)
            return null;

        double xi = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));
        double yi = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / ((x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4));

        if (!(inRange(x1, x2, xi) && (inRange(x3, x4, xi)) && inRange(y1, y2, yi) && inRange(y3, y4, yi)))
            return null;

        MarkedPoint intersectionPoint = new MarkedPoint(xi, yi);
        intersectionPoint.markLikeIntersection();
        return intersectionPoint;
    }

    private boolean inRange(double a, double b, double c) {
        if (a > b) {
            double buff = a;
            a = b;
            b = buff;
        }
        return a <= c && c <= b;
    }

}
