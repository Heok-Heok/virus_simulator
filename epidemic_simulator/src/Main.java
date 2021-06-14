import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.lang.Math;
import java.lang.Thread;;

class MyComponent extends JComponent
{
	private BuildPerson[] agents;
	private Building[] building;
	private int view_type;
	private int[] dotPoint;
	private int dots;
	
	public MyComponent() {
		 Random rnd = new Random();
		 double pi = Math.PI;
		 int distance;
		 int center_x = 300;
		 int center_y = 300;
		 
		 // 1. Create Mainframe and Buildings
		 building = new Building[5];			
		 building[0] = new Building(5,5,500,500,0);		// building[0] will be MainFrame of simulator
		 for (int i=1; i<building.length; i++) {		// building[1~] are normal buildings
			 building[i] = new Building(100+(i-1)*60, 100+(i-1)*60, 40, 40);
			 System.out.println(building[i].bx1 + " " + building[i].by1);
		 }
		 view_type = 0;
		 BuildPerson.b_list = building;		// copy building data to person
		 
		 // 2. Create People object
		 agents = new BuildPerson[1000]; 	// Human Class Array
		 dotPoint = new int[agents.length*2];
		 for (int i =0; i<agents.length;i++) {	// 
			 distance = rnd.nextInt(200);		// Set People start Position Mathmetically random
			 agents[i] = new BuildPerson((int)(distance*Math.cos(2*pi/agents.length*i))+center_x,(int)(distance*Math.sin(2*pi/agents.length*i))+center_y);
			 agents[i].changePlace(building[0]);
			 //g.fillOval(agents[i].getX(), agents[i].getY(), 7, 7);
		 }
	}

public void paint(Graphics g)
 {
	// 1. Draw Mainframe
	if (view_type == 0){		// 1.1 if view is for Mainframe (view_type = 0)
		for (int i=0; i<building.length; i++) {
			g.drawRect(building[i].bx1, building[i].by1, building[i].b_width, building[i].b_height);
			//System.out.println(i);
		 }
	}
	else {						// 1.2 if view is not for MainFrame(view inside of buildings for example)
		
	}
	
	// 2. Draw People(or dots)
	this.dots = building[view_type].getDots(this.dotPoint);
	 for (int i =0; i<dots;i++) {
		 g.fillOval(dotPoint[i], dotPoint[i+dots], 7, 7);
	 }
	 
	 
	 // 3. (option) Draw Sub View
	 int offset = 120;
	 for(int i=1; i<building.length; i++) {
			this.dots = building[i].getDots(this.dotPoint);
			 for (int j =0; j<dots;j++) {
				 g.drawRect(building[i].x + offset*5, building[i].y+(i-1)*offset, building[i].width, building[i].height);
				 g.fillOval(dotPoint[j]+offset*5, dotPoint[j+dots]+(i-1)*offset, 7, 7);
			 }
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
	  System.out.println("Draw");
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