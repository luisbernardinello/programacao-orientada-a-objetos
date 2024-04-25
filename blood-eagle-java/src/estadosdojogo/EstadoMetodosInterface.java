package estadosdojogo;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface EstadoMetodosInterface {
	public void update();

	public void desenha(Graphics g);

	public void mouseClicked(MouseEvent e);

	public void mousePressionado(MouseEvent e);

	public void mouseSolto(MouseEvent e);

	public void mouseMovimento(MouseEvent e);

	public void teclaPressionada(KeyEvent e);

	public void teclaSolta(KeyEvent e);

}