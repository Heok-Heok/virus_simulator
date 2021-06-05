package DataControl;

import FileControl.CSVcontrol;

import Model.*;


public class CityControl {
	public static void createCity(String filepath) {
		
	}
	public static void createTestCity() {
		int city_num = 5;
		
		// 1. ���� ����
		City.cityList.clear();
		//policyApply.pa.clear();
		
		for (int i=0; i<city_num; i++) {
			if (i==0)
				new policyApply(new City(i*100, i*100, 220000, 10));
			else
				new policyApply(new City(i*100, i*100, 220000));
		}
		// 2. ���� ���� ����
		int [][] connection = new int[city_num][]; 
		for (int i=0; i<City.cityList.size(); i++) {
			connection[i] = new int[city_num];
			for (int j=0; j<City.cityList.size(); j++) {
				if (i-j == 1 || j-i == 1 ) {
					connection[i][j] = 2000;
				}
				else {
					connection[i][j] = 0;
				}
			}
		}
		City.city_conn = connection;
		// 3. ���̷��� ����
		Virus.setVirus(0.01, 0.00001);
		
		// 4. ��� �ʱ�ȭ
		Vaccine.restVaccine(City.cityList);
		
		
		
		
	}
	public static void readPolicyData() {
		// CSV ���Ϸ� ���� Policy ������ �����´�.
		int policy_num = 37; // ��ü ��å ��
		policy.policy_list = CSVcontrol.getPolicyList(policy_num);
		return;
	}
	
	public static String[] gameProgress() {
		// ���� ����, �α� ����, ��� ����, ��� ����, ��� ����
		// ���� �� log�� ���� string �迭 ���� 
		int cities = City.cityList.size();	// ��ü ���� ��
		
		for (int i=0; i<cities; i++) {
		//0. ��å ȿ�� ����
		
		//1. ���̷��� ����
			City.cityList.get(i).progressCityVirus();
		//1.a ��� ����
			City.cityList.get(i).progressCityVaccine();
		//2. �α� ����
			for (int j=0; j<cities; j++) {
				if (i!=j) {
					City.cityList.get(i).progessCityFlow(j);
				}
			}
		
		}
		//3. ��� ���� & ���� & �й�
		Vaccine.progressResearch();
		Vaccine.progressProduction();
		Vaccine.vaccineDestribute();
		
		//3. �α� �ݿ�
		// �α� ��Ȳ ��� + ��� ���൵ 
		String[] input_log;
		int people_data[] = null;
		int tmp_data[] = null;
		for (int i=0; i<cities; i++) {
			tmp_data = City.cityList.get(i).getPeople().getPeople();
			if (i==0) {
				people_data = tmp_data;
			}
			else {
				for (int j=0; j<tmp_data.length;j++) {
					people_data[j] += tmp_data[j];
				}
			}
		}
		input_log = new String [people_data.length+1];
		for(int i=0; i<people_data.length;i++) {
			input_log[i] = Integer.toString(people_data[i]);
		}
		input_log[input_log.length-1] = Double.toString(Vaccine.getProgress());
		
		
		//4. ���� ���� Ȯ��
		
		
		//5. ���� ����
		
		
		return input_log;
	}
}
