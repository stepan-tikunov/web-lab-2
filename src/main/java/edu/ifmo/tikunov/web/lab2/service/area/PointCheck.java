package edu.ifmo.tikunov.web.lab2.service.area;

public class PointCheck {
	public final double x;
	public final double y;
	public final double r;
	private boolean hit;
	private String date;
	private long executionTime;

	private static final double R_PX_WIDTH = 300 / 2.533;
	private static final double R_PX_HEIGHT = 300 / 2.533;

	private static final double CORNER_Y_IN_RS = 1.267;
	private static final double CORNER_X_IN_RS = 1.267;

	private static final double POINT_RADIUS = 3;


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getR() {
		return r;
	}

	public String getDate() {
		return date;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public double getOffsetLeft() {
		double xInRs = (x / r) + CORNER_X_IN_RS;

		return xInRs * R_PX_WIDTH - POINT_RADIUS;
	}

	public double getOffsetTop() {
		double yInRs = CORNER_Y_IN_RS - (y / r);

		return yInRs * R_PX_HEIGHT - POINT_RADIUS;
	}

	public boolean isHit() {
		return hit;
	}

	public String getHitStringValue() {
		return hit ? "hit" : "miss";
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public PointCheck(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.hit = false;
	}
}