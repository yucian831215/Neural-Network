package test;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Perceptron {
	ArrayList<double[]> data = new ArrayList();
	double l;
	double c;
	double x0 = -1;
	double [] x1;
	double [] x2;
	double [] e;
	double[] w;
	double max;
	double min;
	double testl = 0.2;
	double testc = 100;

	public void iData(double learn, double convergence) {
		l = learn;
		c = convergence;
		
		double[] change1 = new double[data.get(0).length];
		double[] change2 = new double[data.get(0).length];
		for (int i = 0; i < data.size()*3; i++) {
			int index1 = (int)(Math.random()*data.size());
			int index2 = (int)(Math.random()*data.size());
			change1 = data.get(index1);
			change2 = data.get(index2);
			data.set(index1,change2);
			data.set(index2,change1);
		}
		
		x1 = new double [data.size()];
		x2 = new double [data.size()];
		e = new double [data.size()];
		for(int i = 0 ; i < data.size() ; i++){
			x1[i] = data.get(i)[0];
			x2[i] = data.get(i)[1];
			e[i] = data.get(i)[2];
		}
		
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
	}

	public void setData(ArrayList<double[]> temp) {
		data = temp;
		w = new double[data.get(0).length];		
	}

	public double[] train() {	
		//初始W值
		for (int i = 0; i < w.length; i++) {
			w[i] = (int) Math.random() * 2 - 1;
		}
		System.out.print("初始鍵結值： ");
		print(w);
		
		//控制學習率
		if(l == 0 || l < 0){
			l = testl;
		}
		if(c == 0 || c < 0){
			c = testc;
		}
		
		int k = 0;
		//紀錄訓練最高次數
		int count = 0;
		//口袋
		double [] packet = new double [w.length];
		
		for(int i = 0 ; i < c ; i++){
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
		System.arraycopy(packet, 0, w, 0, w.length);
		System.out.print("最終鍵結值 : ");
		print(w);
		return w;
	}
	
	//訓練正確率
	public double learn(){
		//訓練正確次數
		int learncount = 0;
		for(int i = 0 ; i < (int)((2*x1.length)/3) ; i++){
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) >= 0 && e[i] == max ){
				learncount++;
			}
			if((x0*w[0]+x1[i]*w[1]+x2[i]*w[2]) < 0 && e[i] == min ){
				learncount++;							
			}
		}
		double learnsucess = (double)learncount / (int)((2*x1.length)/3);
		return learnsucess*100;
	}
	
	
	//測試正確率
	public double test(){
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
		//測試正確率
		double testsucess = (double)testcount / (x1.length-(int)((2*x1.length)/3));
		return testsucess*100;
	}
	
	//辨識率
	public double alltest() {
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
		//辨識率
		double sucess =(double)endcount / (double)x1.length;
		return sucess*100;
	}
	

	public static void print(double[] t) {

		for (int i = 0; i < t.length; i++) {
			System.out.print(t[i] + " ");
		}
		System.out.println();
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
