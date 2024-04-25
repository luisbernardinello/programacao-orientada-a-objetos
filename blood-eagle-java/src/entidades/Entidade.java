package entidades;

import static util.Constantes.Direcao.BAIXO;
import static util.Constantes.Direcao.ESQUERDA;
import static util.Constantes.Direcao.CIMA;
import static util.MetodosAuxiliares.MovimentoPermitido;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import principal.Game;



public abstract class Entidade {

	protected float x, y;
	protected int comprimento, altura;
	protected Rectangle2D.Float hitbox;
	protected int animaAtual, animaIndexMax;
	protected int estado;
	protected float arVeloc;
	protected boolean noAr = false;
	protected int vidaMax;
	protected int vidaAtual;
	protected Rectangle2D.Float attackBox;
	protected float andaVeloc;



	public Entidade(float x, float y, int comprimento, int altura) {
		this.x = x;
		this.y = y;
		this.comprimento = comprimento;
		this.altura = altura;

	}
	
	protected void desenhaAttackBox(Graphics g, int deslocamentoLevelX) {
		g.setColor(Color.red);
		g.drawRect((int) (attackBox.x - deslocamentoLevelX), (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
	}

	protected void desenhaHitBox(Graphics g, int deslocamentoLevelX) {
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - deslocamentoLevelX, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	protected void iniciaHitBox(int comprimento, int altura) {
		hitbox = new Rectangle2D.Float(x, y, (int) (comprimento * Game.ESCALA), (int) (altura * Game.ESCALA));
	}

	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}

	public int getEstado() {
		return estado;
	}

	public int getAnimaIndex() {
		return animaIndexMax;
	}

	protected void novoEstado(int estado) {
		this.estado = estado;
		animaAtual = 0;
		animaIndexMax = 0;
	}
}