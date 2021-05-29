package Model;

//import java.lang.Enum;
import java.util.Random;

enum Types{
	/*
	 * S : Susceptible						감염 대상군
	 * E : Exposed							접촉군 : 바이러스 보유 중이나, 전염 x
	 * I_UNID : Infected but unidentified	미확인 감염군 : 감염됐으나 집계 되지 않은 인원
	 * I_UNISO : Infected but not isolated	비격리 감염군 : 감염 O, 확인 O, 격리 X, 전파 가능
	 * I_ISO : Infected and isolated		격리 감염군 : 감염 O, 확인 O, 격리 O, 전파 불가
	 * R : Recovered						회복군
	 * D : Death							사망인원
	 */
	S(0),E(1), I_UNID(2), I_UNISO(3), I_ISO(4), R(5), D(6); 
	
	private final int value;
	Types(int value){this.value = value;}
	public int getValue() {return value;}
}

public class People {
	private int[] people_seir;			// separate people by SEIR+ model 
	private double[] people_age_group;	// 0 : youth rate, 1: middle rate, 2 : old rate
										// The sum of age_grop must be 1
	private int[] dE_list;				// S to E list
										// Using this for E to I

	
	public People() {
		this.people_seir = new int[Types.D.getValue()+1];
		this.people_age_group = new double[3];
		
		for(int i : this.people_seir) {
			i = 0;
		}
		int i_period;			// incubation period : 잠복기, E에서 I 계산 할 때 필요
		i_period = Virus.incubation_period;
		this.dE_list = new int[i_period];
		for(int i : this.dE_list) {
			i = 0;
		}
		
		this.people_age_group[0] = 0.3;
		this.people_age_group[1] = 0.4;
		this.people_age_group[2] = 0.3;
	}
	
	
	public void set_people(int S, int I) {
		this.people_seir[Types.S.getValue()] = S;
		this.people_seir[Types.I_UNID.getValue()] = I;
	}
	public void set_rate(double youth, double middle, double old) {
		this.people_age_group[0] = youth;
		this.people_age_group[1] = middle;
		this.people_age_group[2] = old;
	}
	
	public int[] getPeople() {
		// get People data for view -> [0] : whole people [1] : non_infected, [2] : infected, [3] : Recovered, [4] : Death 
		/* 인구 데이터를 정수 배열로 출력
		 [0] : 전체 인구 수
		 [1] : 집계상 비감염자(실제 비감염 + 접촉군 + 미확인 감염자)
		 [2] : 감염자 (비격리 감염자 + 격리 감염자)
		 [3] : 회복군 (백신 투여 인원  or 회복 인원)
		 [4] : 사망자
		*/
		int[] seir_data;
		seir_data = new int [5];
		
		seir_data[1] = this.people_seir[Types.S.getValue()]+this.people_seir[Types.E.getValue()]+this.people_seir[Types.I_UNID.getValue()];
		seir_data[2] = this.people_seir[Types.I_UNISO.getValue()] +this.people_seir[Types.I_ISO.getValue()];
		seir_data[3] = this.people_seir[Types.R.getValue()];
		seir_data[4] = this.people_seir[Types.D.getValue()];
		
		for (int i=0; i<4; i++) {
			seir_data[0] += seir_data[i+1];
		}
		
		return seir_data;
	}
	public double [] getRate() {
		/*
		 * [0] = youth	유년층
		 * [1] = middle	중년층
		 * [2] = old	노년층
		 */
		return this.people_age_group;
	}
	public int [] getSEIR() {
		return this.people_seir;
	}
	public int [] getdElist() {
		return this.dE_list;
	}
	
	public void progressVirus(Virus vi, int capacity) {		// 인구내 전염 전파 진행, capacity는 격리 수용 가능 인원이다.
		int dE, dI;		// 노출군, 감염군 변화량
		int dIso;		// 격리자 변화량
		int[] dD;		// 사망자 변화량
		dD = new int[3];
		
		// 1. SEIR 변동
		// 1.1 S to E (비감염자들은 감염자(격리자 제외)와 접촉한 경우 전염륭에 따라 접촉군으로 변화 한다.)
		dE = caldE(this.people_seir[Types.S.getValue()], this.people_seir[Types.I_UNID.getValue()]+this.people_seir[Types.I_UNISO.getValue()], getSum(), vi);
		// 1.2 E to I_UNID (접촉군은 잠복기가 끝나면 감염군이 된다.)
		dI = caldI();
		// 1.3 I's to D (감염군은 치사율에 따라 사망한다.)
		dD = caldD(people_seir[Types.I_UNID.getValue()],people_seir[Types.I_UNISO.getValue()],people_seir[Types.I_ISO.getValue()],vi);
		// 1.4 apply changes (위에서 계산한 변화율을 people_seir에 반영한다.)
		this.people_seir[Types.S.getValue()] += -dE;
		this.people_seir[Types.E.getValue()] += dE - dI;
		this.people_seir[Types.I_UNID.getValue()] += dI - dD[0];
		this.people_seir[Types.I_UNISO.getValue()] += -dD[1];
		this.people_seir[Types.I_ISO.getValue()] += -dD[2];
		this.people_seir[Types.D.getValue()] += dD[0] + dD[1] + dD[2];
		
		// 2. 확진자 선별 + 확진자 격리
		
		//2.1.ex 감염자는 100% 확인 된다고 가정 - 이후 수정 할 요소
		//this.people_seir[Types.I_UNISO.getValue()] += this.people_seir[Types.I_UNID.getValue()];
		//this.people_seir[Types.I_UNID.getValue()] = 0;
		//2.1 감염자 선별
		int dIdent = calIdent(this.people_seir[Types.I_UNID.getValue()], vi);
		this.people_seir[Types.I_UNISO.getValue()] += dIdent;
		this.people_seir[Types.I_UNID.getValue()] -= dIdent;
		
		
		// 2.2 격리자 선별
		dIso = calIso(this.people_seir[Types.I_UNISO.getValue()], this.people_seir[Types.I_ISO.getValue()],capacity);
		this.people_seir[Types.I_UNISO.getValue()] -= dIso;
		this.people_seir[Types.I_ISO.getValue()] += dIso; 
		
		
	}
	
	public int progressVaccine(int vaccine) {
		// 백신 투여 (감염군(I's), 접촉군(E), 비감염군(S) 순으로 백신 투여 후 회복군(R)으로 전환)
		// 백신 투여 후 남은 백신 수 리턴
		int toRecover;
		for (int i = Types.I_ISO.getValue(); i>=0; i--) {
			if (this.people_seir[i] > vaccine) {
				toRecover = vaccine;
				this.people_seir[i] -= toRecover;
				this.people_seir[Types.R.getValue()] += toRecover;
				vaccine -= toRecover;
			}
			else {
				toRecover = this.people_seir[i];
				this.people_seir[i] -= toRecover;
				this.people_seir[Types.R.getValue()] += toRecover;
				vaccine -= toRecover;
			}
			if (vaccine == 0) {
				break;
			}
		}
		return vaccine;
	}
	private int getSum() { // 사망자 제외 남은 인구수 계산
		int sum=0;
		for (int i=0; i<Types.D.getValue()+1; i++) {
			sum += this.people_seir[i];
		}
		return sum - this.people_seir[Types.D.getValue()];
	}
	private int caldE(int S, int I, int SUM, Virus vi) {
		// 정규분포를 이용해서 해당 확률에 대한 결과 값 계산
		// Using Standard Distribution to calculate S to E change
		double dE=0;
		int n = S;
		double p;
		Random bin_distri = new Random();
		
		
		p = (double)I/(double)SUM*vi.getInfectionRate();
		
		dE = bin_distri.nextGaussian();
		dE = dE * Math.sqrt((double)n * p*(1-p));
		dE += (double)n*p;
		
		if (dE < 0) {
			dE = 0;
		}
	
		for (int i= Virus.incubation_period-1; i>0; i--) {
			this.dE_list[i] = this.dE_list[i-1];
		}
		this.dE_list[0] = (int) Math.round(dE);
		return (int) Math.round(dE);
	}
	
	private int caldI() {
		return this.dE_list[Virus.incubation_period-1];
	}
	private int[] caldD(int i1, int i2, int i3, Virus vi) {
		int[] deaths;
		deaths = new int [3];
		/*
		for (int i : deaths) {
			i = 0;
		}
		
		for (int j=0; j<i1; j++) {
			if (vi.isDeath()) {
				deaths[0]++;
			}
		}
		for (int j=0; j<i2; j++) {
			if (vi.isDeath()) {
				deaths[1]++;
			}
		}
		for (int j=0; j<i3; j++) {
			if (vi.isDeath()) {
				deaths[2]++;
			}
		}
		*/
		
		double d_count;
		int n;
		double p = vi.getDeathRate();
		Random bin_distri = new Random();
		
		n = i1;
		d_count = bin_distri.nextGaussian();
		d_count = d_count * Math.sqrt((double)n * p*(1-p));
		d_count += (double)n*p;
		deaths[0] = (int)d_count;
		
		n = i2;
		d_count = bin_distri.nextGaussian();
		d_count = d_count * Math.sqrt((double)n * p*(1-p));
		d_count += (double)n*p;
		deaths[1] = (int)d_count;
		
		n = i3;
		d_count = bin_distri.nextGaussian();
		d_count = d_count * Math.sqrt((double)n * p*(1-p));
		d_count += (double)n*p;
		deaths[2] = (int)d_count;
		
		return deaths;
	}
	private int calIso(int uniso, int iso, int capacity) {
		int dIso;
		dIso = capacity - iso;
		
		if (dIso < 0) {
			return 0;
		}
		else if (dIso > uniso) {
			return uniso;
		}
		else {
			return dIso;
		}
		
	}
	private int calIdent(int unid, Virus vi) {
		double ident;
		
		int n;
		double p;
		Random bin_distri = new Random();
		
		p = vi.getSelectionRate();
		n = unid;
		ident = bin_distri.nextGaussian();
		ident = ident * Math.sqrt((double)n * p*(1-p));
		ident += (double)n*p;
		
		return (int) Math.round(ident);
	}
	
	public int FlowOut(People destination, int flow) {
		// 현재 인구에서 나가는 인원을 계산
		// 자동적으로 받는 쪽 인구 추가도 계산
		//  최종 유동 인구 전체 값을 return
		
		double d_flow;	// 확률 계산을 위한 인구 유동 (int로 캐스팅 해서 moving에게 전달)
		int moving;		// 실제 인구 유동 수
		int final_flow ;	// 실제 인구 유동 총합; 
		final_flow = flow;
		
		int n;
		double p;
		Random bin_distri = new Random();
		int sum = this.getSum();
		//System.out.println(sum);
		for (int i=0; i<6; i++) {
			// 1. 감염 현황에 따라 나가는 인구 계산
			if (sum == 0) {		// 유동 인구 sum이 0이면 종료 (0나누기 방지)
				return final_flow - flow;
			}
			n = flow;
			p = ((double)this.people_seir[i]/sum);
			d_flow = bin_distri.nextGaussian();
			d_flow = d_flow * Math.sqrt((double)n * p*(1-p));
			d_flow += (double)n*p;
			//System.out.print("d_flow " + n +" - " + p+ " - ");
			moving = (int)Math.round(d_flow);
			
			// 2. 유동 인구 적절성 평가 (나가는 인구가 현재 인구 수 보다 많으면 안된다.)
			if (i==5) {
				// 계산 상으로 마지막 케이스는 0이 나온다.
				moving = flow;
			}
			if (moving > this.people_seir[i]) {
				moving = this.people_seir[i];
			}
			// 2.ex 접촉군은 dE_list 정보도 같이 보내야함
			//		이 인원들은 몇턴 이후 감염군이 되기 때문
			if (i == Types.E.getValue()) {
				int tmp = moving;
				for (int j=0; j<Virus.incubation_period; j++) {
					if (tmp > this.dE_list[j]) {
						tmp -= this.dE_list[j];
						destination.flow_dE_list(j, this.dE_list[j]);
						this.dE_list[j] = 0;
					}
					else {
						destination.flow_dE_list(j, tmp);
						this.dE_list[j] -= tmp;
						tmp = 0;
						break;
					}
				}
			}
			
			// 3. 결과 반영
			this.people_seir[i] -= moving;
			destination.people_seir[i] += moving;
			
			// 4. sum과 flow 값 변동 (이미 있는 인구 비율에서 계산 된 비율은 제외)
			//System.out.println(i+ ": "+this.people_seir[i] + "명 중, "+ moving+ "명");
			sum -= this.people_seir[i] + moving;
			flow -= moving;
		}
		
		return final_flow;
	}
	void flow_dE_list(int day, int flow) {
		// 유동 인구 계산시 접촉군의 경우 dE list도 전달 해야하므로 메소드 추가
		// 인자 설명 : day일 후 감염군이 되는 인원 flow명 만큼 해당 dE_list에 add
		this.dE_list[day] += flow; 
	}
}

