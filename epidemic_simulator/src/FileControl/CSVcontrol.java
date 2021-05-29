package FileControl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	
	public static int[][] readCSV(int column, int row){ // 별도의 파일 클래스를 열 필요가 없다.
		 int[][] indat = new int[row][column];
	        
	        try {
	            // csv 데이타 파일
	            //File csv = new File("d:\\data\\Regression_ver20130401.csv");
	            File csv = new File(filePath);
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            row =0; 
	            int i;
	 
	            while ((line = br.readLine()) != null) {
	                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
	                String[] token = line.split(",");
	                for(i=0;i<column;i++) {
	                	indat[row][i] = Integer.parseInt(token[i]);
	                	//System.out.println(row + "," + i + "   : " + indat[row][i]);
	                }
	                
	                // CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
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
	
	public static String[][] readCSV_string(int column, int row){ // 별도의 파일 클래스를 열 필요가 없다.
		 String [][] indat = new String[row][column];
	        
	        try {
	            // csv 데이타 파일
	            //File csv = new File("d:\\data\\Regression_ver20130401.csv");
	            File csv = new File(filePath);
	            BufferedReader br = new BufferedReader(new FileReader(csv));
	            String line = "";
	            row =0; 
	            int i;
	 
	            while ((line = br.readLine()) != null) {
	                // -1 옵션은 마지막 "," 이후 빈 공백도 읽기 위한 옵션
	                String[] token = line.split(",");
	                for(i=0;i<column;i++) {
	                	indat[row][i] = token[i];
	                	//System.out.println(row + "," + i + "   : " + indat[row][i]);
	                }
	                
	                // CSV에서 읽어 배열에 옮긴 자료 확인하기 위한 출력
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
	
}
