package MP;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

public class GUI implements ActionListener{
	
	static ArrayList<double[]> data;
	JFrame frame = null;
	JPanel panel1 = new JPanel();
	JLabel labelL = new JLabel("學習率 (正數)");
	JTextField inputL = new JTextField(5);
	JLabel labelC = new JLabel("收斂條件(誤差)");
	JTextField inputC = new JTextField(5);
	JLabel label1 = new JLabel("訓練正確率：");
	JTextField output1 = new JTextField(6);
	JLabel label2 = new JLabel("測試正確率：");
	JTextField output2 = new JTextField(6);
	JLabel label3 = new JLabel("辨識率：");
	JTextField output3 = new JTextField(6);
	JLabel label4 = new JLabel("均方根誤差：");
	JTextField output4 = new JTextField(20);
	JLabel label5 = new JLabel("檔名：");
	JTextField output5 = new JTextField(6);
	JFileChooser fileChoose = new JFileChooser();
	File file = null;
	String filename;
	MPerceptron mperceptron = new MPerceptron();
	
	public GUI(){
		JButton btn1 = new JButton("選擇檔案");
		JButton btn2 = new JButton("開始測試");
		
		output1.setEditable(false);
		output2.setEditable(false);
		output3.setEditable(false);
		output4.setEditable(false);
		output5.setEditable(false);
		inputL.setText("0.2");
		inputC.setText("0.001");
		
		frame  = new JFrame("Multilayer Perceptron");
		frame.setSize(500, 140);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		
		frame.add(panel1);
		
		panel1.add(btn1);
		panel1.add(labelL);
		panel1.add(inputL);
		panel1.add(labelC);
		panel1.add(inputC);
		panel1.add(btn2);
		panel1.add(label1);
		panel1.add(output1);
		panel1.add(label2);
		panel1.add(output2);
		panel1.add(label3);
		panel1.add(output3);
		panel1.add(label4);
		panel1.add(output4);
		panel1.add(label5);
		panel1.add(output5);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		int result;
		
		if (e.getActionCommand().equals("選擇檔案")) {
			fileChoose.setDialogTitle("檔案");
			result = fileChoose.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION ) {
				file = fileChoose.getSelectedFile();
				output5.setText(file.getName());
				
				FileReader fr;
				filename = file.toString();
				
				try {
					data = new ArrayList();
					fr = new FileReader(filename);
					BufferedReader br = new BufferedReader(fr);
					while (br.ready()){
						String s = br.readLine();
						String [] st = s.split("\\s+");
						double [] tt = null;
						
						if(st[0].isEmpty()){
							int sp = 1;
							while(st[sp].isEmpty()){
								sp++;
							}
							tt = new double [st.length - sp];
							for(int i = sp ; i < st.length ; i++){
								tt[i-sp] = Double.valueOf(st[i]);
							}
						}else if(!st[0].isEmpty()){
							tt = new double[st.length];
							for(int i = 0 ; i < st.length ; i++){
								tt[i] = Double.valueOf(st[i]);
							}		
						}
						data.add(tt);
					}
					mperceptron.setData(data);
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		if (e.getActionCommand().equals("開始測試")&&file!=null) {
			mperceptron.iData(Double.valueOf(inputL.getText()), Double.valueOf(inputC.getText()));
			mperceptron.train();
			String per  = "%";
			output3.setText(String.format("%.2f %s", mperceptron.alltest(),per));
			output1.setText(String.format("%.2f %s", mperceptron.traintest(),per));
			output2.setText(String.format("%.2f %s", mperceptron.test(),per));
			output4.setText(String.format("%s",mperceptron.avlms()));
		}
	}
}
