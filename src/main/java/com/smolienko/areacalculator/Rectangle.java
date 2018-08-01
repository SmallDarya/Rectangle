package com.smolienko.areacalculator;

import java.util.*;

/**
 * The class describes rectangle and contains four lines. If all four vertices of
 * rectangle are equal this is a point. Rectangle with two pairs of equal
 * vertices is a line.
 *
 * @author d_smolienko
 */
public class Rectangle implements Figure {

    // This flag is true when all vertices of rectangle are the same point
    private boolean isPoint;
    //This flag is true when width or height of rectangle is 0;
    private boolean isLine;

    //Rectangle contains four lines
    private final ArrayList<Line> lines = new ArrayList(4);

    /**
     * For describing rectangle you have to enter beginning and ending points of
     * the left boundary of rectangle and the rectangle's width.
     *
     * @param firstPoint begin {@code Point} of the left boundary of rectangle
     * @param secondPoint end {@code Point} of the left boundary of rectangle
     * @param width width of the rectangle
     */
    public Rectangle(Point firstPoint, Point secondPoint, double width) {
        double eVectorX;
        double eVectorY;
        double height;
        double x3;
        double y3;
        double x4;
        double y4;

        if (width == 0.0) {
            x3 = firstPoint.x;
            y3 = firstPoint.y;
            x4 = secondPoint.x;
            y4 = secondPoint.y;
            isLine = true;
            if (x3 == x4) {
                isPoint = true;
            }
        } else {
            if (firstPoint.equals(secondPoint)) {
                isLine = true;
                eVectorX = 1;
                eVectorY = 1;
                height = 0;
            } else {
                height = Math.sqrt(Math.pow(firstPoint.x - secondPoint.x, 2) + Math.pow(firstPoint.y - secondPoint.y, 2));
                eVectorX = (firstPoint.x - secondPoint.x) / height;
                eVectorY = (firstPoint.y - secondPoint.y) / height;
            }

            x3 = secondPoint.x + (-eVectorY * width);
            y3 = secondPoint.y + (eVectorX * width);
            x4 = firstPoint.x + (-eVectorY * width);
            y4 = firstPoint.y + (eVectorX * width);
        }

        lines.add(new Line(firstPoint, secondPoint));
        lines.add(new Line(secondPoint, new Point(x3, y3)));
        lines.add(new Line(new Point(x3, y3), new Point(x4, y4)));
        lines.add(new Line(new Point(x4, y4), firstPoint));
    }

    /**
     * Returns four {@code Line} of rectangle
     *
     * @return array of{@code Line}
     */
    public Line[] getLines() {
        Line[] linesArray = new Line[4];
        return lines.toArray(linesArray);
    }

    /**
     * Returns four {@code Point} of rectangle
     *
     * @return array of{@code Point}
     */
    public Point[] getPoints() {
        Point[] points = new Point[4];
        for (int i = 0; i < this.lines.size(); i++) {
            points[i] = new Point(lines.get(i).getBegin().x, (lines.get(i).getBegin().y));
        }
        return points;
    }

    /**
     * This method calculates area of rectangle by multiplication width by
     * height
     *
     * @return area of rectangle.
     */
    @Override
    public double getArea() {
        return lines.get(0).getLength() * lines.get(1).getLength();
    }

    /**
     * This function calculates intersection area of two rectangles. Algorithm
     * contains two parts: 1.First of all, we have to describe figure of
     * intersection. It can contain from three to eight vertices and it will be
     * a convex polygon. For this target algorithm use the Weiler-Atherton
     * clipping algorithm, which can find all points of intersection area, but
     * for the second part this points have to keep order. It means if we will
     * draw line from point to point we have to get convex polygon without inner
     * diagonals. For more details see method {@link #WeilerAthertonAlgorithm}
     * <p>
     * 2. Second part is Gauss's area formula, which is a mathematical algorithm
     * to determine the area of a simple polygon whose vertices are described by
     * their Cartesian coordinates in the plane. For more details see method
     * {@link #functionOfGauss}
     *
     * @param anotherrectangle another rectangle.
     * @return value of intersection area.
     */
    public double getCrossArea(Rectangle anotherrectangle) {
        if (this.isLine || this.isPoint || anotherrectangle.isLine || anotherrectangle.isPoint) {
            return 0.0;
        }
        List<MarkedPoint> points = WeilerAthertonAlgorithm(anotherrectangle);
        if (points.isEmpty()) {
            return 0.0;
        }
        return functionOfGauss(points);
    }

    /**
     * It is realization mathematical algorithm to determine the area of a
     * simple polygon.The area formula is derived by taking each edge AB, 
     * and calculating the (signed) area of triangle ABO with a vertex at 
     * the origin O, by taking the cross-product (which gives the area of 
     * a parallelogram) and dividing by 2. As one wraps around the polygon, 
     * these triangles with positive and negative area will overlap, and 
     * the areas between the origin and the polygon will be cancelled out 
     * and sum to 0, while only the area inside the reference triangle remains. 
     *
     * @param points sorted {@code List} of  {@code Point} which describes a polygon.
     * @return value of are
     */
    private double functionOfGauss(List<MarkedPoint> points) {
        if (points.size() < 3) {
            return 0.0;
        }
        Iterator<MarkedPoint> iterator = points.iterator();
        double firstSumOfCoordinates = 0;
        double secondSumOfCoordinates = 0;
        Point current = iterator.next();
        while (iterator.hasNext()) {
            Point next = iterator.next();
            firstSumOfCoordinates += current.x * next.y;
            secondSumOfCoordinates += current.y * next.x;
            current = next;
        }
        firstSumOfCoordinates += current.x * points.get(0).y;
        secondSumOfCoordinates += current.y * points.get(0).x;
        return Math.abs((firstSumOfCoordinates - secondSumOfCoordinates)) / 2;
    }

    /**
     * Returns <tt>true</tt> if point is contained in rectangle; if it is out of
     * bounds, or if point is on the line the function returns
     * <tt>false</tt> .
     *
     * @param point point for check.
     * @return the <tt>true</tt> if rectangle contains the point and
     * <tt>false</tt> if does not contains
     */
    public boolean isPointInRectangle(Point point) {
        Line lineLeft = lines.get(0);
        Line lineRight = lines.get(2);
        Line lineUp = lines.get(1);
        Line lineDown = lines.get(3);

        return isPointBetweenTwoLines(lineLeft, lineRight, point) && isPointBetweenTwoLines(lineUp, lineDown, point);
    }

    /**
     * This function returns {@code IntersectionPointsList} which contains all
     * points of all points of intersection area. The function implements
     * Weiler-Atherton clipping algorithm. The main logic of algorithm contained
     * in {@link #getIntersectionPoligonPoints}, which determine inner and
     * intersection points. Classic algorithm use two lists of vertices(first
     * with respect to the second and second with respect to the first), but in
     * this implementation we make second list only if we have not inner point
     * in list on the first try.
     *
     * @param anotherRectangle another rectangle.
     * @return {@code List} of {@code MarkedPoint}
     */
    private List<MarkedPoint> WeilerAthertonAlgorithm(Rectangle anotherRectangle) {
        IntersectionPointsList intersectionPoints = getIntersectionPoligonPoints(anotherRectangle);
        if (!intersectionPoints.isContainInnerPoints()) {
            intersectionPoints = anotherRectangle.getIntersectionPoligonPoints(this);
        }
        List<MarkedPoint> points = intersectionPoints.getIntersectionPoints();
        return points;
    }

    /**
     * This function returns {@code IntersectionPointsList} which contains all
     * points of intersection area. The algorithm traverses the vertices of the
     * rectangle and mark vertex like INNER if the vertex is in other rectangle.
     * Calculate INTERSECTION points of rectangles boundaries. All the points
     * are placed in the list. This algorithm keep order of points to describe
     * convex polygon. So, result contains ordered inner and intersection
     * points.
     *
     * @param anotherRectangle another rectangle.
     * @return {@code IntersectionPointsList} object containing {@code List} of
     * {@code MarkedPoint} and flag which is true if the list contains even one
     * inner point.
     */
    private IntersectionPointsList getIntersectionPoligonPoints(Rectangle anotherRectangle) {
        List<MarkedPoint> markedPoints = new LinkedList<>();
        IntersectionPointsList intersectionPointsList = new IntersectionPointsList(markedPoints);
        MarkedPoint pointBegin = new MarkedPoint(lines.get(0).getBegin().x, lines.get(0).getBegin().y);

        if (anotherRectangle.isPointInRectangle(pointBegin)) {
            pointBegin.markLikeInner();
            markedPoints.add(pointBegin);
            intersectionPointsList.setContainInnerPoints(true);
        }

        for (Line current : this.lines) {
            List<MarkedPoint> intersectionPoints = new ArrayList<>();
            for (Line anotherRectangleCurrentLine : anotherRectangle.lines) {
                MarkedPoint point = current.getCrossPoint(anotherRectangleCurrentLine);
                if (point != null) {
                    intersectionPoints.add(point);
                    MarkedPoint anotherRectangleBeginPoint = new MarkedPoint(anotherRectangleCurrentLine.getBegin().x, anotherRectangleCurrentLine.getBegin().y);
                    if (this.isPointInRectangle(anotherRectangleBeginPoint)) {
                        anotherRectangleBeginPoint.markLikeInner();
                        markedPoints.add(anotherRectangleBeginPoint);
                        intersectionPointsList.setContainInnerPoints(true);
                    }
                }
            }
            markedPoints.addAll(sortPointsOnLine(current, intersectionPoints));
            MarkedPoint pointEnd = new MarkedPoint(current.getEnd().x, current.getEnd().y);
            if (anotherRectangle.isPointInRectangle(pointEnd)) {
                pointEnd.markLikeInner();
                markedPoints.add(pointEnd);
                intersectionPointsList.setContainInnerPoints(true);
            }
        }
        return intersectionPointsList;
    }

    /**
     * This function determine if the point is located between two lines.
     *
     * @param first first {@code Line};
     * @param second second {@code Line};
     * @param point a {@code Point} for check
     * @return the <tt>true</tt> if the point is located between lines and
     * <tt>false</tt> if it is not.
     */
    private boolean isPointBetweenTwoLines(Line first, Line second, Point point) {
        Double left = getValueY(first, point.x);
        Double right = getValueY(second, point.x);

        if (left != null && right != null) {
            if (left > right) {
                double buff = left;
                left = right;
                right = buff;
            }
            if (!(left < point.y && right > point.y)) {
                return false;
            }
        } //if line given like y=4 or x=7
        else {
            if (left == null) {
                left = first.getBegin().x;
            }
            if (right == null) {
                right = second.getBegin().x;
            }
            if (left > right) {
                double buff = left;
                left = right;
                right = buff;
            }

            if (!(left < point.x && right > point.x)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This function determines value of y of line at the point x.
     *
     * @param line {@code Line} for calculating;
     * @param x - value of x;
     * @return value of y in point x of function described by line
     */
    private Double getValueY(Line line, double x) {
        double a = line.getBegin().y - line.getEnd().y;
        double b = line.getEnd().x - line.getBegin().x;
        double c = line.getBegin().x * line.getEnd().y - line.getEnd().x * line.getBegin().y;
        if (b == 0) {
            //this means that line given like  x=7
            return null;
        }
        return (-c - a * x) / b;
    }

    /**
     * The function determines the order of the location of points on the line.
     * The function calculates the distance from the expected start of the
     * segment to each point and sorts the distances.
     *
     * @param line {@code Line} with points
     * @param points {@code List} of {@code Point} for sorting.
     * @return value of y in point x of function described by line
     */
    private List<MarkedPoint> sortPointsOnLine(Line line, List<MarkedPoint> points) {
        Point begin = line.getBegin();
        List<MarkedPoint> result = new ArrayList<>();
        Map<Double, MarkedPoint> pointsDistances = new HashMap<>();
        for (MarkedPoint point : points) {
            double distance = Math.sqrt(Math.pow(point.x - begin.x, 2) + Math.pow(point.y - begin.y, 2));
            pointsDistances.put(distance, point);
        }
        List<Double> distances = new ArrayList<>(pointsDistances.keySet());
        Collections.sort(distances);
        for (Double distance : distances) {
            result.add(pointsDistances.get(distance));
        }
        return result;
    }

    /**
     * This class is needed for store of interim calculations Weiler-Atherton
     * clipping algorithm
     */
    private class IntersectionPointsList {

        private List<MarkedPoint> intersectionPoints;
        private boolean containInnerPoints = false;

        public List<MarkedPoint> getIntersectionPoints() {
            return intersectionPoints;
        }

        public IntersectionPointsList(List<MarkedPoint> intersectionPoints) {
            this.intersectionPoints = intersectionPoints;
        }

        public boolean isContainInnerPoints() {
            return containInnerPoints;
        }

        public void setContainInnerPoints(boolean hasInnerPoints) {
            this.containInnerPoints = hasInnerPoints;
        }
    }
}
