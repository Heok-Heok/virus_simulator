package Model;

public class CityEx {
	public People population;
	public Virus vi;
	private int capacity;
	
	public CityEx() {
		population = new People();
		vi = new Virus();
	}
	
	public int[] getCityPoulation() {
		return this.population.getPeople();
	}
	
	public void setCityPopulation(int pop) {
		this.population.set_people(pop, 0);
	}
	public void setCityInfection(int infect) {
		int [] pop_list;
		pop_list = this.population.getPeople();
		this.population.set_people(pop_list[1] - infect, infect);
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	public void addCapacity(int add) {
		this.capacity += add; 
	}
	
	public void cityProgress() {
		this.population.progressVirus(vi, capacity);
		
		int [] pop_list;
		pop_list = this.population.getPeople();
		//System.out.println(pop_list[0]+ "-  non_Infect:" + pop_list[1]+" Infected:" + pop_list[2]+ " Death:" + pop_list[4] + " Cured:" + pop_list[3]);
	}
}
