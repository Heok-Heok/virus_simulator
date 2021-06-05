package DataControl;

import FileControl.CSVcontrol;

import Model.*;


public class CityControl {
	public static void createCity(String filepath) {
		
	}
	public static void createTestCity() {
		int city_num = 5;
		
		// 1. 도시 생성
		City.cityList.clear();
		//policyApply.pa.clear();
		
		for (int i=0; i<city_num; i++) {
			if (i==0)
				new policyApply(new City(i*100, i*100, 220000, 10));
			else
				new policyApply(new City(i*100, i*100, 220000));
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
		
		for (int i=0; i<cities; i++) {
		//0. 정책 효과 적용
		
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
}
