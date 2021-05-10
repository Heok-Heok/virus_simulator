package tail;

import java.awt.Graphics;
import java.util.Random;

import screen.*;

public class main {
	public static void main(String[] args) throws InterruptedException {
		int n = 20;
		int sizeX = 800;
		int sizeY = 800;
		for(int popu = 1; popu < n; popu++) {
			new person(sizeX/popu, sizeY/popu, 10);
		}
		
		painting w1 = new painting();
		
		//Graphics g = w1.getGraphics();
		
		Random rand = new Random();
		double[] gene = new double[10];
		
		int po1=0;
		int po2;
		for(int t4 = 0; t4<10; t4++) {
		for(int t3 = 0; t3<10; t3++) {
		for(int t2 = 0; t2<10; t2++) {
		for(int t = 0; t<5; t++) {
			System.out.println(t+t2+t3+t4);
			{
				person.gak[0] = person.gak[1]= t4*18-90;			
				person.gak[2] = t2*18-90;
				person.gak[3] = person.gak[2];
				person.gak[4] = t3*18-90;
				person.gak[5] = person.gak[4];
				person.gak[6] = t4*18-90;
				person.gak[7] = person.gak[6];
				person.gak[8] = person.gak[6];
				person.gak[9] = t+1;
			}
			person.gak[9] = rand.nextInt(10)+1;
			person.point=0;
			
			for(int i = 0; i<1000; i++) {
				w1.repaint();
				for(person p : person.p) {
					if(!p.des) {
						int x= rand.nextInt(sizeX)+100;
						int y= rand.nextInt(sizeY)+100;
						p.navigate(x, y);
					}
				}
				for(person p : person.p) {
					p.moving();
				}
				
				//Thread.sleep(0);		//18, 72, 54, 18, 7
			}
			po2=person.point;
			if(po1<po2) {
				po1=po2;
				System.out.println(po1);
				for(int index = 0; index<10; index++) {
					gene[index]=person.gak[index];
				}
			}
			for(int popu = 1; popu < n; popu++) {
				person.p.get(popu-1).blink(sizeX/popu, sizeY/popu);
				person.p.get(popu-1).des=false;
			}
		}}}}
		
		for(double g : gene) {
			System.out.print(g);
		}
		for(int index = 0; index<10; index++) {
			person.gak[index]=gene[index];
		}
		
		while(true) {
			w1.repaint();
			for(person p : person.p) {
				if(p.stop) {
					int x= rand.nextInt(sizeX)+100;
					int y= rand.nextInt(sizeY)+100;
					p.navigate(x, y);
				}
			}
			for(person p : person.p) {
				p.moving();
			}
			Thread.sleep(50);
		}
	}
}