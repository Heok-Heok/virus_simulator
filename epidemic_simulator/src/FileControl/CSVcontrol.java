package FileControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Model.policy;

public class CSVcontrol {
	static final String filePath = "D:\\demo.csv";
	static File file;
	static BufferedWriter bw = null;

	public static void openWriteFile(){
		try {
			file = new File(filePath);
			bw = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void closeWriteFile() {
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void addArray(int i, int data[]) {
		//file = null;
		//bw = null;
		String NEWLINE = System.lineSeparator();
		
		try {
			
			bw.write(Integer.toString(i));
			for (int j : data) {
				bw.write("," + j);
			}
			bw.write(NEWLINE);
			
			bw.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	public static void addArray(int i, String data[]) {
		//file = null;
		//bw = null;
		String NEWLINE = System.lineSeparator();
		
		try {
			
			bw.write(Integer.toString(i));
			for (String j : data) {
				bw.write("," + j);
			}
			bw.write(NEWLINE);
			
			bw.flush();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
				
	}
	
	public static int[][] readCSV(int column, int row){ // ������ ���� Ŭ������ �� �ʿ䰡 ����.
		 int[][] indat = new int[row][column];
	        
	        try {
	            // csv ����Ÿ ����
	            //File csv = new File("d:\\data\\Regression_ver20130401.csv");
	            File csv = new File(filePath);
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            row =0; 
	            int i;
	 
	            while ((line = br.readLine()) != null) {
	                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
	                String[] token = line.split(",");
	                for(i=0;i<column;i++) {
	                	indat[row][i] = Integer.parseInt(token[i]);
	                	//System.out.println(row + "," + i + "   : " + indat[row][i]);
	                }
	                
	                // CSV���� �о� �迭�� �ű� �ڷ� Ȯ���ϱ� ���� ���
	                //for(i=0;i<6;i++)    System.out.print(indat[row][i] + ",");
	                //System.out.println("");
	                
	                row++;
	            }
	            br.close();
	 
	        } 
	        catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        return indat;

	}
	
	public static String[][] readCSV_string(int column, int row){ // ������ ���� Ŭ������ �� �ʿ䰡 ����.
		 String [][] indat = new String[row][column];
	        
	        try {
	            // csv ����Ÿ ����
	            //File csv = new File("d:\\data\\Regression_ver20130401.csv");
	            File csv = new File(filePath);
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            row =0; 
	            int i;
	 
	            while ((line = br.readLine()) != null) {
	                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
	                String[] token = line.split(",");
	                for(i=0;i<column;i++) {
	                	indat[row][i] = token[i];
	                	//System.out.println(row + "," + i + "   : " + indat[row][i]);
	                }
	                
	                // CSV���� �о� �迭�� �ű� �ڷ� Ȯ���ϱ� ���� ���
	                //for(i=0;i<6;i++)    System.out.print(indat[row][i] + ",");
	                //System.out.println("");
	                
	                row++;
	            }
	            br.close();
	 
	        } 
	        catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        return indat;

	}
	
	public static PolicyData readCSV_policy(int policy_num){ // csv ���Ͽ��� policy�� �о� �´�.
			String [][] indat = new String[policy_num][];
	        String csvPath = "D:\\policy_list.csv";
	        int row =0; 
	        
	        try {
	            // csv ����Ÿ ����
	            //File csv = new File("d:\\data\\Regression_ver20130401.csv");
	            File csv = new File(csvPath);
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            
	            //int i;
	 
	            while ((line = br.readLine()) != null) {
	                // -1 �ɼ��� ������ "," ���� �� ���鵵 �б� ���� �ɼ�
	            	if (row >= policy_num) {
	            		break;
	            	}
	                String[] token = line.split(",");
	                indat[row] = token;
	                
	                // CSV���� �о� �迭�� �ű� �ڷ� Ȯ���ϱ� ���� ���
	                //for(i=0;i<6;i++)    System.out.print(indat[row][i] + ",");
	                //System.out.println("");
	                
	                row++;
	            }
	            br.close();
	 
	        } 
	        catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } 
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        // String Int �κ� ���� ����
	        PolicyData result_data = new PolicyData(row);
	        
	        for(int i=0; i<indat.length; i++) {
	        	result_data.policy_information[i] = new String[4];
	        	result_data.policy_property[i] = new int[indat[i].length - 5];
	        	System.out.println(i + " !");
	        	result_data.policy_range[i] = Integer.parseInt(indat[i][0]);
	        	for(int j=1; j<5; j++) {
	        		result_data.policy_information[i][j-1] = indat[i][j];
	        	}
	        	for (int j=5; j<indat[i].length; j++) {
	        		result_data.policy_property[i][j-5] = Integer.parseInt(indat[i][j]);
	        		//System.out.print(result_data.policy_property[i][j-5] + " ");
	        	}
	        }
	        
	        return result_data;

	}
	public static policy[] getPolicyList(int policy_num) {
		PolicyData read_data = CSVcontrol.readCSV_policy(policy_num);
		policy [] policy_list = new policy[read_data.getNum()];
		
		String[][] policy_info = read_data.getPolicyInfromation();
		int[][] policy_property = read_data.getPolicyProperty();
		
		for (int i=0; i<read_data.getNum(); i++) {
			policy_list[i] = new policy(0,0, i, policy_info[i], policy_property[i]);
			policy_list[i].setRange(read_data.policy_range[i]);
			policy_list[i].printPolicyInfo();
		}
		policy.policy_list = policy_list;
		
		policy.splitByRange();
		
		return policy_list;
	}
}

class PolicyData{
	// Policy.csv�� ��� ������ �����ϴ� Ŭ����
	// 2���� String �迭��, 2���� int �迭�� ������ �ִ�.
	int policy_num;
	String [][] policy_information;
	int [][] policy_property;
	int [] policy_range;
	
	PolicyData(int policy_num){
		this.policy_num = policy_num;
		this.policy_information = new String[this.policy_num][];
		this.policy_property = new int [this.policy_num][];
		this.policy_range = new int [this.policy_num];
	}
	
	int getNum() {
		return this.policy_num;
	}
	String[][] getPolicyInfromation() {
		return this.policy_information;
	}
	int [][] getPolicyProperty(){
		return this.policy_property;
	}
	
	
}
