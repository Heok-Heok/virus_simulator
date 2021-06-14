

package View;

import FileControl.*;
import Model.*;
import DataControl.*;

public class TestView {

	public static void main(String[] args) {
		p_listViewTest();
		
		
		
	}
	
	static void p_listViewTest(){
		CityControl.readPolicyData();
		CityControl.createTestCity();
		
		policyList listview = new policyList();
		
		
		City ci = City.cityList.get(0);
		ci.addPolicy(policy.policy_list[0]);
		ci.addPolicy(policy.policy_list[1]);
		listview.getPolicyName(ci, 0);
		listview.repaint();
	}
}
