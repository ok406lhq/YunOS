package com.anchor.util;

public class Calc {
	
	/** * 计算方位角  author:zhoajunwei
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	public static double azimuthAngle(double x1, double y1, double x2, double y2) {
		double dx, dy, angle = 0;
		dx = x2 - x1;
		dy = y2 - y1;
		if (x2 == x1) {
			angle = Math.PI / 2.0;
			if (y2 == y1) {
				angle = 0.0;
			} else if (y2 < y1) {
				angle = 3.0 * Math.PI / 2.0;
			}
		} else if ((x2 > x1) && (y2 > y1)) {
			angle = Math.atan(dx / dy);
		} else if ((x2 > x1) && (y2 < y1)) {
			angle = Math.PI / 2 + Math.atan(-dy / dx);
		} else if ((x2 < x1) && (y2 < y1)) {
			angle = Math.PI + Math.atan(dx / dy);
		} else if ((x2 < x1) && (y2 > y1)) {
			angle = 3.0 * Math.PI / 2.0 + Math.atan(dy / -dx);
		}
		return (angle * 180 / Math.PI);
	}

	public static void main(String[] args) {
		double s=azimuthAngle(0,0,1,1);
		System.out.println(s);
	}
}
