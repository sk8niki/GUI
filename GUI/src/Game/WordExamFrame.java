package Game;
/**
*@author Rain
*
*
*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class WordExamFrame extends JFrame 
                           implements ActionListener
{
	//定义一个存放候选字符的数组
	final char[] choiceChar={'q','w','e','r','t','y','u','i','o','p','[',']','a','s','d','f','g','h','j','k','l'
			,';','z','x','c','v','b','n','m',',','.','/','Q','W','E','R','T','Y','U','I','O','P',
			'{','}','A','S','D','F','G','H','J','K','L',':','"','Z','X','C','V','B','N','M','<','>',
			'?','|','!','@','#','$','%','^','&','(',')','_','-','+','=','`','~','*'};
	//定义记录用户得分的变量
	int score =0;
	//定义界面上出现的元素
	JButton btnBegin=new JButton("开始");
	JButton btnClose=new JButton("关闭");
	JLabel lblScore=new JLabel("得分：");
	
	JLabel lblWord=new JLabel("等待产生的字符...");
	
	JLabel lblTime=new JLabel("倒计时：");
	JTextField tfdWord=new JTextField(20);
	
	//定义界面的Init初始化方法
	public void Init()
	{
		setSize(400,300);
		setTitle("打字测试程序，检测你是否为键人！");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*****将上面6个界面元素加入到屏幕上*****/
		
		//1.设置整体窗口的布局方式--边框布局
		setLayout(new BorderLayout());
		
		//2.增加顶部元素
		//2.1创建一个顶部托盘JPanel
		JPanel pTop=new JPanel();
		pTop.setBackground(Color.yellow);
		pTop.add(btnBegin);
		pTop.add(btnClose);
		pTop.add(lblScore);
		//2.2将pTop放到屏幕的顶部
        add(pTop,BorderLayout.NORTH);	
        
        //3.增加中间元素
        add(lblWord,BorderLayout.CENTER);
        
        //4.增加底部元素
        JPanel pBottom =new JPanel();
        pBottom.setBackground(Color.green);
        pBottom.add(lblTime);
        pBottom.add(tfdWord);
        add(pBottom,BorderLayout.SOUTH);
        
        lblWord.setFont(new Font("黑体",1,50));
        lblWord.setHorizontalAlignment(JLabel.CENTER);
	}
	//定义构造方法
	public WordExamFrame()
	{
		//1.调用Init进行界面的初始化
		Init();
		
		//2.设置对时间的监听
		//2.1对输入框的回车事件进行监听
		tfdWord.addActionListener(this);
		
		//创建并启动产生字符的任务
		TaskOfCreateWord t1=new TaskOfCreateWord();
		t1.start();
		
		//创建并启动倒计时线程
		new TaskOfCountTime().start();
	}
	
	//定义产生随机字符的线程任务类--内部类
	class TaskOfCreateWord extends Thread
	{   
		@Override
		public void run(){
			Random rd =new Random();
			while(totalTime>0)
			{	
			//随机产生一个字符
				int index =rd.nextInt(choiceChar.length);
			String x=""+ choiceChar[index];
			//显示在屏幕中央
			lblWord.setText(x);
			
			//任务休眠即停顿几秒
			try {
				sleep(1000*2);
			} catch (Exception e) { 
				e.printStackTrace();
			}
			}//end while
			
			
		}//end run
	}//end class
	
	//编写main作为程序的入口
	public static void main(String[] args) {
		WordExamFrame f1 =new WordExamFrame();
	                  f1.setVisible(true);	
	}
//用户事件处理
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// 区分事件源
		if(arg0.getSource()== tfdWord)//用户在输入框中按回车
		{
			//获得用户输入的内容
			String s1 =tfdWord.getText();
			//获得当前显示的内容
			String s2 =lblWord.getText();
			//比较
			if(s1.equals(s2))
			{   
				score++;
				lblScore.setText("得分："+score);
			}
		}//清空输入框中的内容
		tfdWord.setText("");
		
	}//end actionOerfirmed
	//定义计时变量
	int totalTime= 2*60;
	//定义一个倒计时线程任务类TaskOfCountTime
	class TaskOfCountTime extends Thread
	{
		public void run()
		{   
			do{
			try{
			sleep(1000);
			   }catch(Exception e){}
			totalTime--;
			lblTime.setText("倒计时："+totalTime);
		      }while(totalTime>0);
			JOptionPane.showMessageDialog(null, "本次测试结束！");
		}//end run
	}
	}//end class TaskOfCountTime

