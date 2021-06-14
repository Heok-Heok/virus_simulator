package Model;
import java.util.ArrayList;
import java.util.LinkedList;

public class City {
	
	public static ArrayList<City> cityList = new ArrayList<City>();
	
	public static int CityCount;		// ��ü ���ü�
	public static int mask_production;	// ����ũ ���� ����
	public static int total_gold;
	//public static LinkedList<policy> nation_list = new LinkedList<policy>();
	
	int x;
	int y;
	
	int cityNum;				// ���� ���� ��ȣ
	int trust_index;			// ������, gg�� ��Ī�� �ٲ�
	int capacity; 				// ���밡�� �ο�, �ݸ� ������ �α��� �ǹ� -> Virus Class�� ���� ��?
	int plus_gold;				// �ϴ� ���
	double city_flow; 			// �α� ������				
	
	
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
	public City() {		// ���� ������ ���� ����
		
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
	public int getTrustIndex() {
		return this.trust_index;
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
	public int getCapacity() {
		return this.capacity;
	}
	public int getTurnGold() {
		return this.plus_gold;
	}
	public double getCityFlow() {
		return this.city_flow;
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

	public void addPolicy(policy p) {
		this.p_list.add(p);
	}

	public void removePolicy() {
		this.p_list.remove();
	}
	
	
	void setP(People p) {
		this.cityP = p;
	}
	public void setFlowRate() {
		this.city_flow = 1;
	}
	public void setTrustIndex(int gg) {
		this.trust_index = gg;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void setGold(int gold) {
		this.plus_gold = gold;
	}
	public void setGold(int gold, double rate) {
		double[] age_rate = this.getPeople().getRate();
		this.plus_gold = (int)(age_rate[1]*gold*rate); 
	}
	public void progressCityVirus() {
		// ���̷��� ���� �޼ҵ� 
		this.cityP.progressVirus(this.vi, this.capacity);
	}
	public int progessCityFlow(int dest_city_index) {
		// �α� ���� �޼ҵ�
		People dest_people = City.cityList.get(dest_city_index).getPeople();
		
		int toflow;
		toflow = City.city_conn[dest_city_index][this.cityNum-1];
		
		
		return this.cityP.FlowOut(dest_people, toflow);
	}
	public void progressCityVaccine() {
		int [] city_vaccine = Vaccine.getVaccineCity();
		Vaccine.setCityVaccine(this.cityNum-1, this.cityP.progressVaccine(city_vaccine[this.cityNum-1]));
	}
	
	
	public void addTrustIndex(int index_change) {
		this.trust_index += index_change;
		if (this.trust_index > 100) {
			this.trust_index = 100;
		}
	}
	public void chageFlowRate(double rate) {
		this.city_flow = rate;
	}
	public void addCapacity(int additional_capacity) {
		this.capacity += additional_capacity;
	}
	
}