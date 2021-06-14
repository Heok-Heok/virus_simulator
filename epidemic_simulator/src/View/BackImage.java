package View;

//package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Model.*;

public class BackImage extends JFrame implements ActionListener {

	MyButton[] BTS;
	JLabel city_label;
	JLabel gold_label;
	JLabel trust_label;
	JLabel infection_rate;
	
	int current_index=1;
	public policyList listview;
	
	
	public BackImage() {
		
		
		setTitle("Project V");
		Container c = getContentPane();
		c.setLayout(null);
		
		/*
		MyButton b = new MyButton("City 1");
		//b.setOpaque(true);
		b.setBackground(Color.CYAN);
		b.setSize(100, 50);
		b.setLocation(500, 100);
		c.add(b);
		
		MyButton b2 = new MyButton("City 2");
		b2.setBackground(Color.CYAN);
		b2.setSize(100, 50);
		b2.setLocation(800, 200);
		c.add(b2);

		MyButton b3 = new MyButton("City 3");
		b3.setBackground(Color.CYAN);
		b3.setSize(100,50);
		b3.setLocation(450,600);
		c.add(b3);
		*/
		
		MyButton[] BTS = new MyButton[6];
		for (int i=0; i<6; i++) {
			BTS[i] = new MyButton("City" + (i+1));
			BTS[i].setBackground(Color.CYAN);
			BTS[i].setSize(100, 50);
			BTS[i].setLocation(100*i, 100*i);
			BTS[i].addActionListener(this);
			c.add(BTS[i]);
		}
		
		
		
		
		//test
		//b.addActionListener(this);
		//b2.addActionListener(this);
		//b3.addActionListener(this);
		//test
		
		
		
		
		
		JLabel l1 = new JLabel("papa");
		
		//setContentPane(new JLabel(new ImageIcon("D:\\map.png")));
      //setLayout(new FlowLayout());
      l1 = new JLabel(new ImageIcon("D:\\map.png"));

      
      setSize(1200,1081);
		l1.setSize(1200,1081);
		l1.setLocation(20,20);
		c.add(l1);
		
		setSize(1400, 1200);
		setVisible(true);
		
	}
	public BackImage(int city_num) {
		
		
		setTitle("Project V");
		Container c = getContentPane();
		c.setLayout(null);
		JPanel panel = new JPanel();
		
		city_label = new JLabel();
		listview = new policyList();
		listview.setVisible(false);
		
		gold_label = new JLabel("Gold");
		trust_label = new JLabel("Trust");
		infection_rate = new JLabel("Infection");
		
		gold_label.setLocation(1250, 150);
		gold_label.setSize(50,50);
		//gold_label.setBackground(new Color(255,255,0));
		trust_label.setLocation(1250, 250);
		trust_label.setSize(50,50);
		//trust_label.setBackground(new Color(0,0,255));
		infection_rate.setLocation(1250, 350);
		infection_rate.setSize(50,50);
		//infection_rate.setBackground(new Color(255,0,0));
		
		c.add(gold_label);
		c.add(trust_label);
		c.add(infection_rate);
		//panel.add(gold_label);
		
		
		//this.add(panel);
		
		
		BTS = new MyButton[city_num];
		for (int i=0; i<city_num; i++) {
			City ci = City.cityList.get(i);
			BTS[i] = new MyButton("City" + (i+1));
			BTS[i].setBackground(Color.BLUE);
			BTS[i].setSize(100, 50);
			BTS[i].setLocation(ci.getx(), ci.gety());
			BTS[i].addActionListener(this);
			c.add(BTS[i]);
		}
		
		
		
		JLabel l1 = new JLabel("papa");
		
		//setContentPane(new JLabel(new ImageIcon("D:\\map.png")));
      //setLayout(new FlowLayout());
      l1 = new JLabel(new ImageIcon("D:\\map.png"));

      
      setSize(1200,1081);
		l1.setSize(1200,1081);
		l1.setLocation(20,20);
		c.add(l1);
		
		setSize(1400, 1200);
		setVisible(true);
		
	}
	
	public void setButtonColor() {
		int sum;
		int infection;
		double color_rate;
		//int color_index;
		
		int[] rate;
		
		for (int i=0; i<BTS.length; i++) {
			rate = City.cityList.get(i).getPeople().getPeople();
			sum = rate[0];
			infection = rate[2];
			sum = sum / 50;
			color_rate = ((double)(infection))/sum; 
			if (color_rate>1) {
				color_rate = 1;
			}
			//color_index = 255()
			//System.out.println(color_rate+" "+(int)(255*(1-color_rate)));
			BTS[i].setBackground(new Color(255, (int)(255*(1-color_rate)),(int)(255*(1-color_rate))));
		}
	}
	private String getCityInfo(int num) {
		num -= 1;
		String info = "<html> 도시명 : City " + (num+1) +"<br>";
		
		City ci = City.cityList.get(num);
		People p_data = ci.getPeople();
		
		int[] population = p_data.getPeople();
		double[] age = p_data.getRate();
		
		info += "<br>";
		info += "인구 정보 <br>";
		info += "전체 인구 수 : " + population[0]+ " 명<br>";
		info += "정상 인구 수 : " + (population[1]+population[3])+ " 명<br>";
		info += "감염 인구 수 : " + population[2]+ " 명<br>";
		info += "사망 인구 수 : " + population[4]+ " 명<br><br>";
		
		info += "연령대 <br>";
		info += "유년 : " + age[0] + "<br>";
		info += "중년 : " + age[1] + "<br>";
		info += "노년 : " + age[2] + "<br>";
		
		info += "<br>지지율  : "+ ci.getTrustIndex()+ "<br>";
		info += "<br>턴 골드 획득량  : "+ ci.getTurnGold()+ "<br>";
		info += "<br>격리 시설 수용 인원  : "+ ci.getCapacity()+ "<br>";
		info += "<br>타 도시 인구 유동량  : "+ ci.getCityFlow()+ "<br>";
		info += "<br>";
		
		info += "</html>";
		return info;
	}
	
	class MyButton extends JButton{
		public MyButton(String s) {
			super(s);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			//g.setColor(Color.RED);
			//g.drawOval(0, 0, this.getWidth()-1, this.getHeight()-1);
			
		
			
			
		}
	}
	



	
	/*
	public static void main(String[] args) {
		
		new BackImage();
		
	}
	*/
	public static void openBackImage() {
		new BackImage();
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		// TODO Auto-generated method stub
		
		if(action.equals("정책 리스트")) {
			//listview = new policyList();
			City ci = City.cityList.get(this.current_index-1);
			//listview = new policyList();
			listview.getPolicyName(ci, 0);
			listview.repaint();
			listview.setVisible(true);
			return;
		}
		
		//if(action.equals("City 1")) {
			//System.out.println("Display City1"); //create new window
			//JFrame frame = new JFrame("City 1");
			String[] set = action.split("y");
			int num = Integer.parseInt(set[1]);
			this.current_index = num;
			
			JFrame frame = new JFrame(action);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
			panel.setOpaque(true);
			
			//needs to display data that is always changing ie total Popluation
			//city_label = new JLabel();// = new JLabel( num +" : Total Population: "); // + variable to display variable
			city_label.setText(getCityInfo(num));
			panel.add(city_label);
			city_label.setLocation(250,250);
			city_label.setVisible(true);
			city_label.setOpaque(true);
			
			JButton call_policy = new JButton("정책 리스트");
			call_policy.addActionListener(this);
			
			panel.add(call_policy);
		
          JPanel inputpanel = new JPanel();
          inputpanel.setLayout(new FlowLayout());

          panel.add(inputpanel);
          frame.getContentPane().add(BorderLayout.CENTER, panel);
          frame.pack();
          frame.setLocationByPlatform(true);
          frame.setVisible(true);
          frame.setSize(500,500);
          frame.setResizable(false);
			
          //while(true) {
          //  city_label.setText(getCityInfo(num));
          //}
		//}
		
	}
	
	public void update_city_info() {
		this.city_label.setText(getCityInfo(this.current_index));
	}
	public void update_MainStatus() {
		int num;
		num = (int)(Math.random()*1500);
		this.gold_label.setText("<html>Gold : <br>" + num + "</html>");
		this.trust_label.setText("<html>Trust : <br>" + 100 + "</html>");
		
		int sum = 0;
		int infected = 0;
		int [] p_data;
		
		for (int i=0; i<City.cityList.size(); i++) {
			p_data = City.cityList.get(i).getPeople().getPeople();
			sum += p_data[0];
			infected += p_data[2]+p_data[4];
		}
		double rate;
		
		rate = (double)(infected) / sum;
		rate = Math.round(rate*100*100)/100;
		
		this.infection_rate.setText("<html>Infection: <br>" + rate + "%</html>");
		System.out.println(rate);
	}

}


