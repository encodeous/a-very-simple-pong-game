package ca.encodeous.pong.physics; /**
 * MIT License
 *
 * Copyright (c) 2023 Adam Chen
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * 2-d vector representation
 * Includes helper methods that are pretty nice :)
 */
public class Vec2d implements Comparable<Vec2d> {
    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Converts a polar vector into a cartesian one
     *
     * @param magnitude the magnitude
     * @param direction the direction in degrees
     * @return a cartesian vector
     */
    public static Vec2d ofPolar(double magnitude, double direction) {
        double radDir = direction / 180 * Math.PI;
        return new Vec2d(magnitude * Math.sin(radDir), -magnitude * Math.cos(radDir));
    }

    /**
     * Adds another vector to the current vector.
     *
     * @param other
     * @return
     */
    public Vec2d add(Vec2d other) {
        return new Vec2d(x + other.x, y + other.y);
    }

    /**
     * Subtracts another vector from the current vector
     *
     * @param other
     * @return
     */
    public Vec2d subtract(Vec2d other) {
        return new Vec2d(x - other.x, y - other.y);
    }

    /**
     * Multiplies the vector by a scalar
     *
     * @param scalar
     * @return
     */
    public Vec2d multiply(double scalar) {
        return new Vec2d(x * scalar, y * scalar);
    }

    /**
     * Multiplies both the x and y components by individual scalars.
     *
     * @param scalar
     * @return
     */
    public Vec2d multiply(Vec2d scalar) {
        return new Vec2d(x * scalar.x, y * scalar.y);
    }

    /**
     * Calculates the distance to another point.
     *
     * @param point
     * @return
     */
    public double distanceTo(Vec2d point) {
        double dx = x - point.x, dy = y - point.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the magnitude of the vector
     *
     * @return
     */
    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * Gets a normalized vector of the current one
     *
     * @return
     */
    public Vec2d normalize() {
        double mag = magnitude();
        return new Vec2d(x / mag, y / mag);
    }

    /**
     * Gets the bearing of another point in relation to the current point
     *
     * @param other
     * @return
     */
    public double getAngleTo(Vec2d other) {
        Vec2d diff = other.subtract(this);
        return diff.getDirection();
    }

    /**
     * Gets the angle of the current vector. North is 0 degrees
     *
     * @return
     */
    private double getUnitDirection() {
        double ra = 180 * Math.acos(x) / Math.PI - 90;
        if (x < 0) {
            if (y < 0) {
                return -ra;
            } else {
                return -180 + ra;
            }
        } else {
            if (y < 0) {
                return -ra;
            } else {
                return 180 + ra;
            }
        }
    }

    /**
     * Gets the compass bearing of the current vector
     *
     * @return
     */
    public double getDirection() {
        return normalize().getUnitDirection();
    }

    public double x, y;

    /**
     * Gets the smaller angle difference between two headings
     *
     * @param a1 heading 1
     * @param a2 heading 2
     * @return the difference added to a1 to achieve a2
     */
    public static double getAngleDifference(double a1, double a2) {
        if (a1 < 0 != a2 < 0) {
            double c1 = Math.abs(a1) + Math.abs(a2);
            double c2 = 360 - Math.abs(a1) - Math.abs(a2);
            if (c1 < c2) {
                if (a1 < a2) {
                    return c1;
                } else {
                    return -c1;
                }
            } else {
                if (a1 > a2) {
                    return c2;
                } else {
                    return -c2;
                }
            }
        } else {
            if (Math.abs(a1 - a2) < Math.abs(a2 - a1)) {
                return a1 - a2;
            } else {
                return a2 - a1;
            }
        }
    }

    public int compareTo(Vec2d o) {
        if (x == o.x) {
            return Double.compare(y, o.y);
        }
        return Double.compare(x, o.x);
    }

    @Override
    public String toString() {
        return "ca.encodeous.pong.physics.Vec2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Gets the distance between a line and a point
     *
     * @param ep1 p1 of the line
     * @param ep2 p2 of the line
     * @param pt  the point
     * @return the distance
     */

    public static double getLineDist(Vec2d ep1, Vec2d ep2, Vec2d pt) {
        // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
        double dx1 = ep2.x - ep1.x;
        double dy1 = ep2.y - ep1.y;
        return Math.abs(dx1 * (ep1.y - pt.y) - (ep1.x - pt.x) * (dy1)) / Math.sqrt(dx1 * dx1 + dy1 * dy1);
    }
}
