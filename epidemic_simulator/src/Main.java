import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Math;
import java.lang.Thread;;

class MyComponent extends JComponent
{
	private Person[] agents;
	
	public MyComponent() {
		 Random rnd = new Random();
		 double pi = Math.PI;
		 int distance;
		 int center_x = 300;
		 int center_y = 300;
		 
		 //Person[] agents;
		 agents = new Person[1000]; 	// Human Class Array
		 for (int i =0; i<agents.length;i++) {	// 
			 distance = rnd.nextInt(200);
			 agents[i] = new Person((int)(distance*Math.cos(2*pi/agents.length*i))+center_x,(int)(distance*Math.sin(2*pi/agents.length*i))+center_y);
			 //g.fillOval(agents[i].getX(), agents[i].getY(), 7, 7);
		 }
	}

public void paint(Graphics g)
 {

	 for (int i =0; i<agents.length;i++) {
		 g.fillOval(agents[i].getX(), agents[i].getY(), 7, 7);
	 }
	 
 }
 public void moveDots() {
	 for (int i=0; i<agents.length;i++) {
		 agents[i].randomMove();
	 }
	 repaint();		// Update the current status
	 				// When repaint() is called, paint() executes.
 }
}

class Java2D extends JFrame
{
 public Java2D()
 {
  this.setTitle("Java 2D");
  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  this.setSize(1200, 1000);
  
  this.setVisible(true);
  
  MyComponent mc = new MyComponent();
  this.add(mc);
  
  // dot moves randomly
  while(true) {
  //for (int i=0; i<1000; i++) {
	  mc.moveDots();
	  try {
		Thread.sleep(10);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
 }
}

public class Main {
 public static void main(String[] args) {
  Java2D jFrame = new Java2D();
 }

} 