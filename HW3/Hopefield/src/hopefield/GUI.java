package hopefield;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GUI implements ActionListener{
	JFrame frame = null;
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	//JPanel panel3 = new JPanel();
	//JLabel label1 = new JLabel("原圖");
	//JLabel label2 = new JLabel("回想結果圖");
	JTextField input1 = new JTextField(10);
	JTextField input2 = new JTextField(10);
	codepaint canvas = new codepaint();
	codepaint1 canvas1 = new codepaint1();
	Hopefield hopefield = new Hopefield();
	JFileChooser fileChoose = null;
	File trainfile = null;
	File testfile = null;
	String filename;
	//String [][] traindata = new String[3][13];
	ArrayList<String []> traindata = new ArrayList<String []>();
	//String [][] testdata = new String[3][13];
	ArrayList<String []> testdata = new ArrayList<String []>();
	int [][] recalldata;
	double [][] w;
	double [] theta;
	boolean testing = false;
	int number = 0;
	int upline;
	GUI(){
		input1.setEditable(false);
		input2.setEditable(false);
		JButton btn1 = new JButton("選擇訓練檔案");
		JButton btn2 = new JButton("選擇測試檔案");
		JButton btn3 = new JButton("開始測試");
		JButton btn4 = new JButton("上一個");
		JButton btn5 = new JButton("下一個");
		panel1.add(btn1);
		panel1.add(input1);
		panel1.add(btn2);
		panel1.add(input2);
		panel1.add(btn3);
		panel2.add(btn4);
		panel2.add(btn5);
		
		canvas.setSize(320,460);
		canvas1.setSize(320,460);
		
		fileChoose = new JFileChooser();
		
		frame = new JFrame("Hopefield");
		frame.setSize(800, 560);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		
		contentPane.add(panel1, BorderLayout.NORTH);
		contentPane.add(canvas, BorderLayout.WEST);
		contentPane.add(canvas1, BorderLayout.EAST);
		contentPane.add(panel2, BorderLayout.CENTER);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int result;
		
		if (e.getActionCommand().equals("選擇訓練檔案")){
			fileChoose.setDialogTitle("檔案");
			result = fileChoose.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION ) {
				trainfile = fileChoose.getSelectedFile();
				input1.setText(trainfile.getName());
				
				filename = trainfile.toString();
				
				try {
					traindata = new ArrayList<String[]>();
					FileReader fr = new FileReader(filename);
					BufferedReader br = new BufferedReader(fr);
					StringBuffer temp = new StringBuffer();
					String temp1 = null;
					String [] temp2 = new String[13];
					int j = 0;
					while(br.ready()){						
						temp.append(br.readLine());
						while(temp.length() < 9){
							temp.append("0");
						}
						temp1 = temp.toString();
						temp2[j] = temp1.replaceAll(" ", "0");
						temp = new StringBuffer();
						j++;
						if(j == 13){
							br.readLine();br.readLine();
							traindata.add(temp2);
							temp2 = new String[13];
							j=0;
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				testing = false;
				hopefield.setwData(traindata);
				w = hopefield.makew();
				theta = Hopefield.maketheta(w);
			}
		}
		
		if (e.getActionCommand().equals("選擇測試檔案")){
			fileChoose.setDialogTitle("檔案");
			result = fileChoose.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION ) {
				testfile = fileChoose.getSelectedFile();
				input2.setText(testfile.getName());
				
				filename = testfile.toString();
				
				try {
					testdata = new ArrayList<String[]>();
					FileReader fr = new FileReader(filename);
					BufferedReader br = new BufferedReader(fr);
					StringBuffer temp = new StringBuffer();
					String temp1 = null;
					String [] temp2 = new String[13];
					int j = 0;
					while(br.ready()){						
						temp.append(br.readLine());
						while(temp.length() < 9){
							temp.append("0");
						}
						temp1 = temp.toString();
						temp2[j] = temp1.replaceAll(" ", "0");
						temp = new StringBuffer();
						j++;
						if(j == 13){
							br.readLine();br.readLine();
							testdata.add(temp2);
							temp2 = new String[13];
							j=0;
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				number = 0;
				testing = false;
				canvas.setData(testdata.get(number));
				upline = testdata.size();
			}
		}
		
		if (e.getActionCommand().equals("開始測試")&&trainfile!=null&&testfile!=null){
			recalldata = hopefield.recall(testdata, w, theta);
			canvas.setData(testdata.get(number));
			canvas1.setData(recalldata[number]);
			testing = true;
		}
		
		if(e.getActionCommand().equals("上一個")&& testfile != null){
			if(number > 0){
				number -= 1;
				canvas.setData(testdata.get(number));
				if(testing){
					canvas1.setData(recalldata[number]);
				}
			}
		}
		
		if(e.getActionCommand().equals("下一個")&& testfile != null){
			if(number < upline-1){
				number += 1;
				canvas.setData(testdata.get(number));
				if(testing){
					canvas1.setData(recalldata[number]);
				}
			}
		}
	}
}
