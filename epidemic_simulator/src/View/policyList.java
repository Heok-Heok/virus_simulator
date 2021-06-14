package View;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.*;

import Model.*;
import FileControl.*;



public class policyList extends JFrame{
    String[] non_policy={"��å ���� �ȵȰ͵�", "���޺�����"};
    String[] on_policy= {"����Ȱ͵�", "�;ƾƾƾƾ�"};
    ArrayList<Integer> selected_index = new ArrayList<Integer>();
    ArrayList<Integer> nonselected_index= new ArrayList<Integer>();
    
    JList npList;
    JList opList;
    JLabel policy_info;
    JButton ApplyButton;
    JButton DisableButton;
    
    private City ci;
    
    int mode;
    
    public policyList(){
        this.setTitle("��å ����Ʈ");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        
        npList = new JList(non_policy);
        this.add(new JScrollPane(npList));
        npList.addListSelectionListener(new JListHandler());
        
        opList = new JList(on_policy);
        this.add(new JScrollPane(opList));
        opList.addListSelectionListener(new JListHandler());
        
        this.ApplyButton = new JButton("����");
        this.ApplyButton.addMouseListener(new ButtonHandler());
        this.add(this.ApplyButton);
        this.DisableButton = new JButton("����");
        this.DisableButton.addMouseListener(new ButtonHandler());
        this.add(this.DisableButton);
        
        policy_info = new JLabel("information");
        this.add(policy_info);
        
        this.setLocationRelativeTo(null);
        this.setSize(500,600);
        this.setVisible(true);
    }
    public void getPolicyName(City ci,int type) {
    	this.ci = ci;
    	getPolicyList(ci, type);
    	this.non_policy = new String[this.nonselected_index.size()];
    	this.on_policy = new String[this.selected_index.size()];
    	
    	int index_num;
    	
    	for (int i=0; i<this.non_policy.length; i++) {
    		index_num = this.nonselected_index.get(i);
    		this.non_policy[i] = policy.policy_list[index_num].getPolicyName();
    	}
    	for (int i=0; i<this.on_policy.length; i++) {
    		index_num = this.selected_index.get(i);
    		this.on_policy[i] = policy.policy_list[index_num].getPolicyName();
    	}
    	
    	this.npList.setListData(this.non_policy);
    	this.opList.setListData(this.on_policy);
    	//repaint();
    }
    
    private void getPolicyList(City ci, int type) {
    	// ����� ��å�� �������� ���� ��å�� ����
    	// type -> 0:����/1:����/2:���/3;�̺�Ʈ
    	int [][] list = policy.getRangeList();
    	this.nonselected_index.clear();
    	this.selected_index.clear();
    	
    	for (int i=0; i<list[type].length; i++) {
    		this.nonselected_index.add(list[type][i]);
    	}
    	
    	LinkedList<policy> policy_list= ci.getPolicy();
    	int list_size = policy_list.size();
    	int num;
    	
    	for (int i=0; i<list_size; i++) {
    		num = policy_list.get(i).getPolicyIndex();
    		this.nonselected_index.remove(num);
    		this.selected_index.add(num);
    	}
    }
    private class ButtonHandler implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			//System.out.println("hi "+ mode + " city : "+ci.getNum());
			//policy p_data;
			String policy_name;
			int index;
			if(e.getSource().equals(ApplyButton)) {
				System.out.println("yo");
				if (mode == 0) {
					
					policy_name = (String) npList.getSelectedValue();
					System.out.println(policy_name);
					index = policy.searchPolicyString(policy_name);
					ci.addPolicy(policy.policy_list[index]);
					getPolicyName(ci,0);
					//repaint();
				}
			}
			else if(e.getSource().equals("����")) {
				if (mode == 1) {
					
				}
			}
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    private class JListHandler implements ListSelectionListener{
    	public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) {    //�̰� ������ mouse ������, ���� ���� �ѹ��� ȣ��Ǽ� �� �ι� ȣ��
                //System.out.println("selected :"+npList.getSelectedValue());
            	String policy_name;
            	if(e.getSource().equals(npList)) {
            		mode = 0;
            		policy_name = (String) npList.getSelectedValue();
            	}
            	else {
            		mode = 1;
            		policy_name = (String) opList.getSelectedValue();
            	}
            	int index = policy.searchPolicyString(policy_name);
            	
            	String info = "<html>��å �� : ";
            	info = info + policy_name +"<br>";
            	info = info + "��å ���� : " + policy.policy_list[index].getPolicyType()+"<br>";
            	info = info + "�䱸 ��� �� : "+ policy.policy_list[index].getRequireGold() +"<br>";
            	info = info + "��å ���� : " +policy.policy_list[index].getPolicyExplain()+"<br>";
            	info = info + "TMI : " +policy.policy_list[index].getPolicyTMI()+"<br>";
            	info = info + "</html>";
            	policy_info.setText(info);
            }
        }
    }
}