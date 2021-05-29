package Model;

/*
 * ���̷��� Ŭ����
 * �⺻ ���� ���̷����� �������� ġ������ ��� ����������,
 * ���ú� ����ȭ�� ���� ��ü�� ���� �����ؼ� �����ϴ°� ���� �� ����.
 * 
 * static �������� �������� ������ �ִ� ���̷����� �⺻�� 			<- ����� ��å�� ���ؼ� ������ ����
 * infection_chage�� death_change�� ���ú� ����Ǵ� ��ȭ�� 	<- ����� ��å�� ���ؼ� ����, ���ú��� �ٸ� ��
 * 
 * ���� ���α׷� ������ ���Ǵ� ������(ġ����)�� ������ ���� ���ȴ�.
 * ������ = infection_rate * this.infection_change
 * ġ���� = death_rate * this,death_change
 */


public class Virus {
	static final double MINIMUM_RATE = 0.00000001;
	static int incubation_period = 14;			// �ẹ��
	static double infection_rate;
	static double death_rate;
	
	private	double selection_rate;			// ���ú� ������ ���� ���� Ȯ��
	private double infection_change;		// ���ú� ������ ��ȭ��
	private double death_change;			// ���ú� ġ���� ��ȭ��
	
	
	public Virus(){
		this.resetChanges();
	}
	
	static public void setVirus(double i_rate, double d_rate) {
		infection_rate = i_rate;
		death_rate = d_rate;
	}
	
	public void resetChanges() {	// ��ȭ�� �ʱ�ȭ
		this.infection_change= 1;
		this.death_change = 1;
		this.selection_rate = 0.4;
	}
	// ������(ġ����)�� ��å�� ���� n��ŭ ��ȭ�� ��� �����ϴ� ���  : ex) ������ 0.05 �϶� -> infectionChange(-0.5) 
	public void infectionChange(double n) {
		if (this.infection_change+n >0)
			this.infection_change += n;
		else
			this.infection_change = MINIMUM_RATE;	// ��ȭ�� ���� ���� 0�̳� ������ ���� �ʵ��� �Ѵ�.
	}
	public void deathChange(double n) {
		if (this.death_change+n >0)
			this.death_change+= n;
		else
			this.death_change = MINIMUM_RATE;
	}
	public void selectionChange(double n) {
		if (this.selection_rate+n >0)
			this.selection_rate+= n;
		else
			this.selection_rate = MINIMUM_RATE;
	}
	
	// Ư�� ����� ���� �Ǵ� ������� Ȯ��  
	public boolean isInfect() {
		double rando = Math.random();
		
		if (rando < (infection_rate * this.infection_change))
			return true;
		else
			return false;
	}
	public boolean isDeath() {
		double rando = Math.random();
		
		if (rando < (death_rate * this.death_change))
			return true;
		else
			return false;
	}
	public double getInfectionRate() {
		return infection_rate * this.infection_change;
	}
	public double getDeathRate() {
		return death_rate * this.death_change;
	}
	public double getInfectionChange() {
		return  this.infection_change;
	}
	public double getDeatChange() {
		return this.death_change;
	}
	public double getSelectionRate() {
		return this.selection_rate;
	}
}
