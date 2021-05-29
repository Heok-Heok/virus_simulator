package Model;


import java.awt.Component;
import FileControl.*;


import org.knowm.xchart.QuickChart;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.SwingWrapper;

public class Model_test {
	static int check_time = 8000;
	public static void main(String[] args) {
		
		//test1();
		test2();
		
	}
	
	static void test1() {
		//int i = Types.S;
		

				System.out.println(Types.S.getValue());
				
				Virus.setVirus(0.01, 0.00001);
				
				CityEx test_city = new CityEx();
				
				test_city.setCityPopulation(2200000);
				test_city.setCityInfection(10);
				
				double [] x_data;
				double [] y_data;
				int [] city_population;
				x_data = new double [check_time];
				y_data = new double [check_time];
				
				final XYChart chart = QuickChart.getChart("Infection Change", "Turns", "Population", "Infection", x_data, y_data);
				final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
				sw.displayChart();
				
				for (int i=0; i<check_time; i++) {
					System.out.print(i+ " ");
					
					test_city.cityProgress();
					city_population = test_city.getCityPoulation();
					x_data[i] = i;
					y_data[i] = city_population[2];
					chart.updateXYSeries("Infection", x_data, y_data, null);
					sw.repaintChart();
					if (i==2400) {
						test_city.vi.infectionChange(0);
					}
					if (i==5000) {
						test_city.vi.deathChange(10);
					}
					if (i == 500) {
						test_city.addCapacity(15000);
					}
				}
	}

	static void test2() {
		//int i = Types.S;
				int city_num = 5;

				System.out.println(Types.S.getValue());
				
				Virus.setVirus(0.02, 0.00001);
				
				// 도시들 생성
				CityEx test_cities[] = new CityEx [city_num];
				int city_flow[][] = new int [city_num][];
				for (int i =0; i< city_num; i++) {
					city_flow[i] = new int[city_num];
				}
				for (int i=0; i<city_num ; i++) {
					for(int j=0; j<city_num; j++) {
						if (i!=j) {
							city_flow[i][j] = 0;
							if (i-j == 1 || j-i == 1 ) {
								city_flow[i][j] = 5000;
							}
						}
						else {
							city_flow[i][j] = 0;
						}
						System.out.print(city_flow[i][j] + " ");
					}
					System.out.println();
				}
				
				for (int i=0 ; i<city_num; i++) {
					test_cities[i] = new CityEx();
					test_cities[i].setCityPopulation(220000);
				}
				
				test_cities[0].setCityInfection(10);
				
				//입출력 로그 배열
				int [] city_population = new int [5*city_num];
				int [] sub_int;
				int capacity = 0;
				
				for (int i=0 ; i<city_num; i++) {
					sub_int = test_cities[i].getCityPoulation();
					System.out.print(i + " ");
					for (int j=0; j<5; j++) {
						System.out.print(sub_int[j] + " ");
					}
					System.out.println();
				}
				
				// 실행
				CSVcontrol.openWriteFile();
				
				for (int i=0; i<check_time; i++) {
					//System.out.print(i+ " ");
					// Virus+Flow progress
					for (int j=0; j<city_num; j++) {
						test_cities[j].population.progressVirus(test_cities[j].vi, capacity);
						for(int k=0; k<city_num; k++) {
							if (k!=j) {
								int tmp;
								tmp = test_cities[j].population.FlowOut(test_cities[k].population, city_flow[j][k]);
								System.out.print("("+j +","+ k + "->"+ tmp+")");
							}
						}
						System.out.println();
					}
					
					for (int j=0; j<city_num; j++) {
						sub_int = test_cities[j].getCityPoulation();
						for(int k=0; k<5; k++) {
							city_population[j*5+k] = sub_int[k]; 
							if (k==0) System.out.print(sub_int[k] + "  ");
								
						}
						
					}
					System.out.println();
					CSVcontrol.addArray(i, city_population);

				}
				CSVcontrol.closeWriteFile();
	}

}
