package Model;

//import java.lang.Enum;
import java.util.Random;

enum Types{
	/*
	 * S : Susceptible						���� ���
	 * E : Exposed							���˱� : ���̷��� ���� ���̳�, ���� x
	 * I_UNID : Infected but unidentified	��Ȯ�� ������ : ���������� ���� ���� ���� �ο�
	 * I_UNISO : Infected but not isolated	��ݸ� ������ : ���� O, Ȯ�� O, �ݸ� X, ���� ����
	 * I_ISO : Infected and isolated		�ݸ� ������ : ���� O, Ȯ�� O, �ݸ� O, ���� �Ұ�
	 * R : Recovered						ȸ����
	 * D : Death							����ο�
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
		int i_period;			// incubation period : �ẹ��, E���� I ��� �� �� �ʿ�
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
		/* �α� �����͸� ���� �迭�� ���
		 [0] : ��ü �α� ��
		 [1] : ����� �񰨿���(���� �񰨿� + ���˱� + ��Ȯ�� ������)
		 [2] : ������ (��ݸ� ������ + �ݸ� ������)
		 [3] : ȸ���� (��� ���� �ο�  or ȸ�� �ο�)
		 [4] : �����
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
		 * [0] = youth	������
		 * [1] = middle	�߳���
		 * [2] = old	�����
		 */
		return this.people_age_group;
	}
	public int [] getSEIR() {
		return this.people_seir;
	}
	public int [] getdElist() {
		return this.dE_list;
	}
	
	public void progressVirus(Virus vi, int capacity) {		// �α��� ���� ���� ����, capacity�� �ݸ� ���� ���� �ο��̴�.
		int dE, dI;		// ���ⱺ, ������ ��ȭ��
		int dIso;		// �ݸ��� ��ȭ��
		int[] dD;		// ����� ��ȭ��
		dD = new int[3];
		
		// 1. SEIR ����
		// 1.1 S to E (�񰨿��ڵ��� ������(�ݸ��� ����)�� ������ ��� �������� ���� ���˱����� ��ȭ �Ѵ�.)
		dE = caldE(this.people_seir[Types.S.getValue()], this.people_seir[Types.I_UNID.getValue()]+this.people_seir[Types.I_UNISO.getValue()], getSum(), vi);
		// 1.2 E to I_UNID (���˱��� �ẹ�Ⱑ ������ �������� �ȴ�.)
		dI = caldI();
		// 1.3 I's to D (�������� ġ������ ���� ����Ѵ�.)
		dD = caldD(people_seir[Types.I_UNID.getValue()],people_seir[Types.I_UNISO.getValue()],people_seir[Types.I_ISO.getValue()],vi);
		// 1.4 apply changes (������ ����� ��ȭ���� people_seir�� �ݿ��Ѵ�.)
		this.people_seir[Types.S.getValue()] += -dE;
		this.people_seir[Types.E.getValue()] += dE - dI;
		this.people_seir[Types.I_UNID.getValue()] += dI - dD[0];
		this.people_seir[Types.I_UNISO.getValue()] += -dD[1];
		this.people_seir[Types.I_ISO.getValue()] += -dD[2];
		this.people_seir[Types.D.getValue()] += dD[0] + dD[1] + dD[2];
		
		// 2. Ȯ���� ���� + Ȯ���� �ݸ�
		
		//2.1.ex �����ڴ� 100% Ȯ�� �ȴٰ� ���� - ���� ���� �� ���
		//this.people_seir[Types.I_UNISO.getValue()] += this.people_seir[Types.I_UNID.getValue()];
		//this.people_seir[Types.I_UNID.getValue()] = 0;
		//2.1 ������ ����
		int dIdent = calIdent(this.people_seir[Types.I_UNID.getValue()], vi);
		this.people_seir[Types.I_UNISO.getValue()] += dIdent;
		this.people_seir[Types.I_UNID.getValue()] -= dIdent;
		
		
		// 2.2 �ݸ��� ����
		dIso = calIso(this.people_seir[Types.I_UNISO.getValue()], this.people_seir[Types.I_ISO.getValue()],capacity);
		this.people_seir[Types.I_UNISO.getValue()] -= dIso;
		this.people_seir[Types.I_ISO.getValue()] += dIso; 
		
		
	}
	
	public int progressVaccine(int vaccine) {
		// ��� ���� (������(I's), ���˱�(E), �񰨿���(S) ������ ��� ���� �� ȸ����(R)���� ��ȯ)
		// ��� ���� �� ���� ��� �� ����
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
	private int getSum() { // ����� ���� ���� �α��� ���
		int sum=0;
		for (int i=0; i<Types.D.getValue()+1; i++) {
			sum += this.people_seir[i];
		}
		return sum - this.people_seir[Types.D.getValue()];
	}
	private int caldE(int S, int I, int SUM, Virus vi) {
		// ���Ժ����� �̿��ؼ� �ش� Ȯ���� ���� ��� �� ���
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
		// ���� �α����� ������ �ο��� ���
		// �ڵ������� �޴� �� �α� �߰��� ���
		//  ���� ���� �α� ��ü ���� return
		
		double d_flow;	// Ȯ�� ����� ���� �α� ���� (int�� ĳ���� �ؼ� moving���� ����)
		int moving;		// ���� �α� ���� ��
		int final_flow ;	// ���� �α� ���� ����; 
		final_flow = flow;
		
		int n;
		double p;
		Random bin_distri = new Random();
		int sum = this.getSum();
		//System.out.println(sum);
		for (int i=0; i<6; i++) {
			// 1. ���� ��Ȳ�� ���� ������ �α� ���
			if (sum == 0) {		// ���� �α� sum�� 0�̸� ���� (0������ ����)
				return final_flow - flow;
			}
			n = flow;
			p = ((double)this.people_seir[i]/sum);
			d_flow = bin_distri.nextGaussian();
			d_flow = d_flow * Math.sqrt((double)n * p*(1-p));
			d_flow += (double)n*p;
			//System.out.print("d_flow " + n +" - " + p+ " - ");
			moving = (int)Math.round(d_flow);
			
			// 2. ���� �α� ������ �� (������ �α��� ���� �α� �� ���� ������ �ȵȴ�.)
			if (i==5) {
				// ��� ������ ������ ���̽��� 0�� ���´�.
				moving = flow;
			}
			if (moving > this.people_seir[i]) {
				moving = this.people_seir[i];
			}
			// 2.ex ���˱��� dE_list ������ ���� ��������
			//		�� �ο����� ���� ���� �������� �Ǳ� ����
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
			
			// 3. ��� �ݿ�
			this.people_seir[i] -= moving;
			destination.people_seir[i] += moving;
			
			// 4. sum�� flow �� ���� (�̹� �ִ� �α� �������� ��� �� ������ ����)
			//System.out.println(i+ ": "+this.people_seir[i] + "�� ��, "+ moving+ "��");
			sum -= this.people_seir[i] + moving;
			flow -= moving;
		}
		
		return final_flow;
	}
	void flow_dE_list(int day, int flow) {
		// ���� �α� ���� ���˱��� ��� dE list�� ���� �ؾ��ϹǷ� �޼ҵ� �߰�
		// ���� ���� : day�� �� �������� �Ǵ� �ο� flow�� ��ŭ �ش� dE_list�� add
		this.dE_list[day] += flow; 
	}
}

