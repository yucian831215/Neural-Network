package hopefield;

import java.awt.*;

public class codepaint1 extends Canvas{
	int [][] input;
	boolean check;
	codepaint1() {
		check = false;
	}
	public void paint(Graphics g){
		super.paint(g);
		for(int i = 0 ;  i <= 315 ; i+=35){
			g.drawLine(i, 0, i, 455);
		}
		for(int i = 0 ;  i <= 455 ; i+=35){
			g.drawLine(0, i, 315, i);
		}
		if(check){
			for(int i = 0 ; i < input.length ; i++){
				for(int j = 0 ; j < input[i].length ; j++){
					if(input[i][j] == 1){
						g.setColor(Color.BLACK);
						g.fillRect(j*35, i*35,35,35);
					}else{
						g.setColor(Color.white);
						g.fillRect(j*35, i*35,35,35);
					}
					g.setColor(Color.BLACK);
					g.drawRect(j*35, i*35,35,35);
				}
			}
		}
	}
	
	public void setData(int [] a){
		input = new int [(a.length/9)][9];
		for(int i = 0 ; i < input.length ; i++){
			for(int j = 0 ; j < input[i].length ; j++){
				if(a[i*9+j] == -1){
					input[i][j] = 0;
				}else{
					input[i][j] = 1;
				}
			}
		}
		check = true;
		repaint();
	}
}
