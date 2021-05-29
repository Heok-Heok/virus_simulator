package Model;

public class policy {

	p_node head;
	p_node resent;
	int size=0;
	static p_node error = new p_node(999, 0);
	
	policy(int id, int val)
	{
		this.head = new p_node(id, val);
		resent = head;
	
	}
	
	void addNode(int index, int val)
	{
		p_node addN = new p_node(index, val);
		resent.next = addN;
		resent = addN;
		size++;
	
	}
	
	int getValue(int index)
	
	{
	
		p_node k = findNode(index);
		
		return k.value;
		
	}
	
	p_node findNode(int index)
	
	{
		
		p_node result = head;
		
		int id = 0;
		
		while(id != index)
		
		{
		
		result = result.next;
		
		if(result == null)
		
			return error;
			
			id = result.index;
		
		}
		
		return result;
	
	}
	
	policy(int policy_index)
	
	{
	
		//this.head = new p_node());
	
	}
	
	private void add_std_node()
	{
	
	}
	
}
	
class p_node{
	
		int index;
		int value;
		p_node next;
		
		p_node(int id)
		
		{
		
			this.index = id;
		
		}
		
		p_node(int id, int value)
		
		{
		
		this(id);
		
		this.value = value;
		
		}

}