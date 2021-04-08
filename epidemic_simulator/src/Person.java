import java.util.Random;;

public class Person {
	private int px;		// x position
	private int py;		// y position
	private int status;	// current status 
	static int move_range =1;	// speed of Person 
	private Building place;
	public static Building[] b_list;
	
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
	public Person(int x, int y, Building p) {
		this.px = x;
		this.py = y;
		this.status = 1;
		this.changePlace(p);
	}
	
	public int getX() {
		return this.px;
	}
	public int getY() {
		return this.py;
	}
	public Place getPlace() {
		return this.place;
	}
	
	public void randomMove() {		
		Random rnd = new Random();
		switch (rnd.nextInt(4)) {
		case 0:
			this.px += move_range;
			if (is_in_area(this.place) == false) {		// when person try to get out of boundary
				this.px -= move_range;					// Case(in mainframe) : Go back to origin position
				if(this.place != b_list[0]) {			// Case(in building) : Get out of Building and warp to mainframe
					this.getOutofBuilding(this.place);
				}
			}
			break;
		case 1:
			this.px -= move_range;
			if (is_in_area(this.place) == false) {
				this.px += move_range;
				if(this.place != b_list[0]) {			
					this.getOutofBuilding(this.place);
				}
			}
			break;
		case 2:
			this.py += move_range;
			if (is_in_area(this.place) == false) {
				this.py -= move_range;
				if(this.place != b_list[0]) {			
					this.getOutofBuilding(this.place);
				}
			}
			break;
		case 3:
			this.py -= move_range;
			if (is_in_area(this.place) == false) {
				this.py += move_range;
				if(this.place != b_list[0]) {			
					this.getOutofBuilding(this.place);
				}
			}
			break;
		}
		this.checkEnterBuilding();					// When Person is in Mainframe and Building area, enter to Building.
	}
	public void changePlace(Building p) {
		if (this.place != null) 
			this.place.deletePerson(this);
		p.addPerson(this);
		this.place = p;
	}
	private boolean is_in(Building p) {
		if(this.px>p.bx1 && this.px<(p.bx1 + p.b_width)) {
			if(this.py>p.by1 && this.py < (p.by1 + p.b_height))
				return true;
		}
		return false;
	}
	private boolean is_in_area(Building p) {
		if(this.px>p.x && this.px<(p.x + p.width)) {
			if(this.py>p.y && this.py < (p.y + p.height))
				return true;
		}
		return false;
	}
	private void getOutofBuilding(Building b) {
		this.px = b.bx1 + b.b_width/2;
		this.py = b.by1 + b.b_height + 10;
		changePlace(b_list[0]);
	}
	private void checkEnterBuilding() {
		if (this.place == b_list[0]) {
			for (int i=1; i<b_list.length; i++) {
				if (is_in(b_list[i])) {
					changePlace(b_list[i]);
					this.px = 150;
					this.py = 150;
				}
			}
		}
	}
}

