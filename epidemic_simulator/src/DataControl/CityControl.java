package DataControl;

import FileControl.CSVcontrol;

import Model.*;


public class CityControl {
	public static void createCity(String filepath) {
		
	}
	public static void createTestCity() {
		int city_num = 8;
		
		// 1. 도시 생성
		City.cityList.clear();
		//policyApply.pa.clear();
		
		
		new policyApply(new City(350, 200, 220000,10));
		new policyApply(new City(500, 300, 220000));
		new policyApply(new City(600, 500, 220000));
		new policyApply(new City(300, 800, 220000));
		new policyApply(new City(900, 600, 220000));
		
		new policyApply(new City(750, 750, 220000));
		new policyApply(new City(800, 900, 220000));
		new policyApply(new City(800, 200, 220000));
		//new policyApply(new City(1000, 100, 220000));
		
		for (int i=0; i<city_num; i++) {
			/*
			if (i==0)
				new policyApply(new City(i*100+100, i*100+100, 220000, 10));
			else
				new policyApply(new City(i*100+100, i*100+100, 220000));
			*/
			City.cityList.get(i).setGold(10);
			City.cityList.get(i).setTrustIndex(100);
		}
		
		
		
		// 2. 도시 연결 관리
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
		// 3. 바이러스 설정
		Virus.setVirus(0.01, 0.00001);
		
		// 4. 백신 초기화
		Vaccine.restVaccine(City.cityList);
		
		
		
		
	}
	public static void readPolicyData() {
		// CSV 파일로 부터 Policy 정보를 가져온다.
		int policy_num = 37; // 전체 정책 수
		policy.policy_list = CSVcontrol.getPolicyList(policy_num);
		return;
	}
	
	public static String[] gameProgress() {
		// 감염 진행, 인구 유동, 백신 연구, 백신 투여, 백신 생산
		// 진행 후 log에 넣을 string 배열 리턴 
		int cities = City.cityList.size();	// 전체 도시 수
		
		//0. 정책 효과 적용
		applyPolicy();
		
		for (int i=0; i<cities; i++) {
			
		//1. 바이러스 전파
			City.cityList.get(i).progressCityVirus();
		//1.a 백신 투여
			City.cityList.get(i).progressCityVaccine();
		//2. 인구 유동
			for (int j=0; j<cities; j++) {
				if (i!=j) {
					City.cityList.get(i).progessCityFlow(j);
				}
			}
		
		}
		//3. 백신 개발 & 생산 & 분배
		Vaccine.progressResearch();
		Vaccine.progressProduction();
		Vaccine.vaccineDestribute();
		
		//3. 로그 반영
		// 인구 현황 계산 + 백신 진행도 
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
		
		
		//4. 종료 여부 확인
		
		
		//5. 상태 저장
		
		
		return input_log;
	}
	
	private static void applyPolicy() {	
		// 정책 적용
		int[][] data_change = policyApply.getPAL();
		int cities = City.CityCount;
		
		for (int i=0; i<cities; i++) {
			Virus thisVi = City.cityList.get(i).getVi();
			thisVi.resetChanges();
			//* 1 : infection_rate
			thisVi.infectionChange((double)(data_change[i][0])*0.01);
			//* 2 : death_rate
			thisVi.deathChange((double)(data_change[i][1])*0.001);
			
			//* 3 : trust_index
			City.cityList.get(i).addTrustIndex(data_change[i][2]);
			
			//* 4 : turn_gold
			City.total_gold += data_change[i][3];
			
			//* 5 : city_flow
			City.cityList.get(i).setFlowRate();
			City.cityList.get(i).chageFlowRate((double)(data_change[i][4])*0.1);
			//* 6 : population youth_rate
			//* 7 : population middle_rate
			//* 8 : population old_rate
			//* 9 : vaccine distribute
			//* 10 : capacity
			City.cityList.get(i).addCapacity(data_change[i][9]);
			//* 11 : selection rate
			thisVi.selectionChange((double)(data_change[i][10]));
			//* 12
			//* 13
			//* 14
		}
		 //* 15 : research progress
		 //* 16 : research step
		 //* 17 : vaccine production
		 //* 18 : mask_production		
	}
}


