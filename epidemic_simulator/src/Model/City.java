package Model;
import java.util.ArrayList;
import java.util.LinkedList;

public class City {
	
	public static ArrayList<City> cityList = new ArrayList<City>();
	
	static int CityCount;		// ��ü ���ü�
	
	int x;
	int y;
	
	int cityNum;				// ���� ���� ��ȣ
	int trust_index;			// ������, gg�� ��Ī�� �ٲ� ��
	int capacity; 				// ���밡�� �ο�, �ݸ� ������ �α��� �ǹ� -> Virus Class�� ���� ��?
	
	LinkedList<policy> p_list = new LinkedList<policy>();
	
	

	People cityP;				// ���� ���� �α� ����
	Virus vi;					// ���� ���� ���̷��� ����

	public static int city_conn[][];	// ���� �� ����, ���� ��� ���·� ����
	
	public City(int x, int y) {
		this.x = x;
		this.y = y;
		
		cityList.add(this);
		
		this.cityNum = cityList.size();
		this.vi = new Virus();
		this.cityP = new People();
	}
	public City(int x, int y, int population) {
		this(x,y);
		//this.cityP = new People();
		this.cityP.set_people(population, 0);
	}
	public City(int x, int y, int population, int infected) {
		this(x,y);
		//this.cityP = new People();
		this.cityP.set_people(population, infected);
	}
	
	static void setCityCount(int cc) {
		City.CityCount = cc;
		City.city_conn = new int[cc][cc];
	}
	
	static void setCityConn(int x, int y, int val) {
		City.city_conn[x][y] = val;
	}
	
	
	
	/////////////////////////////////////////////
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}
	public int getNum() {
		return this.cityNum;
	}
	public LinkedList<policy> getPolicy(){
		return this.p_list;
	}
	public People getPeople() {
		return this.cityP;
	}
	public Virus getVi() {
		return this.vi;
	}
	
	static ArrayList<int[]> getCC() {
		ArrayList<int[]> result = new ArrayList<int[]>();
		int[] xyxy = new int[4];
		for(int i = 0; i<CityCount; i++) {
			for(int j = CityCount; j<i; j--) {
				if(city_conn[i][j] > 0) {
					xyxy[0] = index2city(i).getx();
					xyxy[1] = index2city(i).gety();
					xyxy[2] = index2city(j).getx();
					xyxy[3] = index2city(j).gety();
					result.add(xyxy);
				}
			}
		}
		return result;
	}
	
	static City index2city(int index) throws IllegalArgumentException {
		for(City c : City.cityList) {
			if(index == c.cityNum)
				return c;
		}
		throw new IllegalArgumentException("city : out of index");
	}
	
	void changeConn(int val) {
		for(int i = 0; i<CityCount; i++) {
			City.city_conn[this.cityNum][i] *= val;
		}
		
		for(int i = 0; i<CityCount; i++) {
			City.city_conn[i][this.cityNum] *= val;
		}
	}

	void addPolicy(policy p) {
		this.p_list.add(p);
	}

	void removePolicy() {
		this.p_list.remove();
	}

	void setP(People p) {
		this.cityP = p;
	}
	
	
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void progressCityVirus() {
		// ���̷��� ���� �޼ҵ� 
		this.cityP.progressVirus(this.vi, this.capacity);
	}
	public int progessCityFlow(int dest_city_index) {
		// �α� ���� �޼ҵ�
		People dest_people = City.cityList.get(dest_city_index).getPeople();
		return this.cityP.FlowOut(dest_people, City.city_conn[dest_city_index][this.cityNum-1]);
	}
	public void progressCityVaccine() {
		int [] city_vaccine = Vaccine.getVaccineCity();
		Vaccine.setCityVaccine(this.cityNum-1, this.cityP.progressVaccine(city_vaccine[this.cityNum-1]));
	}

	
}