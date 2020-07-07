package hopefield;

import java.util.ArrayList;
import java.util.Arrays;

public class Hopefield {
	int [][] winput;
	int n;
	int p;
	Hopefield() {
		
	}
	
	public void setwData(ArrayList<String []> temp){
		String [][] temp1 = ArraylisttoString(temp);		
		winput = change(temp1);
		n = winput.length;
		p = winput[0].length;
	}
	
	public double [][] makew(){
		double [][] temp1 = mul(winput[0]);
		for(int i = 1 ; i < n ; i++){
			double [][] temp2 = mul(winput[i]);
			temp1 = add(temp1, temp2);
		}
		double [][] b = new double[p][p];
		for(int i = 0 ; i < p ; i++){
			for(int j = 0 ; j < p ; j++){
				if(i == j){
					b[i][j] = (temp1[i][j] - n);
				}else{
					b[i][j] = temp1[i][j];
				}
			}
		}
		return b;
	}
	
	public static double [] maketheta(double [][] a){
		double [] b = new double [a.length];
		for(int i = 0 ; i < a.length ; i++){
			for(int j = 0 ; j < a[i].length ; j++){
				b[i] += a[i][j];
			}
		}
		return b;
	}
	
	public int [][] recall(ArrayList<String[]> a, double [][] w, double [] theta){
		String [][] tempString = ArraylisttoString(a);
		int [][] temp = change(tempString);
		int [][] output = new int [temp.length][temp[0].length];
		for(int i = 0 ; i < temp.length ; i++){
			int [] temp1 = Arrays.copyOf(temp[i], temp[i].length);
			int [] temp2 = Arrays.copyOf(temp1, temp1.length);
			do{
				temp2 = Arrays.copyOf(temp1, temp1.length);
				temp1 = recallmethod(temp2, w, theta);
			}while(!check(temp1, temp2));
			output[i] = Arrays.copyOf(temp1, temp1.length);	
		}
		return output;
	}
	
	public int [] recallmethod(int [] a, double [][] w, double [] theta){
		int [] d = new int [a.length];
		for(int i = 0 ; i < w.length ; i++){
			double temp = 0;
			for(int j = 0 ; j < w[i].length ; j++){
				temp += (w[i][j]*a[j]);
			}
			int temp1 = Double.compare(temp, theta[i]);
			if(temp1 > 0){
				d[i] = 1;
			}else if(temp1 < 0){
				d[i] = -1;
			}else{
				d[i] = a[i];
			}
		}
		return d;
	}
	
	public static double [][] mul(int[]a){
		double [][] c = new double [a.length][a.length];
		for(int i = 0 ; i < a.length ; i++){
			for(int j = 0 ; j < a.length ; j++){
				c[i][j] = (double)a[i]*a[j];
			}
		}
		return c;
	}
	
	public static double [][] add(double[][]a,double[][]b){
		for(int i = 0; i < a.length ; i++){
			for(int j = 0 ; j < a[i].length ; j++){
				a[i][j] += b[i][j]; 
			}
		}
		return a;
	}
	
	public int [][] change(String [][] a){
		int [][] b = new int [a.length][a[0].length*a[0][0].length()];
		StringBuffer sb;
		String [] temp = new String[a.length];
		for(int i = 0 ; i < a.length ; i++){
			sb = new StringBuffer();
			for(int j = 0 ; j < a[i].length ; j++){
				sb.append(a[i][j]);
			}
			temp[i] = sb.toString();
		}
		for(int i = 0 ; i < temp.length ; i++){
			for(int j = 0 ; j < temp[i].length() ; j++){
				if(Integer.parseInt(temp[i].substring(j, j+1)) == 0){
					b[i][j] = -1;
				}else{
					b[i][j] = 1;
				}
			}
		}
		return b;
	}
	
	public String [][] ArraylisttoString(ArrayList<String[]> a){
		String [][] b = new String[a.size()][a.get(0).length];
		for(int i = 0 ; i < b.length ; i++){
			for(int j = 0 ; j < b[i].length ; j++){
				b[i][j] = a.get(i)[j];
			}
		}
		return b;
	}
	
	public boolean check(int [] a , int [] b){
		boolean c = true;
		for(int i = 0 ; i < a.length ; i++){
			if(a[i] != b[i]){
				c = false;
			}
		}
		return c;
	}
}
