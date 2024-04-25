package principal;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class GameJanela {
	private JFrame jframe;

	public GameJanela(GamePainel gamePanel) {

		jframe = new JFrame();

		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.add(gamePanel);
		
		jframe.setResizable(false);
		jframe.pack();
		
		jframe.setLocationRelativeTo(null);
		
		jframe.setVisible(true);
		//nao perder o foco da tela
		jframe.addWindowFocusListener(new WindowFocusListener() {
			
	

			@Override
			public void windowGainedFocus(WindowEvent e) {
				gamePanel.getGame().percaFocoJanela();			}

			@Override
			public void windowLostFocus(WindowEvent e) {
				
			}});
	}

}