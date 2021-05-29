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
			city_object.put("x", cities.get(i).getNum());
			city_object.put("y", cities.get(i).getNum());
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
