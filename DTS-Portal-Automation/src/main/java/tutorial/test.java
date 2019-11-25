package tutorial;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;

public class test {
	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(5000);
		a.MouseMover();

	}

}
class a{
	public static void MouseMover() {
    	try {
    		Robot robot = new Robot();
			Point point = MouseInfo.getPointerInfo().getLocation();
			System.out.println(point);
			robot.mouseMove(point.x+120, point.y +120);
//			robot.mouseMove(point.x, point.y);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

