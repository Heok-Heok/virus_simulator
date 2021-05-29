package Model;

/*
 * 바이러스 클래스
 * 기본 적인 바이러스의 전염율과 치사율은 모두 동일하지만,
 * 도시별 차별화를 위해 객체로 따로 생성해서 관리하는게 좋을 거 같다.
 * 
 * static 변수들은 국가에서 퍼지고 있는 바이러스의 기본값 			<- 사용자 정책에 의해서 변하지 않음
 * infection_chage와 death_change는 도시별 적용되는 변화량 	<- 사용자 정책에 의해서 변함, 도시별로 다른 값
 * 
 * 실제 프로그램 내에서 계산되는 감염율(치사율)은 다음과 같이 계산된다.
 * 감염율 = infection_rate * this.infection_change
 * 치사율 = death_rate * this,death_change
 */


public class Virus {
	static final double MINIMUM_RATE = 0.00000001;
	static int incubation_period = 14;			// 잠복기
	static double infection_rate;
	static double death_rate;
	
	private	double selection_rate;			// 도시별 감염자 선별 성공 확률
	private double infection_change;		// 도시별 감염율 변화율
	private double death_change;			// 도시별 치사율 변화율
	
	
	public Virus(){
		this.resetChanges();
	}
	
	static public void setVirus(double i_rate, double d_rate) {
		infection_rate = i_rate;
		death_rate = d_rate;
	}
	
	public void resetChanges() {	// 변화량 초기화
		this.infection_change= 1;
		this.death_change = 1;
		this.selection_rate = 0.4;
	}
	// 전염율(치사율)을 정책을 통해 n만큼 변화한 경우 적용하는 방법  : ex) 감염율 0.05 하락 -> infectionChange(-0.5) 
	public void infectionChange(double n) {
		if (this.infection_change+n >0)
			this.infection_change += n;
		else
			this.infection_change = MINIMUM_RATE;	// 변화율 값은 절대 0이나 음수가 되지 않도록 한다.
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
	
	// 특정 사람이 감염 또는 사망여부 확인  
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
