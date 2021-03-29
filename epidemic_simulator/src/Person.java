import java.util.Random;;

public class Person {
	private int px;		// x position
	private int py;		// y position
	private int status;	// current status 
	static int move_range =1;	// speed of Person 
	
	public Person() {
		this.px = 0;
		this.py = 0;
		this.status = 1;
	}
	public Person(int x, int y) {
		this.px = x;
		this.py = y;
		this.status = 1;
	}
	
	public int getX() {
		return this.px;
	}
	public int getY() {
		return this.py;
	}
	
	public void randomMove() {		
		Random rnd = new Random();
		switch (rnd.nextInt(4)) {
		case 0:
			this.px += move_range;
			break;
		case 1:
			this.px -= move_range;
			break;
		case 2:
			this.py += move_range;
			break;
		case 3:
			this.py -= move_range;
			break;
		}
	}
}
