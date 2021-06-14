package FileControl;

import org.json.simple.JSONObject; // JSON객체 생성
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser; // JSON객체 파싱
import org.json.simple.parser.ParseException;


import java.util.ArrayList;
import java.util.LinkedList;

import Model.*;


public class JsonControl {
	@SuppressWarnings("unchecked")
	public static String city2json(ArrayList<City> cities) {
		String Json;
		JSONObject whole_city = new JSONObject();
		JSONArray citylist = new JSONArray();
		
		// City static class 정보 저장
		JSONObject static_data = new JSONObject();
		
		// 1-1 전체 도시 수, 마스크 생산 여부
		static_data.put("CityCount", City.CityCount);
		static_data.put("mask_production", City.mask_production);
		
		// 1-2. 도시 연결 정보
		JSONArray city_connection = new JSONArray();
			for (int i=0; i<cities.size(); i++) {
				JSONArray sub_connection= new JSONArray();
				for (int j=0; j<cities.size(); j++) {
					sub_connection.add(j, City.city_conn[i][j]);
				}
				city_connection.add(i, sub_connection);
			}
		static_data.put("connection", city_connection);
		
		// 1-3 바이러스 정보
		static_data.put("Infection_rate", Virus.getStaticInfection());
		static_data.put("Death_rate", Virus.getStaticDeath());
		
		// 1-4 백신 정보
		JSONObject vaccine_data = new JSONObject();
		vaccine_data.put("research_progress", Vaccine.getProgress());
		vaccine_data.put("research_speed", Vaccine.getSpeed());
		vaccine_data.put("current_step", Vaccine.getStep());
		vaccine_data.put("vaccine_stock", Vaccine.getVstock());
		
		JSONArray city_vaccine_rate= new JSONArray();
		JSONArray city_vaccine= new JSONArray();
		double[] cv_rate = Vaccine.getVaccineCityRate();
		int[] cv = Vaccine.getVaccineCity();
		for (int i=0; i<cities.size(); i++) {
			city_vaccine_rate.add(cv_rate[i]);
			city_vaccine.add(cv[i]);
		}
		vaccine_data.put("city_vaccine_rate", city_vaccine_rate);
		vaccine_data.put("city_vaccine", city_vaccine);
		static_data.put("vaccine_data", vaccine_data);
		
		
		whole_city.put("static_data", static_data);
		
		
		JSONObject city_object;
		JSONArray policy_object;
		JSONObject people_object; 
		JSONObject virus_object; 
		//JSONArray people_array;
		
		for (int i=0; i<cities.size(); i++) {
			
			// 1. 도시 정보
			// city_num, x, y
			city_object = new JSONObject();
			city_object.put("city_num", cities.get(i).getNum());
			city_object.put("x", cities.get(i).getx());
			city_object.put("y", cities.get(i).gety());
			city_object.put("trust", cities.get(i).getTrustIndex());
			city_object.put("capacity", cities.get(i).getCapacity());
			city_object.put("gold", cities.get(i).getTurnGold());
			
			// 2. 정책 정보
			/*
			policy_object = new JSONArray();
			LinkedList<policy> policy_list = cities.get(i).getPolicy();
			policy_list.
			*/
			
			// 3. 인구 정보
			people_object = new JSONObject();
			JSONArray people_seir = new JSONArray();
			JSONArray people_age = new JSONArray();
			JSONArray people_dElist = new JSONArray();
			int [] seir = cities.get(i).getPeople().getSEIR();
			double [] age = cities.get(i).getPeople().getRate();
			int [] dElist = cities.get(i).getPeople().getdElist();
				//3.1 SEIR 현황
			for (int j =0; j<seir.length; j++) {
				people_seir.add(j, seir[j]);
			}
				//3.2  연령대
			for (int j =0; j<age.length; j++) {
				people_age.add(j, age[j]);
			}
				//3.3 dE_list
			for (int j =0; j<dElist.length; j++) {
				people_dElist.add(j, dElist[j]);
			}
			people_object.put("seir", people_seir);
			people_object.put("age", people_age);
			people_object.put("dE_list", people_dElist);
			city_object.put("People", people_object);
			
			// 4. 바이러스 정보
			virus_object = new JSONObject();
			virus_object.put("infection_change", cities.get(i).getVi().getInfectionChange());
			virus_object.put("death_rate", cities.get(i).getVi().getDeatChange());
			city_object.put("Virus", virus_object);
			
			citylist.add(i, city_object);
		}
		whole_city.put("city_list",citylist);
		
		
				
		Json = whole_city.toJSONString();
		
		
			
		return Json;
	}
}
