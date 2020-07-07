package hopefield;

import java.awt.*;

public class codepaint extends Canvas{
	int [][] input;
	boolean check;
	codepaint() {
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
	
	public void setData(String [] s){
		input = new int[s.length][s[0].length()];
		for(int i = 0 ; i < s.length ; i++){
			for(int j = 0 ; j < s[i].length() ; j++){
				input[i][j] = Integer.parseInt(s[i].substring(j, j+1));
			}
		}
		check = true;
		repaint();
	}
}
