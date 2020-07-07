package test;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Arc2D.Float;
import java.util.ArrayList;

public class coodpaint extends Canvas {
	ArrayList<double[]> data = new ArrayList();
	double[] w = new double[1];

	public coodpaint() {
		double[] fill = new double[1];

		data.add(fill);
	}

	public void paint(Graphics g) {
		g.drawLine(320, 0, 320, 640);
		g.drawLine(0,320,640,320);
		
		if(data.get(0).length == 3){

			//尋找距離原點最大的值，用來改變圖的間距
	        double amax = Math.abs(data.get(0)[0]);
	        for(int i = 0 ; i < data.size() ; i++ ){
	        	if(amax < Math.abs(data.get(i)[0])){
	        		amax = Math.abs(data.get(i)[0]);
	        	}
	        	if(amax < Math.abs(data.get(i)[1])){
	        		amax = Math.abs(data.get(i)[1]);
	        	}
	        }
	        //期望值
	        double max = data.get(0)[2];
	        double min = data.get(0)[2];
	        for(int i = 0 ; i < data.size() ; i++ ){
	        	if(max < Math.abs(data.get(i)[2])){
	        		max = data.get(i)[2];
	        	}
	        	if(min > Math.abs(data.get(i)[2])){
	        		min = data.get(i)[2];
	        	}
	        }
			if(w.length == 3){
				double ay = 0;
		        
		        //分割線
		        if(amax <= 5){
		            ay = Math.abs(320-((w[0]/w[2])*60));
		        }else if(amax <= 10){
		            ay = Math.abs(320-((w[0]/w[2])*30));
		        }else if(amax <= 30){
		            ay = Math.abs(320-((w[0]/w[2])*10));
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
		        if(((m*ay)+320) <= 640){
		        	xtop = (m*ay)+320;
		        	ytop = 0;
		        }else{
		        	xtop = 640;
		        	ytop = ay-(320/m);
		        }
		        
		        //左下判定
		        if((320-((640-ay)*m)) > 0){
		        	xbot = 320-((640-ay)*m);
		        	ybot = 640;
		        }else{
		        	xbot = 0;
		        	ybot = ay+(320/m);
		        }
		                
		        g.setColor(Color.green);
		        g.drawLine((int)xtop, (int)ytop, (int)xbot, (int)ybot);
			}
			
	        
	        //畫點
	        for(int i = 0 ; i < data.size() ; i++){
	        	if(amax <= 5){
	        		int x = (int)(data.get(i)[0]*60);
	            	int y = (int)(data.get(i)[1]*60);

	            	if(data.get(i)[2] == max){
	    				g.setColor(Color.blue);
	            		g.drawString("O", x+316, Math.abs(325-y));
	            	}else if (data.get(i)[2] == min){
	            		g.setColor(Color.red);
	            		g.drawString("X", x+316, Math.abs(325-y));
	            	}
	        	}else if(amax <= 10){
	        		int x = (int)(data.get(i)[0]*30);
	            	int y = (int)(data.get(i)[1]*30);

	            	if(data.get(i)[2] == max){
	    				g.setColor(Color.blue);
	            		g.drawString("o", x+318, Math.abs(323-y));
	            	}else if (data.get(i)[2] == min){
	            		g.setColor(Color.red);
	            		g.drawString("x", x+318, Math.abs(323-y));
	            	}
	        		}else if(amax <= 30){
	        		int x = (int)(data.get(i)[0]*10);
	            	int y = (int)(data.get(i)[1]*10);
	            
	            	if(data.get(i)[2] == max){
	    				g.setColor(Color.blue);
	            		g.drawString(".", x+320, Math.abs(320-y));
	            	}else if (data.get(i)[2] == min){
	            		g.setColor(Color.red);
	            		g.drawString(".", x+320, Math.abs(320-y));
	            	}
	        	}
			}
		}
	}

	public void drawData(ArrayList<double[]> temp) {
		data = temp;
		repaint();
	}

	public void drawLine(double[] temp) {
		w = temp;
		repaint();
	}
}