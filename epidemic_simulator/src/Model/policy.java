package Model;

public class policy {

	// ���� �߰� �׸� ================
	public static policy[] policy_list;
	static int[][] range_list; 		// ��å����  ������� ���� ����
	
	private int policy_index;		// ��å index ��ȣ
	private String policy_name;		// ��å �̸�
	private String policy_explain;	// ��å ����
	private String policy_tmi;		// ��å TMI
	private String policy_type;		// ��å ����
	private int require_gold;		// �Һ� ��� ��
	private int range;				// ��å ���� ����  0:����, 1:����, 2:�ؿ�, 3:�̺�Ʈ
	
	
	//==========================
	
	p_node head;
	p_node resent;
	int size=0;
	static p_node error = new p_node(999, 0);
	
	policy(int id, int val)
	{
		this.head = new p_node(id, val);
		resent = head;
	
	}
	policy(int id, int val, int policy_index, String[] information ){
		this(id, val);
		this.policy_index = policy_index;
		this.policy_name =information[0];
		this.policy_explain = information[1];
		this.policy_tmi = information[2];
		this.policy_type = information[3];
	}
	public policy(int id, int val, int policy_index, String[] information, int[] property ){
		this(id, val, policy_index, information);
		
		this.require_gold = property[0];
		
		for (int i=1; i<property.length; i++) {
			this.addNode(i, property[i]);
			//System.out.print(this.resent.getValue() + " ");
		}
	}
	
	void addNode(int index, int val)
	{
		p_node addN = new p_node(index, val);
		this.resent.next = addN;
		this.resent = addN;
		this.size++;
	
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
	
	// ���� �߰� �� ���� =======================
	/*
	private int policy_index;
	private String policy_name;
	private String policy_explain;
	private String policy_tmi;
	private String policy_type;
	private int require_gold;
	*/
	public static void splitByRange() {
		int total = 4;
		int [] types = new int[total];
		for (int i=0; i<types.length;i++) {
			types[i] = 0;
		}
		for(int i=0; i<policy.policy_list.length; i++) {
			types[policy.policy_list[i].getRange()] += 1;
		}
		
		policy.range_list = new int [types.length][];
		for (int i=0; i<types.length;i++) {
			policy.range_list[i] = new int[types[i]];
			types[i] = 0;
		}
		
		for(int i=0; i<policy.policy_list.length; i++) {
			policy.range_list[policy_list[i].getRange()][types[policy_list[i].getRange()]] = i;
			types[policy_list[i].getRange()] += 1;
		}
	}
	
	public void setRange(int range) {
		this.range = range;
	}
	public int getRange() {
		return this.range;
	}
	public static int[][] getRangeList() {
		return policy.range_list;
	}
	
	public int getPolicyIndex() {
		return this.policy_index;
	}
	public String getPolicyName() {
		return this.policy_name;
	}
	public String getPolicyExplain() {
		return this.policy_explain;
	}
	public String getPolicyTMI() {
		return this.policy_tmi;
	}
	public int getRequireGold() {
		return this.require_gold;
	}
	public void printPolicyInfo() {
		System.out.println(this.getPolicyIndex() + " : "  + this.getPolicyName());
		System.out.println("gold : " + this.getRequireGold());
		System.out.println("explain : " + this.getPolicyExplain());
		System.out.println("TMI : " + this.getPolicyTMI());
		System.out.print("property : ");
		
		p_node check = this.head;
		
		while (check != null) {
			System.out.print(check.getValue() + " -> ");
			check = check.next;
		}
		System.out.println();
	}
	
	// ===================================
	
}
	
class p_node{
	
		int index;
		int value;
		p_node next;
		
		p_node(int id)
		
		{
		
			this.index = id;
			this.next = null;
		
		}
		
		p_node(int id, int value)
		
		{
			this(id);
			this.value = value;
		}
		
		int getValue() {
			return this.value; 
		}

}