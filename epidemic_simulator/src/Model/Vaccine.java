package Model;
import java.util.ArrayList;

public class Vaccine {
	private static double research_progress;	// ���� ���൵ max = 1
	private static double research_speed;		// ���� ���� �ӵ� 
	private static int current_step;			// ���� ���� �ܰ� 
	static final double[] RESEARCH_STEP = {0.3, 0.6, 1};	// �ܰ� �� ���� ���൵ 
	
	private static int vaccine_stock;			// ��� ������
	private static int vaccine_production;		// ��� ���귮
	
	private static double[] vaccine_city_rate;			// ���ú� ��� �й� ����
	public static int[] vaccine_city;					// ���ú�  ���� ��� �й跮 
	
	
	public static void restVaccine(ArrayList<City> list) {
		research_progress = 0;
		research_speed = 0;
		current_step = -1;
		vaccine_stock = 0;
		vaccine_production = 0;
		vaccine_city_rate = new double[list.size()];
		vaccine_city = new int[list.size()];
		
		for (int i=0; i< list.size(); i++) {
			vaccine_city_rate[i] = 0;
			vaccine_city[i] = 0;
		}
		
	}
	public static double getProgress() {
		return research_progress;
	}
	public static double getSpeed() {
		return research_speed;
	}
	public static int getVstock() {
		return vaccine_stock;
	}
	public static double[] getVaccineCityRate() {
		return vaccine_city_rate;
	}
	public static int[] getVaccineCity() {
		return vaccine_city;
	}
	
	public static void setSpeed(double speed) {
		research_speed = speed;
	}
	public static void setStep(int step) {
		current_step = step;
	}
	public static double progressResearch() {
		// ��� ���� ����
		
		if (current_step < 0 || current_step > 3)
			return research_progress;
		
		research_progress += research_speed;
		if (research_progress > RESEARCH_STEP[current_step]) {
			research_progress = RESEARCH_STEP[current_step];
			setSpeed(0);
		}
		return research_progress;
	}
	public static void setProduction(int production) {
		vaccine_production = production;
	}
	
	public static void setCityRate(int index, double rate) {
		vaccine_city_rate[index] = rate;
	}
	public static void setCityVaccine(int index, int vacc){
		vaccine_city[index] = vacc;
	}
	public static int progressProduction() {
		// ��� ���� ����
		if (research_progress != 1) {
			return vaccine_stock;
		}
		vaccine_stock += vaccine_production;
		return vaccine_stock;
	}
	public static int[] vaccineDestribute() {
		// ��� �й�
		double rate_sum = 0;	// ��� �й� ���� �հ�
		int v_sum = 0;			// �� �й� ��� ��
		
		rate_sum = getSum(vaccine_city_rate);
		
		if (rate_sum == 0) {
			for (int i=0 ; i<vaccine_city_rate.length; i++) {
				vaccine_city[i] += 0;
			}
		}
		for (int i=0 ; i<vaccine_city_rate.length; i++) {
			vaccine_city[i] += (int) (vaccine_stock * vaccine_city_rate[i] / rate_sum);
		}
		
		v_sum = getSum(vaccine_city);
		vaccine_stock -= v_sum;
		
		return vaccine_city;
	}
	private static int getSum(int[] array) {
		int sum = 0;
		for (int i=0 ; i<array.length; i++) {
			sum += array[i];
		}
		return sum;
	}
	private static double getSum(double[] array) {
		double sum = 0;
		for (int i=0 ; i<array.length; i++) {
			sum += array[i];
		}
		return sum;
	}
}
