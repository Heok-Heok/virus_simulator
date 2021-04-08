
public class Building extends Place{
	private int type;
	int bx1, by1;
	int b_width, b_height;
	
	public Building() {
		super(20,20,20,20);
		this.type=1;
		this.bx1 = 0;
		this.by1 = 0;
		this.b_width=0;
		this.b_height=0;
	}
	public Building(int x, int y, int weight, int height) {
		super(100,100,100,100);
		this.type=1;
		this.bx1 = x;
		this.by1 = y;
		this.b_width=weight;
		this.b_height=height;
	}
	public Building(int x, int y, int width, int height, int type) {
		super(100,100,100,100);
		this.type=type;
		this.bx1 = x;
		this.by1 = y;
		this.b_width=width;
		this.b_height=height;
		if (type == 0) {
			this.x =x;
			this.y =y;
			this.width = width;
			this.height = height;
		}
	}
	
	public void changeType(int type) {
		this.type = type;
	}
}
