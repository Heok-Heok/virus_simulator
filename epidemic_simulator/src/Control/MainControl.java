package Control;

import Model.*;
import View.*;

import javax.swing.JFrame;

import DataControl.*;
import FileControl.*;

public class MainControl {
	static BackImage main_map;
	static View.mainMenu main_menu;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		open_test();
		//MainMenu();
	}
	public static void open_test() {
		MultiThread[] threads = new MultiThread[2];
		//threads[0] = new MultiThread();
		
		createCity();
		main_map = callMainMap();
		
		while(true) {
			try {
				DataControl.CityControl.gameProgress();
				main_map.update_city_info();
				main_map.setButtonColor();
				main_map.update_MainStatus();
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void createCity() {
		DataControl.CityControl.createTestCity();
		DataControl.CityControl.readPolicyData();
	}
	public static BackImage callMainMap() {
		return new View.BackImage(City.cityList.size());
	}
	
	
	public static View.mainMenu MainMenu() {
		return new View.mainMenu();
	}
}
class MultiThread extends Thread
{
    MultiThread(){
    	
    }
    public void run(){
        try{
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}