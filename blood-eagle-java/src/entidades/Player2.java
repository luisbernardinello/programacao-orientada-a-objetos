package entidades;

import static estadosdojogo.Jogando.*;
import static util.Constantes.ANIMA_VELOC;
import static util.Constantes.GRAVIDADE;
import static util.Constantes.Direcao.BAIXO;
import static util.Constantes.Direcao.ESQUERDA;
import static util.Constantes.Direcao.DIREITA;
import static util.Constantes.Direcao.CIMA;
import static util.Constantes.ConstantesPlayer2.*;
import static util.Constantes.ConstantesPlayer.*;
import static util.MetodosAuxiliares.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import nivel.GerenciaLevel;
import principal.Game;
import util.CarregaSprite;

import javax.imageio.ImageIO;

import entidades.Player;
import entidades.Player2;
import estadosdojogo.Jogando;

public class Player2 extends Entidade {
	private BufferedImage[][] animations;
	private int animaAtual, animaIndexMax, animaVeloc = 25;
	private int playerAction = P2_PARADO;
	private int playerAttack;
	private boolean movimento = false, atacando = false;
	private boolean esquerda, cima, direita, baixo, salto;
	private float playerVeloc = 2.0f;
	private int[][] lvlDados;
	private float xDrawOffset = 21 * Game.ESCALA;
	private float yDrawOffset = 6 * Game.ESCALA;
	private boolean ataqueVerificado;
	private Jogando playing;
	private GerenciaLevel gerenciaLevel;
	
	//gravidade
	
	private float arVeloc = 0f;
	private float gravidade = 0.04f * Game.ESCALA;
	private float saltoVeloc = -2.25f * Game.ESCALA;
	private float velocQuedaAposColidir = 0.5f * Game.ESCALA; // no caso de bater no teto por exemplo
	private boolean noAr = false;

	private BufferedImage barraDeVidaImg;
	
	private int barraDeVidaImgComprimento = (int) (192 * Game.ESCALA);
	private int barraDeVidaImgAltura = (int) (38 * Game.ESCALA);
	private int barraDeVidaImgX = (int) (550 * Game.ESCALA);
	private int barraDeVidaImgY = (int) (10 * Game.ESCALA);

	private int barraDeVidaComprimento = (int) (150 * Game.ESCALA);
	private int barraDeVidaAltura = (int) (4 * Game.ESCALA);
	private int barraDeVidaXInicial = (int) (34 * Game.ESCALA);
	private int barraDeVidaYInicial = (int) (14 * Game.ESCALA);

	private int vidaMax = 100;
	private int vidaAtual = vidaMax;
	private int vidaComprimento = barraDeVidaComprimento;
	
	//attackbox
	
	private Rectangle2D.Float attackBoxP2;
	
	private int inverteX = 0;
	private int inverteComprimento = 1;
	private int tileY = 0;

	
	public Player2 (float x, float y, int comprimento, int altura, Jogando playing) {
		
		super(x, y, comprimento, altura);
		this.playing = playing;
		this.estado = P2_PARADO;
		this.vidaMax = 100;
		this.vidaAtual = vidaMax;
		carregaAnima();
		iniciaHitBox(20,29);
		iniciaAttackBoxP2();
	}
	
	private void iniciaAttackBoxP2() {
		attackBoxP2 = new Rectangle2D.Float(x, y, (int) (10 * Game.ESCALA), (int) (10 * Game.ESCALA));
		resetaAttackBoxP2();

	}		
	
	
	public void update() {
		
		updateBarraDeVidaP2();
		
		if(vidaAtual <= 0) {
			if(estado != P2_MORTO) {
				estado = P2_MORTO;
				animaAtual=0;
				animaIndexMax=0;
				playing.setPlayerMorrendo(true);
				
				// verifica se o player2 morreu no ar
				if (!EntidadeNoChao(hitbox, lvlDados)) {
					noAr = true;
					arVeloc = 0;
				}
			}else if (animaIndexMax == quantidadeSprites(P2_MORTO) - 1 && animaAtual >= ANIMA_VELOC -1) 
				playing.setGameOver(true);

			
			else {
				updateAnimaAtual();
				
				if (noAr) 
					if (MovimentoPermitido(hitbox.x, hitbox.y + arVeloc, hitbox.width, hitbox.height, lvlDados)) {
						hitbox.y += arVeloc;
						arVeloc += GRAVIDADE;
					} else
						noAr = false;
		}
				return;
	}
		
		updateAttackBoxP2();
		
			updatePos();
		
		if(movimento)
			tileY = (int) (hitbox.y / Game.TILES_TAMANHO);
		
		if(atacando)
			verificaAtaque();
		
		updateAnimaAtual();
		setAnima();

		
	}
	
	private void verificaAtaque() {
		if(ataqueVerificado || animaIndexMax != 2)
			return;
		ataqueVerificado = true;
		
		playing.verificaHitP1(attackBoxP2);
	}
	
	private void setAttackBoxDireita() {
		attackBoxP2.x = hitbox.x + hitbox.width - (int) (Game.ESCALA * 5);
	}

	private void setAttackBoxEsquerda() {
		attackBoxP2.x = hitbox.x - hitbox.width - (int) (Game.ESCALA * 10);
	}


	private void updateAttackBoxP2() {
		if (direita && esquerda) {
			if (inverteComprimento == 1) {
				setAttackBoxDireita();
			} else {
				setAttackBoxEsquerda();
			}
		}
			else if (direita || ( inverteComprimento == 1))
				setAttackBoxDireita();
			else if (esquerda || ( inverteComprimento == -1))
				setAttackBoxEsquerda();

		
		attackBoxP2.y = hitbox.y + (Game.ESCALA * 10);
	}
	
	private void updateBarraDeVidaP2() {
		vidaComprimento = (int)((vidaAtual / (float)vidaMax)* barraDeVidaComprimento);
		
	}


	
	public void renderiza(Graphics g, int deslocamentoLevelX) {
		g.drawImage(animations[estado][animaIndexMax], (int) (hitbox.x - xDrawOffset) - deslocamentoLevelX + inverteX, (int) (hitbox.y - yDrawOffset), comprimento * inverteComprimento, altura, null);

		desenhaBarraDeVida(g);
	}
	
	public void desenhaAttackBox(Graphics g, int deslocamentoLevelX) {
		g.setColor(Color.red);
		g.drawRect((int)attackBoxP2.x - deslocamentoLevelX, (int)attackBoxP2.y, (int)attackBoxP2.width, (int)attackBoxP2.height);
	}

	private void desenhaBarraDeVida(Graphics g) {
		g.drawImage(barraDeVidaImg, barraDeVidaImgX, barraDeVidaImgY, barraDeVidaImgComprimento, barraDeVidaImgAltura, null);
		g.setColor(Color.green);
		g.fillRect(barraDeVidaXInicial + barraDeVidaImgX, barraDeVidaYInicial + barraDeVidaImgY, vidaComprimento, barraDeVidaAltura);
	}

	private void updateAnimaAtual() {
		animaAtual++;
		if (animaAtual >= ANIMA_VELOC) {
			animaAtual = 0;
			animaIndexMax++;
			if (animaIndexMax >= quantidadeSpritesP2(estado)) {
				animaIndexMax = 0;
				atacando = false;
				ataqueVerificado= false;
				if (estado == P2_HIT) {
					novoEstado(P2_PARADO);
					arVeloc = 0f;
					if (!IsFloor(hitbox, 0, lvlDados))
						noAr = true;
				}
			}
			
		}

	}

	private void setAnima() {
		int startAnima = estado;
		if(estado == P2_HIT)
			return;
		
		if (movimento)
			estado = P2_CORRENDO;
		else
			estado = P2_PARADO;
		
		if(noAr) {

				estado = P2_SALTO;
		
		}
		
		if(atacando) {
			estado = P2_ATAQUE_1;
			if(startAnima != P2_ATAQUE_1) {
				animaIndexMax = 1;
				animaAtual = 0;
				return;
			}
		}
		
		if(startAnima != estado) {
			resetaAnimaAtual();
		}
	}

	private void resetaAnimaAtual() {
		animaAtual=0;
		animaIndexMax=0;
		
	}

	private void updatePos() {
		
		
		movimento = false;
		
		if(salto)
			salto();
		if (!esquerda && !direita && !noAr)
			return;

		float xVeloc = 0; 

		if (esquerda) {
			xVeloc -= playerVeloc;
			inverteX = comprimento;// largura atual que estamos desenhando
			inverteComprimento = -1;
		}
		if (direita) {
			xVeloc += playerVeloc;
			inverteX = 0;
			inverteComprimento = 1;
		}
		if(!noAr) 
			if(!EntidadeNoChao(hitbox, lvlDados)) // nao esta no chao, esta no ar
				noAr = true;
			
				
		
		
		if(noAr) {
			//logica do boneco no ar, aumenta velocidade no chao e diminui no ar
			if(MovimentoPermitido(hitbox.x, hitbox.y + arVeloc, hitbox.width, hitbox.height, lvlDados)) {
				hitbox.y += arVeloc;//aumenta com o tempo
				arVeloc += gravidade;
				updateXPos(xVeloc);
			}else {
				hitbox.y = GetEntidadeYDentroLimiteMapa(hitbox, arVeloc);
				if(arVeloc > 0) //caindo e vamos para o chao	
					resetaNoAr();
				else
					arVeloc = velocQuedaAposColidir;
				updateXPos(xVeloc);
				
			}
			
		
		}else 
			updateXPos(xVeloc);
		
		movimento = true;
			

	}
	
	private void salto() {
		if(noAr)
			return;
		noAr = true;
		arVeloc = saltoVeloc;
	}

	private void resetaNoAr() {
		noAr = false;
		arVeloc = 0;
	}

	private void updateXPos(float xVeloc) {
			if (MovimentoPermitido(hitbox.x + xVeloc, hitbox.y, hitbox.width, hitbox.height, lvlDados)) {
				hitbox.x += xVeloc;

			}else {
				hitbox.x = GetEntidadeXProxCanto(hitbox, xVeloc);
			}
	}
	
	public void alteraVidaP2(int quantidade) {
		vidaAtual += quantidade;
		
		if(vidaAtual <= 0) {
			vidaAtual = 0;
			//gameover()
		}else if(vidaAtual >= vidaMax)// nao passar o valor maximo
			vidaAtual = vidaMax;
	}


	public void morteP2() {
		vidaAtual = 0;
	}

		
	private void carregaAnima() {

		BufferedImage img = CarregaSprite.carregaAtlasSprite(CarregaSprite.PLAYER2ATLAS);

		animations = new BufferedImage[10][8];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 96, j * 96, 96, 96);
		
		barraDeVidaImg = CarregaSprite.carregaAtlasSprite(CarregaSprite.BARRADEHP);

	}

	public void carregaDadosLvl(int[][] lvlDados) {
		this.lvlDados = lvlDados;
		if(!EntidadeNoChao(hitbox, lvlDados))
			noAr = true;
	}
	
	public void resetDireBool() {
		esquerda = false;
		direita = false;
		cima = false;
		baixo = false;
		salto = false;
	}
	
	public void setAtacando(boolean atacando) {
		this.atacando = atacando;
		
	}
	
	public void apanhou(int quantidade) {
		vidaAtual -= quantidade;
		if(vidaAtual <= 0)
			novoEstado(P2_MORTO);
		else
			novoEstado(P2_HIT);
		
	}
	
	protected void novoEstado(int estado) {
		this.estado = estado;
		animaAtual=0;
		animaIndexMax=0;
	}
	

	
	public void resetaTudo() {
		resetDireBool();
		noAr = false;
		atacando = false;
		movimento = false;
		estado = P2_PARADO;
		vidaAtual = vidaMax;

		hitbox.x = x;
		hitbox.y = y;
		resetaAttackBoxP2();

		if (!EntidadeNoChao(hitbox, lvlDados))
			noAr = true;
	}
	
	private void resetaAttackBoxP2() {
		if (inverteComprimento == 1)
			setAttackBoxDireita();
		else
			setAttackBoxEsquerda();
	}

	

	public boolean isEsquerda() {
		return esquerda;
	}

	public void setEsquerda(boolean esquerda) {
		this.esquerda = esquerda;
	}

	public boolean isCima() {
		return cima;
	}

	public void setCima(boolean cima) {
		this.cima = cima;
	}

	public boolean isDireita() {
		return direita;
	}

	public void setDireita(boolean direita) {
		this.direita = direita;
	}

	public boolean isBaixo() {
		return baixo;
	}

	public void setBaixo(boolean baixo) {
		this.baixo = baixo;
	}
	public void setSalto(boolean salto) {
		this.salto = salto;
	}
}