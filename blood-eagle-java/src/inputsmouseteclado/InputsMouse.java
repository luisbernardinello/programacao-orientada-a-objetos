package inputsmouseteclado;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import principal.GamePainel;

public class InputsMouse implements MouseListener, MouseMotionListener {

	private GamePainel gamePanel;

	public InputsMouse(GamePainel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton()== MouseEvent.BUTTON1)
			gamePanel.getGame().getJogando().mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}