//hex UI
package Hex;

import javax.swing.*;

import Hex.Vertex.vertexType;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
public class Hex_UI{
	public final static int SIDE=7;
	public static JFrame frame;
	public static JPanel panel;
	public static JMenuBar menuBar;
	public static AnyShapeButton [][]buttons;
	Hex_UI(){
		frame = new JFrame("HEX");
		panel = new JPanel();
		buttons = new AnyShapeButton[SIDE][SIDE];
		panel.setLayout(null);
		for(int i=0 ;i<SIDE;i++){
			for(int j=0;j<SIDE;j++)
			{
				buttons[i][j] = new AnyShapeButton();
				buttons[i][j].number = i*SIDE+j;
			}
		}
		for(int i=0;i<SIDE;i++){
			for(int j=0;j<SIDE;j++){
//				setBounds(����x������y�����γ������ο�)jΪ���������iΪ�������
				buttons[i][j].setBounds(30+38*j+19*i, 30+34*i,34,40);
				panel.add(buttons[i][j]);
			}
		}
		//�˵�***************************************
			menuBar = new JMenuBar();
			JMenu Operate= new JMenu("����");
			JMenuItem buildTree= new JMenuItem("����");
			buildTree.addActionListener(e->{
				
			});
			Operate.add(buildTree);
			menuBar.add(Operate);
			frame.setJMenuBar(menuBar);
		
		
		//*******************************************
		frame.add(panel);
		frame.setSize(60+38*SIDE+19*SIDE, 100+34*SIDE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
	}
	public static void setStep(int step,vertexType clor){
		int i = step/SIDE;
		int j = step%SIDE;
		if(clor==vertexType.RED){
			buttons[i][j].curColor = Color.red;
			buttons[i][j].repaint();
		}
		if(clor==vertexType.BLUE){
			buttons[i][j].curColor = Color.blue;
			buttons[i][j].repaint();
		}
	}
	public static void main(String[] args) {
		DataStructure a = new DataStructure();
		new Hex_UI();

	}
}

//anyshapebutton
package Hex;

import javax.swing.JButton;

import org.omg.CORBA.DATA_CONVERSION;

import Hex.Vertex.vertexType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AnyShapeButton extends JButton {
	static int side=20;
	static double sq3 = Math.sqrt(3);
	static int[]y ={side/2,side*3/2,2*side,side*3/2,side/2,0};
	static int[]x ={0,0,(int)(side/2*sq3),(int)(side*sq3),(int)(side*sq3),(int)(side*sq3/2)};
	Polygon shape;
	Color curColor = Color.WHITE;
	int nflag=0;
	public static int RorB=0;
	public int number;
	public AnyShapeButton(Polygon p){
		init(p);
	}
	public AnyShapeButton(int[]x,int[]y){
		Polygon p = new Polygon(x,y,x.length);
		init(p);
	}
	public AnyShapeButton(){
		Polygon p = new Polygon(x,y,x.length);
		init(p);
	}
	private void init(Polygon p){
		shape = p;
		setOpaque(false);
		setPreferredSize();
		setBorder(null);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e){
				if(nflag==0){
					if(RorB==0){
						SetRed();
//						RorB=1;
						DataStructure.VexBox[number].Clor = vertexType.RED;//���޸ĵ���
						if(Evaluate.Win(vertexType.RED)){
							JOptionPane.showMessageDialog(null,"��Ӯ��","�Ի���",JOptionPane.INFORMATION_MESSAGE);
						}
					//DataStructure.displayVexNet();
						int step=SearchTree.GetTheNextStep(vertexType.BLUE);//�ó���һ��Ҫ�ߵ���
						DataStructure.VexBox[step].Clor = vertexType.BLUE;//�������޸ĳ��߹�һ��
						Hex_UI.setStep(step, vertexType.BLUE);//����ť�е���һ����ĳ���Ӧ��ɫ
						if(Evaluate.Win(vertexType.BLUE)){
							JOptionPane.showMessageDialog(null,"������","�Ի���",JOptionPane.INFORMATION_MESSAGE);
						}
						//DataStructure.displayVexNet();
						//System.out.println(SearchTree.flag1);
					//	System.out.println(SearchTree.flag2);
						
					}
					else if(RorB==1){
						SetBlue();
						//RorB=0;
						DataStructure.VexBox[number].Clor = vertexType.BLUE;//���޸ĵ���
						if(Evaluate.Win(vertexType.BLUE)){
							JOptionPane.showMessageDialog(null,"������","�Ի���",JOptionPane.INFORMATION_MESSAGE);
						}
						
					}
					nflag=1;
				}else{
					return;
				}
			}
		});
	}
	public void setPreferredSize(){
		Rectangle b = shape.getBounds();
		setPreferredSize(new Dimension(b.width,b.height));
		}
	public void SetRed(){
		curColor = Color.red;
		repaint();
	}
	public void SetBlue(){
		curColor = Color.blue;
		repaint();
	}
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		//super.paint(g);
		g.setColor(curColor);
		g.fillPolygon(shape);
	}
	
	public boolean contains(int x,int y){
		return shape.contains(x,y);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
//data structure
package Hex;

import Hex.Vertex.vertexType;

public class DataStructure {
	public static int SIDE=Vertex.SIDE;
	public static int ADDITION = SIDE*SIDE;
	public static Vertex []VexBox;
	DataStructure(){
		//��̬�����
		VexBox = new Vertex [ADDITION];
		for(int i=0;i<ADDITION;i++){
			VexBox[i] = new Vertex(i);
		}
	}
	public static void displayVexNet(){
		for(int i=0,j=0;i<SIDE;i++){
			for(int t=0;t<SIDE;t++){
				if(VexBox[j].Clor==vertexType.WHITE){
					System.out.print("w  ");
				}
				else if(VexBox[j].Clor==vertexType.RED){
					System.out.print("r  ");
				}
				else if(VexBox[j].Clor==vertexType.BLUE){
					System.out.print("b  ");
				}
				j++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
//	public static void main(String[] arg){
//	}
}
//Evaluate
package Hex;

import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;
import Hex.Vertex.vertexType;
public class Evaluate {
	public static int SIDE = DataStructure.SIDE;
	public static int S = 0;
	public static int T = DataStructure.ADDITION+1;
	public  Vertex []VexBox;
	int Net[][] ;
	public void init(){
		//�Ե㼯�Ĳ���
		VexBox = new Vertex[T-1];
		for(int i=0;i<T-1;i++){
			VexBox[i] = new Vertex(DataStructure.VexBox[i]);
		}
			for(int i=0;i<SIDE;i++){
				Net[S][1+SIDE*i]=10000;
			}
			for(int i=1;i<=SIDE;i++){
				Net[SIDE*i][T]=10000;
			}
		for(int i=1;i<T;i++){
			
			if(isLeftExit(i)){
				addArc(i,Left(i));
			}
			if(isTopExit(i)){
				addArc(i,Top(i));
			}
			if(isLeftExit(i)&&isBottomExit(i)){
				addArc(i,LeftBottom(i));
			}
			if(isRightExit(i)){
				addArc(i,Right(i));
			}
			
			if(isBottomExit(i)){
				addArc(i,Bottom(i));
				
			}
			if(isRightExit(i)&&isTopExit(i)){
				addArc(i,RightTop(i));	
			}
			
		}
	}
	public void initByBox(){
		Net = new int [DataStructure.ADDITION+2][DataStructure.ADDITION+2];
		init();
		for(int i=1;i<T;i++){
			if(VexBox[i-1].Clor==vertexType.RED){
				comboVex(i);
				}
			if(VexBox[i-1].Clor==vertexType.BLUE){
				deleVex(i);
			}
		}
	}
	public static vertexType oppsColor(vertexType Clor){
		if(Clor==vertexType.RED){
			return vertexType.BLUE;
		}
		else if(Clor == vertexType.BLUE){
			return vertexType.RED;
		}
		else{
			return vertexType.WHITE;
		}
	}
	public void deleVex(int Vexnum){
		for(int i=0;i<T+1;i++){
			Net[Vexnum][i] = 0;
			Net[i][Vexnum] = 0;
		}
	}
	public void comboVex(int Vexnum){
		int []lineBegin = new int [SIDE*SIDE];
		int []lineEnding = new int [SIDE*SIDE];
		int flag1 = 0;
		int flag2 = 0;
		for(int i=0;i<T+1;i++){
			if(Net[Vexnum][i]!=0){
				lineEnding[flag1]=i;
				//System.out.println("�յ�"+i);
				flag1++;
			}
			if(Net[i][Vexnum]!=0){
				lineBegin[flag2] = i;
				//System.out.println("���"+i);
				flag2++;
			}
		}
		for(int i =0;i<flag2;i++){
			for(int j=0;j<flag1;j++){
				Net[lineBegin[i]][lineEnding[j]]=Math.max(Net[lineBegin[i]][Vexnum], Net[Vexnum][lineEnding[j]]);
			}
		}
		deleVex(Vexnum);
	}
	public static boolean isLeftExit(int vertexNum){
		if((vertexNum-1)%SIDE-1>=0){
			return true;
		}
		else return false;
	}
	public static boolean isRightExit(int vertexNum){
		if((vertexNum-1)%SIDE+1<SIDE){
			return true;
		}
		else return false;
	}
	public static  boolean isTopExit(int vertexNum){
		if((vertexNum-1)/SIDE>0){
			return true;
		}
		else return false;
	}
	public static boolean isBottomExit(int vertexNum){
		if((vertexNum-1)/SIDE+1<SIDE){
			return true;
		}
		else return false;
	}
	public static int Left(int vertexNum){
		if(isLeftExit(vertexNum)){
			return vertexNum-1;
		}
		else return -1;
	}
	public static int Right(int vertexNum){
		if(isRightExit(vertexNum)){
			return vertexNum+1;
		}
		else return -1;
	}
	public static int Top(int vertexNum){
		if(isTopExit(vertexNum)){
			return vertexNum-SIDE;
		}
		else return -1;
	}
	public static int Bottom(int vertexNum){
		if(isBottomExit(vertexNum)){
			return vertexNum+SIDE;
		}
		else return -1;
	}
	public static int LeftBottom(int vertexNum){
		return vertexNum+SIDE-1;
	}
	public static int RightTop(int vertexNum){
		return vertexNum-SIDE+1;
	}
	public void addArc(int i,int j){
		Net[i][j]=1;
	}
	void display(){
		System.out.print("   ");
		for(int i=0;i<T+1;i++){
			System.out.print(i+"  ");
		}
		System.out.println("");
		for(int i=0;i<T+1;i++){
			System.out.print(i+"  ");
			for(int j=0;j<T+1;j++){
				if(Net[i][j]>1){
					System.out.print("��  ");
				}else{
				System.out.print(Net[i][j]+"  ");
				}
			}
			System.out.println("");
		}
	}
	public static boolean hasPath (int rGrath[][],int s,int t,int path[]){
		boolean visited[] = new boolean [T+1];
		Queue<Integer> queue = new LinkedList<Integer>();
		queue.add(s);
		visited[s]=true;
		while(queue.size()>0){
			int top =  queue.poll();
			for(int i=0;i<T+1;i++){
				if(!visited[i]&&rGrath[top][i]>0){
					queue.add(i);
					visited[i]=true;
					path[i]=top;
				}
			}
		}
		return visited[t]==true;
	}
	
	private static int maxFlow(int[][]grath,int s,int t){
		int rGrath[][] =  new int [T+1][T+1];
		for (int i=0;i<T+1;i++){
			for(int j=0;j<T+1;j++){
					rGrath[i][j]= grath[i][j];
			}
		}
		int maxFlow = 0;
		int path [] =  new int [T+1];
		while(hasPath(rGrath,s,t,path)){
			int min_flow = Integer.MAX_VALUE;
			for(int v=t;v!=s;v=path[v]){             //����ÿ������������С����
				int u = path[v];
				min_flow = Math.min(min_flow, rGrath[u][v]);
				}
			for(int v=t; v != s; v=path[v]){         //�����������
				int u = path[v];
				rGrath[u][v] = rGrath[u][v]-min_flow;
			    rGrath[v][u] = rGrath[v][u]+min_flow;
			    }
			            maxFlow = maxFlow+min_flow;
		}
		return maxFlow;
	}
	public static int value(){
		Evaluate a =new Evaluate();
		a.initByBox();;
		return maxFlow(a.Net,S,T);
		
	}
	public static boolean Win(vertexType player){						//true��Win��false����
		Evaluate a = new Evaluate();
		int path[]=new int [T+1];
		a.initByBox();
		//a.display();
		if(player==vertexType.RED){
			if(a.Net[S][T]!=0){
				return true;
			}
		}
		if(player==vertexType.BLUE){
			if(!hasPath(a.Net,S,T,path)){
				return true;	
			}
		}
		return false;
	}
	public boolean Winer(vertexType player){						//true��Win��false����
		//Evaluate a = new Evaluate();
		int path[]=new int [T+1];
		//a.initByBox();
		if(player==vertexType.RED){
			if(Net[S][T]!=0){
				return true;
			}
		}
		if(player==vertexType.BLUE){
			if(!hasPath(Net,S,T,path)){
				return true;	
			}
		}
		return false;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Evaluate a = new Evaluate();
		//initByBox(vertexType.RED);
		a.Net = new int [DataStructure.ADDITION+2][DataStructure.ADDITION+2];
		//SearchTree b = new SearchTree(1, vertexType.BLUE);
		new DataStructure();
		a.VexBox = new Vertex[T-1];
		for(int i=0;i<T-1;i++){
			a.VexBox[i] = new Vertex(DataStructure.VexBox[i]);
		}
		a.init();
//		a.deleVex(2);
		a.comboVex(2);
//		a.comboVex(3);
//		a.comboVex(4);
//		a.deleVex(1);
//		a.deleVex(5);
		a.deleVex(7);
		//a.display();
		if(a.Winer(vertexType.BLUE)){
			JOptionPane.showMessageDialog(null,"������","�Ի���",JOptionPane.INFORMATION_MESSAGE);
		}
		if(a.Winer(vertexType.RED)){
			JOptionPane.showMessageDialog(null,"��Ӯ��","�Ի���",JOptionPane.INFORMATION_MESSAGE);
		}
		//a.deleVex(7);
		int flow =maxFlow(a.Net,S, T);
		System.out.println(flow);
	//	a.display();
	//	System.out.println(a.T);
	}

}
//search tree
package Hex;

import java.util.Stack;

import Hex.Vertex.vertexType;

public class SearchTree {
	Vertex Data;
	public SearchTree fChildNode;
	public static Stack<StackType>stack = new Stack<StackType>();
	public static int step;
	public static Vertex vex;
	public static int ASSIGNDEPTH = 4;
	class StackType{
		Vertex v;
		vertexType Player;
		int Depth;
		public StackType() {
		}
		StackType(Vertex a,vertexType player,int Depth){
			v=a;
			this.Depth = Depth;
			this.Player = player;
		}
	}
	SearchTree(){
		Data = new Vertex();
	}
	SearchTree(int step){
		Data = new Vertex(step);
	}
	SearchTree(int step,vertexType TClor){
		Data = new Vertex(step, TClor);
	}
	static int flag=0;
	public void minimax(int depth,vertexType Player){
		if(Player==vertexType.RED){
			Max(depth,Integer.MIN_VALUE,Integer.MAX_VALUE,Player);
		}
		else if(Player==vertexType.BLUE){
			Min(depth,Integer.MIN_VALUE,Integer.MAX_VALUE,Player);
		}
	}
	public int Max(int Depth,int Alpha,int beta,vertexType player){
		//int v=Evaluate.value();
		if(Depth == 0/*||Evaluate.Win(vertexType.RED)*/){
			return /*v*/Evaluate.value();					//������Ϊ0�����ظõ��ֵ
		}
		GenerateLegalMoves(Depth,player);								//GenerateLeagalMoves();	//�����߷�
		while (MovesLeft(Depth)) {								//�����߷����ĺ�����ֱ�ӱ�����������
			MakeNextMove();										//��һ��
			//DataStructure.displayVexNet();
			//System.out.println(flag++);
			int val = Min(Depth - 1,Alpha,beta,oppsColor(player));		//�õ���һ���Ĺ�ֵ
																		//���ҽ���ǰ���ֵ�����½紫����һ��
			UnmakeMove(Depth);										//������һ��
			if (val	>Alpha) {									//����и��ӽڵ��ֵ���ڵ�ǰ���ѡ����滻��
				if(Depth==ASSIGNDEPTH/*��������*/){
					step = vex.vertexNum;
				}
				Alpha = val;	
			}
			if (beta <=Alpha) {									//����и��ӽڵ������һ�㴫�������Ͻ磬����������ڵ�
			//	displayStack();
				//System.out.println(Alpha);
				moveAllNode(Depth);
				break ;							//�����ջ�����иýڵ���ӽڵ㣬�����½�
			}
			
		}
			return Alpha;										//�������ֵ
	}
	public int Min(int Depth,int Alpha,int beta,vertexType player){
	//	int v=Evaluate.value();
		if(Depth == 0/*||Evaluate.Win(vertexType.BLUE)*/){
			return /*v*/Evaluate.value();					//������Ϊ0�����ظõ��ֵ
		}
		//int best = Integer.MAX_VALUE;
		GenerateLegalMoves(Depth,player);							//GenerateLeagalMoves();	//�����߷�
		while (MovesLeft(Depth)) {								//�����߷����ĺ�����ֱ�ӱ�����������
			MakeNextMove();										//��һ��
			//DataStructure.displayVexNet();
			//System.out.println(flag++);
			int val = Max(Depth - 1,Alpha, beta,oppsColor(player));		//�õ���һ���Ĺ�ֵ
																		//���ҽ���ǰ��Сֵ��ѡ�����һ��
			UnmakeMove(Depth);										//������һ��
			if (val <beta) {									//����и��ӽڵ�С�ڵ�ǰ���ѡ���ô�滻��ǰ���ѡ��
				if(Depth==ASSIGNDEPTH/*��������*/){
					step = vex.vertexNum;
				}
				beta = val;
			}
			if (beta < Alpha) {									//����ýڵ��ֵС����һ������½磬����������ڵ�
			//	displayStack();
			//	System.out.println(beta);
				moveAllNode(Depth);
			
				break ;							//�����ջ�����иýڵ���ӽڵ㣬�����½�
			}
			
		}
			return beta;
	}
	public static int GetTheNextStep(vertexType player){
		SearchTree Head = new SearchTree();
		System.out.println("begin");
			//Head.AlphaBeta(ASSIGNDEPTH/*ָ�����*/, -Integer.MAX_VALUE,Integer.MAX_VALUE);
			Head.minimax(ASSIGNDEPTH, player);
		System.out.println("Ending");
		return step;
	}
	public void GenerateLegalMoves(int Depth,vertexType Player){
		//System.out.println("�����߷�");
		for(int i=0;i<DataStructure.ADDITION;i++){
			if(DataStructure.VexBox[i].Clor==vertexType.WHITE){
				stack.push(new StackType(DataStructure.VexBox[i],Player,Depth));
			}
		}
	}
	public boolean MovesLeft(int nowDepth) {
		StackType a;
		if(!stack.empty())
			a =stack.peek();
		else{
			return false;
		}
		if(a.Depth==nowDepth){
			return true;
		}
		return false;
	}
	public void MakeNextMove(){
		StackType a =stack.peek();
		DataStructure.VexBox[a.v.vertexNum].Clor = a.Player;
//		if(a.Player==vertexType.RED)
//			System.out.println("moveRED");
//		else if(a.Player==vertexType.BLUE){
//			System.out.println("moveBLUE");
//		}
	}
	public void UnmakeMove(int Dth){
		StackType a = stack.pop();
		vex = a.v;
		DataStructure.VexBox[a.v.vertexNum].Clor = vertexType.WHITE;
//		if(a.Player==vertexType.RED)
//			System.out.println("backRED");
//		else if(a.Player==vertexType.BLUE){
//			System.out.println("backBLUE");
//		}
	}
	public int AlphaBeta(int depth,int Alpha,int beta){
		if(depth == 0){
			return Evaluate.value();					//������Ϊ0�����ظõ��ֵ
		}
		GenerateLegalMoves(depth,vertexType.RED);							//GenerateLeagalMoves();	//�����߷�
		while (MovesLeft(depth)) {								//�����߷����ĺ�����ֱ�ӱ�����������
			MakeNextMove();										//��һ��
			DataStructure.displayVexNet();
			int val = -AlphaBeta(depth - 1, -beta, -Alpha);		//�õ��ֵ�����ӽڵ���С��ֵ
			System.out.println(val);
			UnmakeMove(depth);										//������һ��
			if (val >= beta) {									//�����ֵ�������ޣ�����������ڵ�
				moveAllNode(depth);
				return beta;										//��˼�Ƕ��ֲ�������һ������Ϊ���и����õ��߷�
			}
			if (val > Alpha) {									//�����ֵ������õģ���ô�����õĳ�Ϊ����
				if(depth==ASSIGNDEPTH/*��������*/){
					step = vex.vertexNum;
				}
				this.Data.weight=Alpha = val;
			}
			
		}
		//DataStructure.displayVexNet();
			return Alpha;	
	}
	public static void displayStack(){
		for(StackType x:stack){
			System.out.print(x.v.vertexNum+"��"+x.Depth+" ");
			if(x.Player==vertexType.RED){
				System.out.println("r");
			}
			else if(x.Player==vertexType.BLUE){
				System.out.println("b");
			}
		}
		
	}
	public void moveAllNode(int depth){
		StackType a = stack.peek();
		while(a.Depth<=depth){
			stack.pop();
			a=stack.peek();
		}
	}
	public static vertexType oppsColor(vertexType Clor){
		if(Clor==vertexType.RED){
			return vertexType.BLUE;
		}
		else if(Clor == vertexType.BLUE){
			return vertexType.RED;
		}
		else{
			return vertexType.WHITE;
		}
	}
	public static void main(String[] args) {
	}
	
}
//text
package Hex;

import Hex.Vertex.vertexType;

public class text {
	
	text(){};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SearchTree Head = new SearchTree(-1);
		Head.fChildNode = new SearchTree(1,vertexType.RED);
		Head.displayStack();
	}

}
//vertex
package Hex;

public class Vertex {
	public static int SIDE = Hex_UI.SIDE;
	enum vertexType{RED,BLUE,WHITE};
	public vertexType Clor;
	public int vertexNum;
	public int weight;
	Vertex (){}
	Vertex(int number){
		this.vertexNum = number;
		this.Clor = vertexType.WHITE;
	}
	Vertex(int number,vertexType clor){
		this.vertexNum = number;
		this.Clor = clor;
	}
	Vertex(Vertex v){
		this.vertexNum = v.vertexNum;
		this.Clor = v.Clor;
		this.weight = v.weight;
	}
	public vertexType oppsColor(){
		if(this.Clor==vertexType.RED){
			return vertexType.BLUE;
		}
		else if(this.Clor == vertexType.BLUE){
			return vertexType.RED;
		}
		else{
			return vertexType.WHITE;
		}
	}
}
