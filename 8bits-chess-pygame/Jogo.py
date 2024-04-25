import pygame
import time
from Tabuleiro import Tabuleiro
from pedras.Bispo import Bispo
from pedras.Cavalo import Cavalo
from pedras.Peao import Peao
from pedras.Rainha import Rainha
from pedras.Rei import Rei
from pedras.Torre import Torre
from pygame.time import get_ticks



pygame.init()

altura = 512
largura = 512
JANELA = (altura, largura)
tela = pygame.display.set_mode(JANELA)  # cria janela

tabuleiro = Tabuleiro(altura, largura)  # instancia da classe para tabuleiro do jogo
pygame.display.set_caption('Pragas do Egito')

# tempo atual
tempoAtual = pygame.time.get_ticks()

def desenha(display):
    tabuleiro.desenha(display)  # desenha tabuleiro e as pedras
    pygame.display.update()  # atualiza janela

def exibeMensagemVitoria(mensagem):
    pygame.font.init()
    fonte = pygame.font.Font(None, 64)
    texto = fonte.render(mensagem, True, (255, 0, 0))
    textoRect = texto.get_rect()
    textoRect.center = (largura // 2, altura // 2)
    tela.blit(texto, textoRect)
    pygame.display.update()
    pygame.time.wait(2000)  # Aguarda 2 segundos antes de fechar

if __name__ == '__main__':
    jogando = True

    while jogando:
        mx, my = pygame.mouse.get_pos()
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                jogando = False
            elif event.type == pygame.MOUSEBUTTONDOWN:
                if event.button == 1:
                    tabuleiro.manipulaCliqueMouse(mx, my)

        
        # atualiza o quadro das pedras animadas
        for pedra in tabuleiro.pedras:
             if isinstance(pedra, Bispo) or isinstance(pedra, Cavalo) or isinstance(pedra, Peao) or isinstance(pedra, Rainha) or isinstance(pedra, Rei) or isinstance(pedra, Torre):
                pedra.atualizarFrame(tempoAtual)

        # Atualize o tempo atual
        tempoAtual = pygame.time.get_ticks()

        if tabuleiro.emXequeMate('black'):
            mensagemVitoria = 'Brancas ganharam!'
            exibeMensagemVitoria(mensagemVitoria)
            jogando = False
        elif tabuleiro.emXequeMate('white'):
            mensagemVitoria = 'Pretas ganharam!'
            exibeMensagemVitoria(mensagemVitoria)
            jogando = False

        
        tempoAtual = pygame.time.get_ticks()
    

        desenha(tela)
