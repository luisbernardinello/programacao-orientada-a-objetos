import pygame

from pedras.Pedra import Pedra

class Rainha(Pedra): #mesmo esquema das demais classes
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)

        caminhoImagem = 'img/' + cor[0] + ' rainha.png'
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'Q'
       
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

        moveNorte = []
        for y in range(self.y)[::-1]:
            moveNorte.append(tabuleiro.pegaQuadradoPosicao(
                (self.x, y)
            ))
        saidaMetodo.append(moveNorte)

        moveNordeste = []
        for i in range(1, 8):
            if self.x + i > 7 or self.y - i < 0:
                break
            moveNordeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x + i, self.y - i)
            ))
        saidaMetodo.append(moveNordeste)

        moveLeste = []
        for x in range(self.x + 1, 8):
            moveLeste.append(tabuleiro.pegaQuadradoPosicao(
                (x, self.y)
            ))
        saidaMetodo.append(moveLeste)

        moveSudeste = []
        for i in range(1, 8):
            if self.x + i > 7 or self.y + i > 7:
                break
            moveSudeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x + i, self.y + i)
            ))
        saidaMetodo.append(moveSudeste)

        moveSul = []
        for y in range(self.y + 1, 8):
            moveSul.append(tabuleiro.pegaQuadradoPosicao(
                (self.x, y)
            ))
        saidaMetodo.append(moveSul)

        moveSudoeste = []
        for i in range(1, 8):
            if self.x - i < 0 or self.y + i > 7:
                break
            moveSudoeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x - i, self.y + i)
            ))
        saidaMetodo.append(moveSudoeste)

        moveOeste = []
        for x in range(self.x)[::-1]:
            moveOeste.append(tabuleiro.pegaQuadradoPosicao(
                (x, self.y)
            ))
        saidaMetodo.append(moveOeste)

        moveNoroeste = []
        for i in range(1, 8):
            if self.x - i < 0 or self.y - i < 0:
                break
            moveNoroeste.append(tabuleiro.pegaQuadradoPosicao(
                (self.x - i, self.y - i)
            ))
        saidaMetodo.append(moveNoroeste)

        return saidaMetodo