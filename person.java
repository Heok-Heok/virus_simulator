package tail;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class person {
	static ArrayList<person> p = new ArrayList<person>();
	
	private int x, y;
	private int size;
	private int desX, desY;
	private int range = 50;
	boolean stop = true;
	boolean hit = false;
	private double angle;
	private int X, Y;
	private Color skin;
	private Color red = new Color(255,0,0);
	private Color black = new Color(0,0,0);
	boolean des = false;
	
	static double[] gak = new double[10];
	
	static int point = 0;
	
	person(int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.size = size;
		p.add(this);
	}

	int getx() {
		return x;
	}
	
	int gety() {
		return y;
	}
	
	void blink(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	private void move() {
		this.x+=X;
		this.y+=Y;
	}
	
	void navigate(int x, int y) {
		this.des = true;
		this.desX = x;
		this.desY = y;
		double a = desX-this.x;
		double b = desY-this.y;
		double r = Math.sqrt(a*a + b*b);
		this.angle = Math.toDegrees(Math.asin(b/r));
		this.des=true;
	}
	
	private boolean setDir() {
		double a = desX-this.x;
		double b = desY-this.y;
		int eR = 7;
		if(-eR <= a && a <= eR && -eR <= b && b <= eR)
		{
			blink(desX, desY);
			point+=5;
			System.out.println(point+10000);
			this.des = false;
			return false;
		}
		if(hit) {
			
		}
		else {
			int a1 = desX-this.x;
			int b1 = desY-this.y;
			int A, B;
			A = (int)(a1*Math.cos(Math.toRadians(90-angle))-b1*Math.sin(Math.toRadians(90-angle)));
			B = (int)(a1*Math.sin(Math.toRadians(90-angle))+b1*Math.cos(Math.toRadians(90-angle)));
			
			if(B>0) {
				if(A>0) {
					angle-=gak[0];
				}
				else if(A<0) {
					angle+=gak[1];
				}
			}
			else {
				if(A>=0) {
					angle-=gak[2];
				}
				else if(A<0) {
					angle+=gak[3];
				}
			}
		}
		X=(int)(Math.cos(Math.toRadians(angle))*7);
		Y=(int)(Math.sin(Math.toRadians(angle))*7);
		return true;
	}
	
	private boolean detectPerson() {
		int pita = 0;
		person p = null;
		double com1;
		double com2 = range;
		this.skin = black;
		for(person per : person.p) {
			if(per==this) {
				continue;
			}
			if(per.getx() < x + range && x - range< per.getx()) {
				if(per.gety() < y + range && y - range< per.gety()) {
					pita = (per.getx()-x)*(per.getx()-x) + (per.gety()-y)*(per.gety()-y);
					com1 = Math.sqrt(pita);
					if(com1 < com2) {
						com2=com1;
						p=per;
						this.skin = red;
					}
				}
			}
		}
		if(range/2 < com2 && p!=null) {
			int a = p.x-this.x;
			int b = p.y-this.y;
			int A, B;
			A = (int)(a*Math.cos(Math.toRadians(90-angle))-b*Math.sin(Math.toRadians(90-angle)));
			B = (int)(a*Math.sin(Math.toRadians(90-angle))+b*Math.cos(Math.toRadians(90-angle)));
			
			if(B>0) {
				if(A>0) {
					angle+=gak[4];
				}
				else if(A<0) {
					angle-=gak[5];
				}
			}
			else if(B==0 && A<0) {
				angle-=gak[6];
			}
			else if(B==0 && A>0) {
				angle+=gak[7];
			}
			else {
				angle+=gak[8];
			}
		}
		else if(p != null && com2>size+1) {
			Random rand = new Random();
			int rate = rand.nextInt((int) (gak[9]*gak[9]+1));
			if(rate<1) {
				stop=true;
			}
		}
		else if(p != null && com2<=size+1) {
			point-=2;
		}
		else {
			return false;
		}
		//point-=1;
		return true;
	}
	
	void moving() {
		if(!setDir()) {
			stop = true;
			return;
		}
		else if(stop){
			stop = false;
		}
		hit = detectPerson();
		move();
	}
	
	int getRange() {
		return range;
	}
	
	int getSize() {
		return size;
	}
	
	int getDesX() {
		return desX;
	}
	
	int getDesY() {
		return desY;
	}
	
	int getX() {
		return X;
	}
	
	int getY() {
		return Y;
	}
	
	Color getColor() {
		return skin;
	}
}