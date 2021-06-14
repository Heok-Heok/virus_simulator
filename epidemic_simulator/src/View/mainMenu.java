package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class mainMenu extends JFrame implements MouseListener{

	private JButton newgame;
	private JButton loadgame;
	private JButton record;
	private JButton quit;
	private JLabel name;
	
	public mainMenu() {
		name = new JLabel("Project V");
		newgame = new JButton("NewGame");
		loadgame = new JButton("LoadGame");
		record = new JButton("Record");
		quit = new JButton("Quit");
		
		newgame.addMouseListener(this);
		loadgame.addMouseListener(this);
		record.addMouseListener(this);
		quit.addMouseListener(this);
		
		setTitle("mainMenu");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container menu = getContentPane();
		menu.setLayout(null);
		menu.setBackground(Color.BLACK);
		
		name.setSize(200, 100);
		name.setLocation(350, 10);
		name.setFont(new Font("Arial", Font.ITALIC, 40));
		name.setForeground(Color.RED);
		menu.add(name);
		
		newgame.setSize(200, 100);
		newgame.setLocation(350, 130);
		newgame.setFont(new Font("Arial", Font.ITALIC, 30));
		newgame.setForeground(Color.RED);
		newgame.setBackground(Color.WHITE);
		menu.add(newgame);
		
		loadgame.setSize(200, 100);
		loadgame.setLocation(350, 250);
		loadgame.setFont(new Font("Arial", Font.ITALIC, 30));
		loadgame.setForeground(Color.RED);
		loadgame.setBackground(Color.WHITE);
		menu.add(loadgame);
		
		record.setSize(200, 100);
		record.setLocation(350, 370);
		record.setFont(new Font("Arial", Font.ITALIC, 30));
		record.setForeground(Color.RED);
		record.setBackground(Color.WHITE);
		menu.add(record);
		
		quit.setSize(200, 100);
		quit.setLocation(350, 490);
		quit.setFont(new Font("Arial", Font.ITALIC, 30));
		quit.setForeground(Color.RED);
		quit.setBackground(Color.WHITE);
		menu.add(quit);
		
		setSize(1000,700);
		setVisible(true);
		
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == newgame) {
			newGame();
		}
		if(e.getSource() == loadgame) {
			loadGame();
		}
		if(e.getSource() == record) {
			record();
		}
		if(e.getSource() == quit) {
			System.exit(0);
		}
		
	}
	public void newGame(){
		//JlistTest frame=new JlistTest("JList Test");
		this.setVisible(false);
	}
	public void loadGame() {}
	public void record() {}
	public void Quit() {
		System.exit(0);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}