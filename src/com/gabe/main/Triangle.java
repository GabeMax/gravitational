package com.gabe.main;

import java.awt.*;

/**
 * Created by Gabriel on 2020-05-21.
 */
public class Triangle {
    
    Point basePoint1;
    Point basePoint2;
    Point outerPoint;
    private Line triangleBase;
    private Line triangleOuter1;
    private Line triangleOuter2;
    boolean isClimbable;
    
    public Triangle(Point basePoint1, Point basePoint2, Point outerPoint, boolean isClimbable) {
        this.basePoint1 = basePoint1;
        this.basePoint2 = basePoint2;
        this.outerPoint = outerPoint;
        this.triangleBase = new Line(basePoint1.x, basePoint1.y, basePoint2.x, basePoint2.y);
        this.triangleOuter1 = new Line(basePoint1.x, basePoint1.y, outerPoint.x, outerPoint.y);
        this.triangleOuter2 = new Line(basePoint2.x, basePoint2.y, outerPoint.x, outerPoint.y);
        this.isClimbable = isClimbable;
    }
    
    public static Triangle getTriangle(int x, int y, Slope slope) {
        if(slope != null) {
            Point p = Tile.getTilePositionAtPoint(new Point(x, y));
            Point basePoint1 = new Point(p.x + slope.xOffset1, p.y + slope.yOffset1);
            Point basePoint2 = new Point(p.x + slope.xOffset2, p.y + slope.yOffset2);
            Point outerPoint = new Point(p.x + slope.xOffset3, p.y + slope.yOffset3);
            return new Triangle(basePoint1, basePoint2, outerPoint, slope.isClimbable);
        }
        return null;
    }

    public boolean pointIsInsideTriangle(Point point) {
        Line[] lines = getAdjacentLines(point, this); 
        Point p1, p2, p3;
        if(lines[0] == null) {
            return false;
        }
        p1 = getPointAtYValue(point, lines[0]);
        p2 = getPointAtYValue(point, lines[1]);
        p3 = getPointAtYValue(point, lines[2]);
        if(p1 == null) {
            return isBetweenX(point, (int)lines[0].getStartX(), (int)lines[0].getEndX());
        }
        if(p2 == null) {
            return isBetweenX(point, (int)lines[1].getStartX(), (int)lines[1].getEndX());
        }
        if(p3 == null) {
            return isBetweenX(point, p1.x, p2.x);
        } else {
            if(p1.x != p2.x) {
                return isBetweenX(point, p1.x, p2.x);
            } else {
                return isBetweenX(point, p1.x, p3.x);
            }
        }
    }
    
    public Line[] getAdjacentLines(GameObject object, Point point) {
        Line[] lines = new Line[3];
        int i = 0;
        switch (object.getGravityDirection()) {
            case DOWN, UP -> {
                if (isBetweenX(point, (int) triangleBase.getStartX(), (int) triangleBase.getEndX())) {
                    lines[i] = triangleBase;
                    i++;
                }
                if (isBetweenX(point, (int) triangleOuter1.getStartX(), (int) triangleOuter1.getEndX())) {
                    lines[i] = triangleOuter1;
                    i++;
                }
                if (isBetweenX(point, (int) triangleOuter2.getStartX(), (int) triangleOuter2.getEndX())) {
                    lines[i] = triangleOuter2;
                }
            }
            case LEFT, RIGHT -> {
                if (isBetweenY(point, (int) triangleBase.getStartY(), (int) triangleBase.getEndY())) {
                    lines[i] = triangleBase;
                    i++;
                }
                if (isBetweenY(point, (int) triangleOuter1.getStartY(), (int) triangleOuter1.getEndY())) {
                    lines[i] = triangleOuter1;
                    i++;
                }
                if (isBetweenY(point, (int) triangleOuter2.getStartY(), (int) triangleOuter2.getEndY())) {
                    lines[i] = triangleOuter2;
                }
            }
        }
        return lines;
    }
    
    private Point getHorizontalOffset(GameObject object, Point point, Line[] lines) {
        int xOffsetDown, xOffsetUp, xOffsetLeft, xOffsetRight;
        if(lines[0] == null) {
            return new Point(0, 0);
        }
        switch(object.getGravityDirection()) {
            case DOWN:
            case UP:
                for(Line line: lines) {
                    if(line != null) {
                        Point xValue = getPointAtYValue(point, line);
                        if(xValue != null) {
                            return new Point(xValue.x - point.x, 0);
                        }
                    }
                }
                break;
            case LEFT:
            case RIGHT:
                for(Line line: lines) {
                    if(line != null) {
                        Point yValue = getPointAtXValue(point, line);
                        if(yValue != null) {
                            return new Point(0, yValue.y - point.y);
                        }
                    }
                }
                break;
        }
        return null;
    }
    
    public Point getHorizontalOffset(GameObject object, Point point) {
        return getHorizontalOffset(object, point, getAdjacentLines(object, point));
    }
    
    private Point getVerticalOffset(GameObject object, Point point, Line[] lines) {
        int yOffsetDown = Integer.MAX_VALUE, yOffsetUp = Integer.MIN_VALUE, xOffsetLeft = Integer.MIN_VALUE, xOffsetRight = Integer.MAX_VALUE;
        if(lines[0] == null) {
            return new Point(0, 0);
        }
        switch (object.getGravityDirection()) {
            case DOWN -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point yValue = getPointAtXValue(point, line);
                        if (yValue == null) {
                            yValue = new Point(point.x, (int) line.getStartY());
                        }
                        if (yValue.y < yOffsetDown) {
                            yOffsetDown = yValue.y;
                        }
                    }
                }
                return new Point(0, point.y - yOffsetDown);
            }
            case UP -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point yValue = getPointAtXValue(point, line);
                        if (yValue == null) {
                            yValue = new Point(point.x, (int) line.getStartY());
                        }
                        if (yValue.y > yOffsetUp) {
                            yOffsetUp = yValue.y;
                        }
                    }
                }
                return new Point(0, point.y - yOffsetUp);
            }
            case LEFT -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point xValue = getPointAtYValue(point, line);
                        if (xValue == null) {
                            xValue = new Point((int) line.getStartX(), point.y);
                        }
                        if (xValue.x > xOffsetLeft) {
                            xOffsetLeft = xValue.x;
                        }
                    }
                }
                return new Point(point.x - xOffsetLeft, 0);
            }
            case RIGHT -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point xValue = getPointAtYValue(point, line);
                        if (xValue == null) {
                            xValue = new Point((int) line.getStartX(), point.y);
                        }
                        if (xValue.x < xOffsetRight) {
                            xOffsetRight = xValue.x;
                        }
                    }
                }
                return new Point(point.x - xOffsetRight, 0);
            }
        }
        return null;
    }

    private Point getVerticalOffsetToBottom(GameObject object, Point point, Line[] lines) {
        int yOffsetDown = Integer.MIN_VALUE, yOffsetUp = Integer.MAX_VALUE, xOffsetLeft = Integer.MAX_VALUE, xOffsetRight = Integer.MIN_VALUE;
        if(lines[0] == null) {
            return new Point(0, 0);
        }
        switch (object.getGravityDirection()) {
            case DOWN -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point yValue = getPointAtXValue(point, line);
                        if (yValue == null) {
                            yValue = new Point(point.x, (int) line.getStartY());
                        }
                        if (yValue.y > yOffsetDown) {
                            yOffsetDown = yValue.y;
                        }
                    }
                }
                return new Point(0, point.y - yOffsetDown);
            }
            case UP -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point yValue = getPointAtXValue(point, line);
                        if (yValue == null) {
                            yValue = new Point(point.x, (int) line.getStartY());
                        }
                        if (yValue.y < yOffsetUp) {
                            yOffsetUp = yValue.y;
                        }
                    }
                }
                return new Point(0, point.y - yOffsetUp);
            }
            case LEFT -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point xValue = getPointAtYValue(point, line);
                        if (xValue == null) {
                            xValue = new Point((int) line.getStartX(), point.y);
                        }
                        if (xValue.x < xOffsetLeft) {
                            xOffsetLeft = xValue.x;
                        }
                    }
                }
                return new Point(point.x - xOffsetLeft, 0);
            }
            case RIGHT -> {
                for (Line line : lines) {
                    if (line != null) {
                        Point xValue = getPointAtYValue(point, line);
                        if (xValue == null) {
                            xValue = new Point((int) line.getStartX(), point.y);
                        }
                        if (xValue.x > xOffsetRight) {
                            xOffsetRight = xValue.x;
                        }
                    }
                }
                return new Point(point.x - xOffsetRight, 0);
            }
        }
        return null;
    }

    public Point getVerticalOffset(GameObject object, Point point, boolean snapToTop) {
        if(snapToTop) {
            return getVerticalOffset(object, point, getAdjacentLines(object, point));
        }
        return getVerticalOffsetToBottom(object, point, getAdjacentLines(object, point));
    }
    
    public static int getYOffset(Point point, Triangle t) {
        Line[] lines = new Line[3];
        int i = 0, yOffset = Integer.MAX_VALUE;
        if(isBetweenX(point, (int)t.triangleBase.getStartX(), (int)t.triangleBase.getEndX())) {
            lines[i] = t.triangleBase;
            i++;
        }
        if(isBetweenX(point, (int)t.triangleOuter1.getStartX(), (int)t.triangleOuter1.getEndX())) {
            lines[i] = t.triangleOuter1;
            i++;
        }
        if(isBetweenX(point, (int)t.triangleOuter2.getStartX(), (int)t.triangleOuter2.getEndX())) {
            lines[i] = t.triangleOuter2;
            i++;
        }
        System.out.println("-----------------------------------------------------------------------------------");
        for(int j = 0; j < i; j++) {
            if(lines[j] != null) {
                Point yValue = getPointAtXValue(point, lines[j]);
                if(yValue != null) {
                    System.out.println(yValue + " " + j);
                }
                if(yValue == null) {
                    yValue = new Point(point.x, (int)lines[j].getStartY());
                    System.out.println("New not null point: " + yValue + " " + j);
                }
                if(yValue.y < yOffset) {
                    yOffset = yValue.y;
                }
            }
        }
        System.out.println("Player point: " + point);
        System.out.println("Final y offset: " + yOffset);
        yOffset = point.y - yOffset;
        System.out.println("Being executed: " + yOffset);
        return yOffset;
    }
    
    public static int getXOffset(Point point, Triangle t) {
        Line[] lines = new Line[3];
        int i = 0, xOffset = 0;
        Point p;
        if(isBetweenX(point, (int)t.triangleBase.getStartX(), (int)t.triangleBase.getEndX())) {
            lines[i] = t.triangleBase;
            i++;
        }
        if(isBetweenX(point, (int)t.triangleOuter1.getStartX(), (int)t.triangleOuter1.getEndX())) {
            lines[i] = t.triangleOuter1;
            i++;
        }
        if(isBetweenX(point, (int)t.triangleOuter2.getStartX(), (int)t.triangleOuter2.getEndX())) {
            lines[i] = t.triangleOuter2;
            i++;
        }
        for(int j = 0; j < i; j++) {
            if (lines[j] != null) {
                p = getPointAtYValue(point, lines[j]);
                if (p != null) {
                    return p.x - point.x;
                }
            }
        }
        return xOffset;
    }
    
    public void getSideToSideOffset(GameObject object, Point point) {
        Line[] lines  = new Line[3];
        int i = 0, xOffset = 0;
        
    }
    
    public static boolean isBetweenX(Point p, int p1, int p2) {
        if(p1 > p2) {
            int swap = p1;
            p1 = p2;
            p2 = swap;
        }
        return p.x >= p1 && p.x <= p2;
    }

    public static boolean isBetweenY(Point p, int p1, int p2) {
        if(p1 > p2) {
            int swap = p1;
            p1 = p2;
            p2 = swap;
        }
        return p.y >= p1 && p.y <= p2;
    }

    public static Line[] getAdjacentLines(Point point, Triangle slope) {
        Line[] lines = new Line[3];
        int i = 0;
        if (isBetweenY(point, (int) slope.triangleBase.getStartY(), (int) slope.triangleBase.getEndY())) {
            lines[i++] = slope.triangleBase;
        }
        if (isBetweenY(point, (int) slope.triangleOuter1.getStartY(), (int) slope.triangleOuter1.getEndY())) {
            lines[i++] = slope.triangleOuter1;
        }
        if (isBetweenY(point, (int) slope.triangleOuter2.getStartY(), (int) slope.triangleOuter2.getEndY())) {
            lines[i] = slope.triangleOuter2;
        }
        return lines;
    }
    
    public static Point getPointAtYValue(Point point, Line line) {
        if(line == null) {
            return null;
        }
        int y = point.y, b = (int)line.getStartY();
        Double m = getSlopeValue(line);
        if(m == null) {
            return new Point((int)line.getStartX(), y);
        } else if(m == 0) {
            return null;
        }
        return new Point((int)((y-b)/m) + (int)line.getStartX(), y);
    }

    public static Point getPointAtXValue(Point point, Line line) {
        if(line == null) {
            return null;
        }
        int x = point.x;
        Double m = getSlopeValue(line);
        if(m == null) {
            return new Point(x, (int)(line.getStartY()));
        } else if(m == 0) {
            return null;
        }
        double b = getBValue(line, m);
        return new Point(x, (int)Math.round(m*x+b));
    }
    
    public static double getBValue(Line line, Double m) {
        return ((-m*line.getEndX()) + line.getEndY());
    }
    
    public static Double getSlopeValue(Line line) {
        if(line.getEndX() - line.getStartX() == 0) {
            return null;
        }
        return (line.getEndY() - line.getStartY()) / (line.getEndX() - line.getStartX());
    }
}
