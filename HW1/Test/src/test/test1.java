package test;

import java.io.FileReader;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.math.BigDecimal;
import java.util.Scanner;

public class test1 extends Frame{

	double [] x1;
	double [] x2;
	double [] e;
	double [] w;
	double max;
	double min;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//初始值
		double x0 = -1;
		double [] w = {-1,0,1};
		double l;
		
		//抓檔案
		String filename;
		//filename為檔案名稱
		Scanner inputfile = new Scanner(System.in);
		System.out.print("請輸入你想測試的檔案名稱(包含檔的類型 EX：XXX.txt)： ");
		filename = inputfile.next();
		//讀取檔案
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		StringBuffer sb = new StringBuffer();
		while (br.ready()){
		  sb.append(br.readLine()+" ");
		}
		fr.close();
		
		String a = sb.toString();
		
		//削去多餘的空格
		a = a.replaceAll("\\s+", " ");
		//切割字串並且調整
		String [] tt1 = a.split(" ");
		String [] tt = new String[(tt1.length)+1];
		if(tt1[0].isEmpty()){
			System.arraycopy(tt1, 0, tt, 0, tt1.length);
		}else{
			System.arraycopy(tt1, 0, tt, 1, tt1.length);
		}
		//分別把資料分配到x1 x2 e
		double [] x1 = new double[(tt.length-1)/3];
		double [] x2 = new double[(tt.length-1)/3];
		double [] e = new double[(tt.length-1)/3];
		
		for(int i = 0 ; i < x1.length ; i++){
			x1[i] = Double.parseDouble(tt[(3*i)+1]);
		}
		
		for(int i = 0 ; i < x2.length ; i++){
			x2[i] = Double.parseDouble(tt[(3*i)+2]);
		}
		
		for(int i = 0 ; i < e.length ; i++){
			e[i] = Double.parseDouble(tt[(3*i)+3]);
		}
		
		//輸入學習率
		Scanner inputlearn = new Scanner(System.in);
		System.out.print("請輸入學習率(學習率介於0~1之間)：");
		l = inputlearn.nextDouble();
		if(0>=l || l>=1){
			System.out.println("輸入的學習率值不符合範圍");
		}else{
			Scanner input = new Scanner(System.in);
			System.out.print("收斂條件(迴圈次數)：");
			int s = input.nextInt();
			System.out.println("x0： "+ x0);
			System.out.println("起始鍵結值： {" + w[0] + " , " + w[1] + " , " + w[2] + "}");
			w = select(x0,x1,x2,e,w,l,s);
		}
		
		new test1(x1,x2,e,w);
	}
	
	public test1(double [] _x1 , double [] _x2 , double [] _e , double [] _w){
		super("感知機圖示");
		x1 = _x1;
		x2 = _x2;
		e = _e;
		w = _w;
		max = e[0];
		min = e[0];
		for(int i = 0 ; i < e.length ; i++){
			if(e[i] > max){
				max = e[i];
			}
			if(e[i] < min){
				min = e[i];
			}
		}
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
		setVisible(true);
		setSize(600,600);
	}
	
	//畫圖
	public void paint(Graphics g){
        g.drawLine(300, 0, 300, 600);
        g.drawLine(0, 300, 600, 300);
        
        //尋找距離原點最大的值，用來改變圖的間距
        double amax = Math.abs(x1[0]);
        for(int i = 0 ; i < x1.length ; i++ ){
        	if(amax < Math.abs(x1[i])){
        		amax = Math.abs(x1[i]);
        	}
        	if(amax < Math.abs(x2[i])){
        		amax = Math.abs(x2[i]);
        	}
        }
        
        
        //畫點
        for(int i = 0 ; i < x1.length ; i++){
        	if(amax <= 5){
        		int x = (int)(x1[i]*60);
            	int y = (int)(x2[i]*60);

            	if(e[i] == max){
    				g.setColor(Color.blue);
            		g.drawString("O", x+296, Math.abs(305-y));
            	}else if (e[i] == min){
            		g.setColor(Color.red);
            		g.drawString("X", x+296, Math.abs(305-y));
            	}
        	}else if(amax <= 10){
        		int x = (int)(x1[i]*30);
            	int y = (int)(x2[i]*30);

            	if(e[i] == max){
    				g.setColor(Color.blue);
            		g.drawString(".", x+300, Math.abs(300-y));
            	}else if (e[i] == min){
            		g.setColor(Color.red);
            		g.drawString(".", x+300, Math.abs(300-y));
            	}
        		}else if(amax <= 30){
        		int x = (int)(x1[i]*10);
            	int y = (int)(x2[i]*10);
            	

            	if(e[i] == max){
    				g.setColor(Color.blue);
            		g.drawString(".", x+300, Math.abs(300-y));
            	}else if (e[i] == min){
            		g.setColor(Color.red);
            		g.drawString(".", x+300, Math.abs(300-y));
            	}
        	}
		}
        
        double ay = 0;
        
        //分割線
        if(amax <= 5){
            ay = Math.abs(300-((w[0]/w[2])*60));
        }else if(amax <= 10){
            ay = Math.abs(300-((w[0]/w[2])*30));
        }else if(amax <= 30){
            ay = Math.abs(300-((w[0]/w[2])*10));
        }
        //斜率
        double m = -(w[2]/w[1]);
        
        //右上點
        double xtop;
        double ytop;
        //左下點
        double xbot;
        double ybot;
        
        //右上判定
        if(((m*ay)+300) <= 600){
        	xtop = (m*ay)+300;
        	ytop = 0;
        }else{
        	xtop = 600;
        	ytop = ay-(300/m);
        }
        
        //左下判定
        if((300-((600-ay)*m)) > 0){
        	xbot = 300-((600-ay)*m);
        	ybot = 600;
        }else{
        	xbot = 0;
        	ybot = ay+(300/m);
        }
                
        g.setColor(Color.green);
        g.drawLine((int)xtop, (int)ytop, (int)xbot, (int)ybot);
    }
	
	private static double[] select(double x0 , double [] x1 , double [] x2 , double [] e , double [] w , double l,int s){
		int k = 0;
		//紀錄最高的次數
		int count = 0;
		//紀錄兩個不同的值
		double max = e[0];
		double min = e[0];
		for(int i = 0 ; i < e.length ; i++){
			if(e[i] > max){
				max = e[i];
			}
			if(e[i] < min){
				min = e[i];
			}
		}
		
		//packet
		double [] packet = new double [w.length];
		for(int i = 0 ; i < s ; i++){
			while(k < (int)(x1.length*2)/3){
				
				int a = 0;
				//訓練的正確次數
				int countnow = 0;
				
				if((x0*w[0]+x1[k]*w[1]+x2[k]*w[2]) >= 0){
					if(e[k] == min){
						while((x0*w[0]+x1[k]*w[1]+x2[k]*w[2]) >= 0){
							w[0] = sub(w[0],(l*x0));
							w[1] = sub(w[1],(l*x1[k]));
							w[2] = sub(w[2],(l*x2[k]));
						}
						while(a < (int)(x1.length*2)/3){
							if((x0*w[0]+x1[a]*w[1]+x2[a]*w[2]) >= 0 && e[a] == max ){
								countnow++;
							}
							if((x0*w[0]+x1[a]*w[1]+x2[a]*w[2]) < 0 && e[a] == min ){
								countnow++;
							}
							a++;
						}
						if(countnow > count){
							count = countnow;
							//新的W值放入口袋中
							System.arraycopy(w, 0, packet, 0, w.length);
						}
					}
					k++;
				}else if((x0*w[0]+x1[k]*w[1]+x2[k]*w[2]) < 0){
					if(e[k] == max){
						while((x0*w[0]+x1[k]*w[1]+x2[k]*w[2]) < 0){
							w[0] = add(w[0],(l*x0));
							w[1] = add(w[1],(l*x1[k]));
							w[2] = add(w[2],(l*x2[k]));
						}

						while(a < (int)(x1.length*2)/3){
							if((x0*w[0]+x1[a]*w[1]+x2[a]*w[2]) >= 0 && e[a] == max ){
								countnow++;
							}
							if((x0*w[0]+x1[a]*w[1]+x2[a]*w[2]) < 0 && e[a] == min ){
								countnow++;
							}
							a++;
						}
						if(countnow > count){
							count = countnow;
							System.arraycopy(w, 0, packet, 0, w.length);
						}
					}
					k++;
				}
			}
			k = 0;
		}
		
		//測試正確次數
		int testcount = 0;
		for(int i = (int)((2*x1.length)/3) ; i < x1.length ; i++){
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) >= 0 && e[i] == max ){
				testcount++;
			}
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) < 0 && e[i] == min ){
				testcount++;
					
			}
		}
		
		//所有正確次數
		int endcount = 0;
		for(int i = 0 ; i < x1.length ; i++){
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) >= 0 && e[i] == max ){
				endcount++;
			}
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) < 0 && e[i] == min ){
				endcount++;
					
			}
		}
		
		//訓練正確率
		double learnsucess= (double)count / (int)((2*x1.length)/3);
		double learnsucess1 = new BigDecimal(learnsucess).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		//測試正確率
		double testsucess = (double)testcount / (x1.length-(int)((2*x1.length)/3));
		double testsucess1 = new BigDecimal(testsucess).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		
		//辨識率
		double sucess =(double)endcount / (double)x1.length;
		double sucess1 = new BigDecimal(sucess).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("訓練出的鍵結值： {"+packet[0]+" , "+packet[1]+" , "+packet[2]+"}");
		System.out.println("訓練正確率： " + learnsucess1*100 + " % ");
		System.out.println("測試正確率： " + testsucess1*100 + " % ");
		System.out.println("辨識率： "+ sucess1*100 + " % ");
		return packet;
		
	}
	
	public static double add(double v1,double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	public static double sub(double v1,double v2) {
	    BigDecimal b1 = new BigDecimal(Double.toString(v1));
	    BigDecimal b2 = new BigDecimal(Double.toString(v2));
	    return b1.subtract(b2).doubleValue();
	}
}

