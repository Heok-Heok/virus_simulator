package tail;

import javax.swing.*;
import java.awt.*;

public class painting extends JFrame {
	static final int X=1800,Y=1000;
	Color white = new Color(255, 255, 255);
	Color black = new Color(0, 0, 0);
	Color blue = new Color(0, 0, 255);

	public painting() {
		super("태일아");
		this.setBounds(0, 0, X, Y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/*public void paint(Graphics g) {
		//g.setColor(white);
		//g.fillRect(0, 0, X, Y);
		//g.setColor(black);
		//g.fillOval(100, 700, 8, 8);
		//g.fillOval(100, 200, 8, 8);

		for(person p : person.p) {
			int x = p.getx();
			int y = p.gety();
			g.setColor(black);
			g.fillOval(x, y, p.getSize(), p.getSize());
			g.setColor(blue);
			g.fillOval(p.getDesX(), p.getDesY(), p.getSize()/2, p.getSize()/2);
			g.drawLine(x, y, p.getDesX(), p.getDesY());
			System.out.println("1");
		}
	}*/
	
	Image buffImage;
	Graphics buffg;
	
	public void paint(Graphics g) {
		if(buffg == null) {
			buffImage = createImage(X, Y);
			
			if(buffImage == null) {
				System.out.println("버프이미지오류1");
			}
			else {
				buffg = buffImage.getGraphics();
			}
		}
		buffg.setColor(white);
		buffg.fillRect(0, 0, X, Y);
		buffg.setColor(black);
		for(person p : person.p) {
			int x = p.getx();
			int y = p.gety();
			buffg.setColor(p.getColor());
			buffg.fillOval(x, y, p.getSize(), p.getSize());
			buffg.drawOval(x-p.getRange()/2, y-p.getRange()/2, p.getSize()+p.getRange(), p.getSize()+p.getRange());
			buffg.setColor(blue);
			buffg.fillOval(p.getDesX(), p.getDesY(), p.getSize()/2, p.getSize()/2);
			//buffg.drawLine(x+p.getSize()/2, y+p.getSize()/2, x+p.getX()*2+p.getSize()/2, y+p.getY()*2+p.getSize()/2);
		}
		g.drawImage(buffImage, 0, 0, this);
	}
	
}