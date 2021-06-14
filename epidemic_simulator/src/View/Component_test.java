package View;

import javax.swing.*;
import java.awt.*;

public class Component_test extends JFrame {

	
	public Component_test() {
		setTitle("For test");
		Container c = getContentPane();
		c.setLayout(null);
		
		MyButton b = new MyButton("test1");
		//b.setOpaque(true);
		b.setBackground(Color.CYAN);
		b.setSize(100, 50);
		b.setLocation(100, 100);
		c.add(b);
		
		MyButton b2 = new MyButton("test2");
		b2.setBackground(Color.CYAN);
		b2.setSize(100, 50);
		b2.setLocation(120, 150);
		c.add(b2);

		JLabel l1 = new JLabel("papa");
		
		//setContentPane(new JLabel(new ImageIcon("D:\\map.png")));
        //setLayout(new FlowLayout());
		//l1 = new JLabel(new ImageIcon("D:\\map.png"));

        setSize(1200,1081);
		l1.setSize(1000,1000);
		l1.setLocation(20,20);
		c.add(l1);
		
		setSize(800, 800);
		setVisible(true);
		
		
		
	}
	
	class MyButton extends JButton{
		public MyButton(String s) {
			super(s);
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.RED);
			g.drawOval(0, 0, this.getWidth()-1, this.getHeight()-1);
		}
	}
	public static void main(String[] args) {
		new Component_test();

	}

}
