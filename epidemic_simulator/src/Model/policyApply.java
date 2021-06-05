package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class policyApply {
	
	public static ArrayList<policyApply> pa = new ArrayList<policyApply>();
	
	private ArrayList<int[]> pal = new ArrayList<int[]>();

	private City c;
	
	static int cityIndexCount = 11; // 4;

	private People p;
	
	private Virus v;
	
	public policyApply(City c) {
		this.c = c;
		this.p = c.cityP;
		this.v = c.vi;
		pa.add(this);
	}
	
	public static int[][] getPAL() { //{{���ù�ȣ, 1�� �ε��� ��ȭ��, 2�� �ε��� ��ȭ�� ... n�� �ε��� ��ȭ��}, {�ݺ�}} ���·� ���
		int[][] civ = new int[pa.size()][cityIndexCount];
		
		for(policyApply p : pa) {
			p.setApply();
			int[] aa = p.arrApply();
			for(int i = 0; i<cityIndexCount; i++)
				civ[p.c.cityNum-1][i] = aa[i];
		}
		return civ;
	}
	
	private int[] arrApply() {
		int[] result = new int[cityIndexCount];
		
		for(int i = 0; i<result.length; i++) {
			result[i] = 1;
		}
		
		for(int[] iv : pal) {
			if(iv[0] < cityIndexCount) {
				if(iv[1] > 0)
					result[iv[0]] *= iv[1];
			}
		}
		return result;
	}
	
	private void setApply() {
		
		for(policy p : c.p_list) {
			arrange(p);
		}

		clean();
	}
	
	private void clean() {
		for(Iterator<policy> itr = c.p_list.iterator(); itr.hasNext();) {
			policy rem = itr.next();
			if(rem.getValue(rem.size) == 5) {
				itr.remove();
			}
		}
	}

	private void arrange(policy p) {
		int[] result = new int[2];
		int id = 0;
		int val = 1;
		int index = 0 + 1;
		int code = 0;	//0. �ܼ�, 1, ���, 2. ����, 3. ��� ���� |����| 4. ����, 5. ��ȸ��
		while((code = readCode(p, index)) < 4) {
			System.out.println(p.getPolicyIndex() + "," + code);
			result[id] = getVal(p,index);
			result[val] = getVal(p, index+1);
			
			
			switch(code) {
				case 0:
					index +=3;
					break;
					
				case 1:
					result[val] *= propor(p, index);
					index += 4;
					break;
					
				case 2:
					result[val] *= pif(p, index);
					index += 4 + ifcode(p, index);
					break;
				
				case 3:
					result[val] *= propor(p, index);
					index +=1;
					result[val] *= pif(p, index);
					index += 4 + ifcode(p, index);
					break;
				default :
					System.out.println("error! : Wrong Syntax!");
					System.out.println("   in policy :" +p.getPolicyIndex() +" -> " + index + " : " + code);
					return;
			}
			
			pal.add(result);
		}
		
	}


	private int readCode(policy p, int i) {
		return p.getValue(i);
	}
	
	private int getVal(policy p, int i) {
		return p.getValue(1+i);
	}
	
	private int getData(policy p, int i) {
		/*
		 * 1 : infection_rate
		 * 2 : death_rate
		 * 3 : trust_index
		 * 4 : turn_gold
		 * 5 : city_flow
		 * 6 : population youth_rate
		 * 7 : population middle_rate
		 * 8 : population old_rate
		 * 9 : vaccine distribute
		 * 10 : capacity
		 * 11 : selection rate
		 * 12
		 * 13
		 * 14
		 * 15 : research progress
		 * 16 : research step
		 * 17 : vaccine production
		 * 18 : mask_production
		 */
		int index = 0;
		index = p.getValue(i);
		double[] rate = c.getPeople().getRate();;
		
		switch(index) {
			case 1:
				return (int)(c.getVi().getInfectionRate() * 100);	// ������ ��������
			case 2:
				return (int)(c.getVi().getDeathRate() * 10000);		// �����
			case 3:
				return c.getTrustIndex();							// ������
			case 4:
				return c.plus_gold;									// �� ���
			case 5:
				return City.city_conn[c.cityNum][c.cityNum];		// �α� ����
			case 6:
				return (int)(rate[0]*100);							// �������
			case 7:
				return (int)(rate[1]*100);							// �߳� ����
			case 8:
				return (int)(rate[2]*100);							// ��� ����
			case 9:
				double[] distribute = Vaccine.getVaccineCityRate();
				return (int)(distribute[c.cityNum-1]*100);			// ���� ��� �й���
			case 10:
				return c.capacity;									// ���� �ݸ� ���� ����
			case 11:
				return (int)(c.getVi().getSelectionRate() * 100);	// ���� ���� ��
				
			case 15:
				return (int)(Vaccine.getProgress() * 1000);			// ��� ���൵ 
			case 16:
				return Vaccine.getStep();							// ��� �ܰ�
			case 17:
				return Vaccine.getVstock();							// ��� ���
			case 18:
				return City.mask_production;						// ����ũ ����
		}
		return 0;
	}
	
	private int propor(policy p, int i) {
		return getData(p, i+3);
	}
	
	private int pif(policy p, int i) {
		int data = getData(p, i+4);
		switch(p.getValue(i+3)) {
			case 0:
				if(data <= 0)
					return 1;
				else
					return 0;
			case 1:
				if(data > 0)
					return 1;
				else return 0;
			case 2:
				if(data == p.getValue(i+5))
					return 1;
				else return 0;
			case 3:
				if(data > p.getValue(i+5))
					return 1;
				else return 0;
		}
		return 0;
	}
	
	private int ifcode(policy p, int i) {
		int code = p.getValue(i+3);
		
		if(code == 1 || code == 2)
			return 0;
		else
			return 1;
	}
	
}