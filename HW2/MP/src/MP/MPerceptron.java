package MP;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.xml.crypto.Data;

public class MPerceptron {
	
	ArrayList<double[]> data = new ArrayList();
	double [][] data2;
	double l;
	double c;
	double max;
	double min;
	double reg;
	double regNu;
	int last; //��������ȩҦb����m
	double [] y; //�Ĥ@�h���üh��X
	double o; //��X�h��X
	double delta1;
	double delta2;
	double delta3;
	double delta4;
	double [] w1;
	double [] w2;
	double [] w3;
	double [] w4;
	double aven;
	double allen;
	double en;
	double top;
	double bottom;
	
	public void iData(double learn, double convergence){
		l = learn;
		c = convergence;
		
		//�~�P�t��k
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
		
		data2 = new double [data.size()][data.get(0).length];
		
		for(int i = 0 ; i < data.size() ; i ++){
			for (int j = 0; j < data.get(0).length; j++) {
				data2[i][j] = data.get(i)[j];
			}
		}
		
		for(int j = 0 ; j < data2[0].length-1 ; j++){
			max = 0;
			min = 1000000000;
			for (int i = 0; i < data2.length ; i++) {
				if(data2[i][j] > max){
					max = data2[i][j];
				}
				if(data2[i][j] < min){
					min = data2[i][j];
				}
			}
			reg = max - min;
			for (int i = 0; i < data2.length ; i++) {
				regNu = (data2[i][j] - min)/reg;
				data2[i][j] = regNu;
			}
		}
		
		//��̤j�̤p��
		last = (data.get(0).length-1);
		max = data.get(0)[last];
		min = data.get(0)[last];
		for(int i = 0 ; i < data.size() ; i ++){
			if(max < data.get(i)[last]){
				max = data.get(i)[last];
			}
			if(min > data.get(i)[last]){
				min = data.get(i)[last];
			}
		}
	}
	
	public void setData(ArrayList<double[]> temp) {
		data = temp;		
	}
	
	public void train(){
		//�O�ĴX�����
		int k = 0;
		int i = 0;
		aven = 100;
		//��J�Ӽ�
		int num = data.get(0).length;
		
		//�Ĥ@�h���üh�T�ӯ��g��
		w1 = new double [num];
		w2 = new double [num];
		w3 = new double [num];
		//��X�h�@�ӯ��g��
		w4 = new double [4];
		
		//���ï��g�����X�h���T�ӿ�J
		y = new  double[3];
		
		w1 = random(w1);
		w2 = random(w2);
		w3 = random(w3);	
		w4 = random(w4);
		
		while(i < 10000 && aven > c){
			allen = 0;
			while(k < (data.size()*2/3)){
				
				y[0] = front1(w1, k);
				y[1] = front1(w2, k);
				y[2] = front1(w3, k);
				o = front2(w4, y);
				
				top = (data.get(k)[last]-min+1)/(max-min+1);
				bottom = (data.get(k)[last]-min)/(max-min+1);
				
				en = lms(o, k);
				
				if(en > c){
					delta4 = back2(k);
					delta1 = back1(y[0], delta4, w4[1]);
					delta2 = back1(y[1], delta4, w4[2]);
					delta3 = back1(y[2], delta4, w4[3]);
					
					w1 = modify1(w1, delta1, k);
					w2 = modify1(w2, delta2, k);
					w3 = modify1(w3, delta3, k);
					w4 = modify2(w4, delta4);
				}
				allen += lms(o, k);
				k++;
			}
			aven = allen / (data.size()*2/3);
			System.out.println(i + " " +aven);
			k = 0;
			i++;
		}
		i = 0;	
	}
	
	//�V�m���T�v
	public double traintest(){
		int traincount = 0;
		for(int i = 0 ; i < (data.size()*2/3); i++){
			y[0] = front1(w1,i);
			y[1] = front1(w2,i);
			y[2] = front1(w3,i);
			o = front2(w4, y);
			top = (data.get(i)[last]-min+1)/(max-min+1);
			bottom = (data.get(i)[last]-min)/(max-min+1);
			if(o > bottom && o < top){
				traincount++;
			}
		}
		double success = (double)traincount / (double)(data.size()*2/3);
		return success*100;
	}
	
	//���ե��T�v
		public double test(){
			int testcount = 0;
			for(int i = (data.size()*2/3) ; i < data.size(); i++){
				y[0] = front1(w1,i);
				y[1] = front1(w2,i);
				y[2] = front1(w3,i);
				o = front2(w4, y);
				top = (data.get(i)[last]-min+1)/(max-min+1);
				bottom = (data.get(i)[last]-min)/(max-min+1);
				if(o > bottom && o < top){
					testcount++;
				}
			}
			double success = (double)testcount / (double)(data.size()-(data.size()*2/3));
			return success*100;
		}	
	
	//���Ѳv
	public double alltest(){
		int endcount = 0;
		for(int i = 0 ; i < data.size(); i++){
			y[0] = front1(w1,i);
			y[1] = front1(w2,i);
			y[2] = front1(w3,i);
			o = front2(w4, y);
			top = (data.get(i)[last]-min+1)/(max-min+1);
			bottom = (data.get(i)[last]-min)/(max-min+1);
			if(o > bottom && o < top){
				endcount++;
			}
		}
		double success = (double)endcount / (double)data.size();
		return success*100;
	}
	
	//����t
	public double avlms(){
		double alllms = 0;
		for(int i = 0 ; i < data.size(); i++){
			y[0] = front1(w1,i);
			y[1] = front1(w2,i);
			y[2] = front1(w3,i);
			o = front2(w4, y);
			alllms += lms(o, i);
		}
		return alllms / data.size();
	}
	
	//���ëe�X��k
	public double front1(double [] w,int k){
		double result = (w[0] * (-1));
		for(int i = 0 ; i < last ; i++){
			result += (w[i+1]*data2[k][i]); 
		}
		result = Math.exp(((-1)*result));
		result += 1;
		return (1/result);
	}
	
	//��X�h�e�X��k
	public double front2(double [] w,double [] y){
		double result = (w[0] * (-1));
		for(int i = 0 ; i < y.length ; i++){
			result += (w[i+1]*y[i]); 
		}
		result = Math.exp(((-1)*result));
		result += 1;
		return (1/result);
	}
	
	//��X�h�˶ǻ���k
	public double back2(int k){
		double result = (data.get(k)[last] - min) / (max - min);
		result = result - o;
		result = result * o * (1-o);
		return result;
	}
	
	//���üh�˶ǻ���k
	public double back1(double y,double delta,double w){
		double result = delta * w;
		result = result * y * (1-y);
		return result;
	}
	
	//�ק��䵲�Ȥ�k(��X�h)
	public double [] modify2(double [] w , double delta){
		w[0] = w[0] + (l*delta*(-1));
		for(int i = 0 ; i < y.length ; i++){
			w[i+1] = w[i+1] + (l*delta*y[i]);
		}
		return w;
	}
	
	//�ק��䵲�Ȥ�k(���üh)
	public double[] modify1(double []w,double delta,int k){
		w[0] = w[0] +(l*delta*(-1));
		for(int i = 0 ; i < last ; i++){
			w[i+1] = w[i+1] + (l*delta*data2[k][i]);
		}
		return w;
	}
	
	//�~�t
	public double lms(double y,int k){
		//�зǤ�
		double d = (data.get(k)[last] - min) / (max - min);
		double result = y - d;
		result = result*result;
		result = result/2;
		return result;		
	}	
	
	//�䵲�ȶüƲ���
	public double [] random(double [] w){
		for(int i = 0 ; i < w.length ; i++){
			w[i] = new BigDecimal(((Math.random()*4)-2)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return w;
	}
}
