package edu.ifmo.tikunov.web.lab2.service;

public class AreaCheckService {

	public boolean check(PointCheck p) {
		if (p.x == 0 && p.y == 0) {
			return true;
		} else if (p.x > 0 && p.y > 0) {
			return p.y <= p.r/2 && p.x <= p.r;
		} else if (p.x < 0 && p.y > 0) {
			return p.y < 2 * p.x + p.r;
		} else if (p.x < 0 && p.y < 0) {
			return false;
		} else {
			return p.x * p.x + p.y * p.y < p.r * p.r;
		}
	}
}
