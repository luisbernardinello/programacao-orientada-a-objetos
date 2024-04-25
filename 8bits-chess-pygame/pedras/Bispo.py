import pygame

from pedras.Pedra import Pedra


class Bispo(Pedra):
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)

        caminhoImagem = 'img/' + cor[0] + ' bispo.png'
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'B'
        
        tileLargura = 16
        tileAltura = 16

       
        numeroTiles = 4


        self.tiles = []

        for i in range(numeroTiles-1):
            x = i * tileLargura
            y = 0

            if x + tileLargura <= self.img.get_width():
                tileRect = pygame.Rect(x, y, tileLargura, tileAltura)
                tile = self.img.subsurface(tileRect)
                tile = pygame.transform.scale(tile, (tabuleiro.tileLargura - 20, tabuleiro.tileAltura - 20))
                self.tiles.append(tile)

        self.frameAtual = 0
        self.frameContador = 0

    def atualizarFrame(self, tempo_atual):
        if tempo_atual - self.frameContador > 200:  # Atualize com o valor de tempo adequado
            self.frameAtual = (self.frameAtual + 1) % len(self.tiles)
            self.frameContador = tempo_atual
            self.img = self.tiles[self.frameAtual]  # Atualiza a imagem do bispo com o próximo "tile"



    def movimentosPossiveis(self, tabuleiro):
        saidaMetodo = []

        moveNordeste = []
        for i in range(1, 8):
            if self.x + i > 7 or self.y - i < 0:
                break #permanece dentro do tabuleiro
            moveNordeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x + i, self.y - i)
            ))
        saidaMetodo.append(moveNordeste)

        moveSudeste = []
        for i in range(1, 8):
            if self.x + i > 7 or self.y + i > 7:
                break
            moveSudeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x + i, self.y + i)
            ))
        saidaMetodo.append(moveSudeste)

        moveSudoeste = []
        for i in range(1, 8):
            if self.x - i < 0 or self.y + i > 7:
                break
            moveSudoeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x - i, self.y + i)
            ))
        saidaMetodo.append(moveSudoeste)

        moveNoroeste = []
        for i in range(1, 8):
            if self.x - i < 0 or self.y - i < 0:
                break
            moveNoroeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x - i, self.y - i)
            ))
        saidaMetodo.append(moveNoroeste)

        return saidaMetodo
