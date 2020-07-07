package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class GUI implements ActionListener{
	static ArrayList<double[]> data;
	JFrame frame = null;
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JLabel labelC = new JLabel("收斂條件(正數)");
	JTextField inputC = new JTextField(5);
	JLabel labelL = new JLabel("學習率 (正數)");
	JTextField inputL = new JTextField(5);
	JLabel labalR = new JLabel("辨識率 ");
	JTextField outputR = new JTextField(5);
	JLabel labalLR = new JLabel("訓練正確率");
	JTextField outputLR = new JTextField(5);
	JLabel labalTR = new JLabel("測試正確率");
	JTextField outputTR = new JTextField(5);
	coodpaint canvas = new coodpaint();
	JFileChooser fileChoose = null;
	File file = null;
	String filename;
	Perceptron perceptron = new Perceptron();

	public GUI() {
		frame = new JFrame("Perceptron");
		Container contentPane = frame.getContentPane();
		canvas.setSize(640,640);
		JButton btn1 = new JButton("選擇檔案");
		JButton btn2 = new JButton("開始測試");
		
		outputR.setEditable(false);
		outputR.setText("0");
		outputTR.setEditable(false);
		outputTR.setText("0");
		outputLR.setEditable(false);
		outputLR.setText("0");
		inputL.setText("0.2");
		inputC.setText("100");
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		panel1.add(btn1);
		panel1.add(labelL);
		panel1.add(inputL);
		panel1.add(labelC);
		panel1.add(inputC);
		panel1.add(labalR);
		panel1.add(outputR);
		panel1.add(btn2);
		panel2.add(labalLR);
		panel2.add(outputLR);
		panel2.add(labalTR);
		panel2.add(outputTR);


		fileChoose = new JFileChooser();

		contentPane.add(canvas, BorderLayout.CENTER);
		contentPane.add(panel1, BorderLayout.NORTH);
		contentPane.add(panel2, BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	public void actionPerformed(ActionEvent e){

		int result;
		
		if (e.getActionCommand().equals("選擇檔案")) {
			fileChoose.setDialogTitle("檔案");
			result = fileChoose.showOpenDialog(frame);

			if (result == JFileChooser.APPROVE_OPTION ) {
				file = fileChoose.getSelectedFile();
				
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
					
					perceptron.setData(data);
					canvas.drawData(data);
					
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
			//傳學習率、收斂條件
			perceptron.iData(Double.valueOf(inputL.getText()), Double.valueOf(inputC.getText()));
			double[] w = perceptron.train();
			canvas.drawLine(w);
			String per  = "%";
			outputR.setText(String.format("%.2f %s", perceptron.alltest(),per));
			outputLR.setText(String.format("%.2f %s", perceptron.learn(),per));
			outputTR.setText(String.format("%.2f %s", perceptron.test(),per));
		}
	}
}
