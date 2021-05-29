package FileControl;

import Model.*;
import DataControl.*;


public class TestFile {

	public static void main(String[] args) {
		json_test();
		System.out.println("Done!");
		
	}
	static void string_test() {
		CSVcontrol.openWriteFile();
		String [] s_array = new String[10];
		for (int i=0; i<10; i++) {
			s_array[i] = "한글 테스트";
			CSVcontrol.addArray(i, s_array);
		}
		String [][] readdata = CSVcontrol.readCSV_string(10, 10);
		for (int i=0; i<10; i++) {
			System.out.println(readdata[i][0]+" "+readdata[i][1]);
		}
		CSVcontrol.closeWriteFile();
	}
	static void int_test() {
		CSVcontrol.openWriteFile();
		int [] t_array = new int [6];
		for (int i=1; i<20; i++) {
			for(int j=0; j<6; j++) {
				t_array[j] = i*j-1;
			}
			CSVcontrol.addArray(i, t_array);
		}
		CSVcontrol.closeWriteFile();
		
		int[][] readdata = CSVcontrol.readCSV(7, 20);
		
		for (int i=0; i<20; i++) {
			System.out.println(readdata[i][0]+" "+readdata[i][1]);
		}
	}
	static void json_test() {
		String str;
		
		CSVcontrol.openWriteFile();
		/*
		Virus.setVirus(0.02, 0.00001);
		for (int i=0; i<5; i++) {
			if (i==0)
				new City(i*100, i*100, 220000, 10);
			else
				new City(i*100, i*100, 220000);
		}
		String str;
		str = JsonControl.city2json(City.cityList);
		System.out.println(str);
		for (int i=0; i<780; i++) {
			test_run_city();
			str = JsonControl.city2json(City.cityList);
			System.out.println(str);
		}
		
		str = JsonControl.city2json(City.cityList);
		System.out.println(str);
		*/
		String input_log[];
		CityControl.createTestCity();
		
		// 시나리오 체크
		int capacity = 0;
		int r_level = -1;
		double r_speed = 0.005;
		int v_production = 100;
		double v_distribute = 0.2;
		
		for (int i=0; i< 50; i++) {
			input_log = CityControl.gameProgress();
			
			CSVcontrol.addArray(i, input_log);
			str = JsonControl.city2json(City.cityList);
			System.out.println(str);
			
			if ((i%500) == 0) {
				capacity += 100*(i/500)*(i/500);
			}
			if(i == 300) {
				r_level += 1;
			}
			if(i == 800) {
				r_level += 1;
				r_speed +=0.002;
			}
			if(i == 1000) {
				r_level += 1;
				Virus.setVirus(0.01, 0.001);
			}
			if(i == 1500) {
				//r_level += 1;
				v_production *= 10;
			}
			if(i == 2000) {
				//v_production *= 10;
				Virus.setVirus(0.01, 0.0015);
			}
			
			for (int j=0; j<City.cityList.size(); j++) {
				City.cityList.get(j).setCapacity(capacity);
				Vaccine.setCityRate(j, v_distribute);
			}
			Vaccine.setStep(r_level);
			Vaccine.setSpeed(r_speed);
			Vaccine.setProduction(v_production);
			
			
		}
		str = JsonControl.city2json(City.cityList);
		System.out.println(str);
	}
	static void test_run_city() {
		City current_city;
		City next_city;
		int capacity = 0;
		
		for(int i=0; i<City.cityList.size(); i++) {
			current_city = City.cityList.get(i);
			current_city.getPeople().progressVirus(current_city.getVi(), capacity);
			for(int j=0; j<City.cityList.size(); j++) {
				if (i!=j) {
					next_city = City.cityList.get(j);
					current_city.getPeople().FlowOut(next_city.getPeople(), 100);
				}
			}	
		}
	}
}
