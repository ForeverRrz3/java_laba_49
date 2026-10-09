public class Line {
    private static final double near = 1e-9;

    private Point p1;
    private Point p2;

    public Line(Point p1, Point p2) {
        checkPoints(p1, p2);
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(Point p1) {
        checkPoints(p1, p2);
        this.p1 = p1;
    }

    public void setP2(Point p2) {
        checkPoints(p1, p2);
        this.p2 = p2;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    private static void checkPoints(Point p1, Point p2) {
        if (Math.abs(p1.getX() - p2.getX()) < near && Math.abs(p1.getY() - p2.getY()) < near) {
            throw new IllegalArgumentException("точки " + p1 + " и " + p2 + " совпадают");
        }
    }

    // вектор (stepX, stepY) от p1 к p2
    private double stepX() {
        return p2.getX() - p1.getX();
    }

    private double stepY() {
        return p2.getY() - p1.getY();
    }

    public boolean isVertical() {
        return Math.abs(stepX()) < near;
    }

    public double getK() {
        if (stepX() < near){
            throw new IllegalArgumentException("Прямая вертикальная, нет коэффициента k")
        }
        return stepY() / stepX();
    }

    public double getB() {
        return p1.getY() - getK() * p1.getX();
    }

    public String getEquation() {
        if (isVertical()) {
            return String.format("x = %.2f", p1.getX());
        }
        if (Math.abs(getK()) < near) {
            return String.format("y = %.2f", getB());
        }
        if (getB() < 0) {
            return String.format("y = %.2fx - %.2f", getK(), -getB());
        }
        return String.format("y = %.2fx + %.2f", getK(), getB());
    }

    public boolean contains(Point p) {
        // У вертикальной прямой нет k, у всех её точек одинаковый x
        if (isVertical()) {
            return Math.abs(p.getX() - p1.getX()) < near;
        }
        return Math.abs(p.getY() - (getK() * p.getX() + getB())) < near;
    }

    public boolean isParallel(Line other) {
        return Math.abs(stepX() * other.stepY() - stepY() * other.stepX()) < near;
    }

    public boolean isPerpendicular(Line other) {
        return Math.abs(stepX() * other.stepX() + stepY() * other.stepY()) < near;
    }

    public Line parallelThrough(Point p) {
        if (contains(p)) {
            throw new IllegalArgumentException("точка " + p + " лежит на прямой");
        }
        return new Line(p, new Point(p.getX() + stepX(), p.getY() + stepY()));
    }

    public Line perpendicularThrough(Point p) {
        // Скалярное произведение stepX * (-stepY) + stepY * stepX = 0
        double perpX = -stepY();
        double perpY = stepX();

        double x2 = p.getX() + perpX;
        double y2 = p.getY() + perpY;
        Point p2 = new Point(x2, y2);

        return new Line(p, p2);
    }

    public Point intersection(Line other) {
        if (isParallel(other)) {
            return null;
        }
        if (isVertical()) {
            double x = p1.getX();
            return new Point(x, other.getK() * x + other.getB());
        }
        if (other.isVertical()) {
            double x = other.p1.getX();
            return new Point(x, getK() * x + getB());
        }
        double x = (other.getB() - getB()) / (getK() - other.getK());
        return new Point(x, getK() * x + getB());
    }

    @Override
    public String toString() {
        return "через " + p1 + " и " + p2 + ": " + getEquation();
    }
}
