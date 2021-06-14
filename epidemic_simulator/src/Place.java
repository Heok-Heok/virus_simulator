
public class Place {
	int x, y;				// position
	int width, height;		// weight and height
	private ListPerson plist;		// the list of people
	private int in_person;			// how many people in this list 
	
	public Place() {
		this.x = 0;
		this.y = 0;
		this.width = 0;
		this.height = 0;
		this.plist = new ListPerson();
		this.in_person=0;
	}
	public Place(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.plist = new ListPerson();
		this.in_person=0;
	}
	
	public void addPerson(BuildPerson p) {
		this.plist.addPerson(p);
		this.in_person += 1;
	}
	public void deletePerson(BuildPerson p) {
		this.plist.deletePerson(p);
		this.in_person -= 1;
	}
	public int getDots(int [] dots) {
		ListPerson current;
		current = this.plist.next;
		for (int i=0; i<this.in_person; i++) {
			dots[i] = current.getPerson().getX();
			dots[this.in_person+i] = current.getPerson().getY();
			current = current.next;
		}
		return this.in_person;
	}
}

class ListPerson{
	ListPerson next;
	private BuildPerson p;
	
	ListPerson(){
		this.next = null;
		this.p = null;
	}
	ListPerson(BuildPerson p){
		this.next = null;
		this.p = p;
	}
	BuildPerson getPerson() {
		return this.p;
	}
	
	void addPerson(BuildPerson p){
		if (this.next == null) {
			this.next = new ListPerson(p);
		}
		else {
			this.next.addPerson(p);
		}
	}
	void deletePerson(BuildPerson p) {
		if (this.next.p == p) {
			this.next = this.next.next;
		}
		else {
			this.next.deletePerson(p);
		}
	}
}