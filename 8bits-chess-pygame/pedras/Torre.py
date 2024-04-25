import pygame

from pedras.Pedra import Pedra

class Torre(Pedra): #mesmo esquema das demais classes
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)

        caminhoImagem = 'img/' + cor[0] + ' torre.png'
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'T'
        # Largura e altura de cada "tile"
        tileLargura = 16
        tileAltura = 16

        # Número total de "tiles" na imagem
        numeroTiles = 4

        # Lista para armazenar os "tiles" recortados da imagem
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

        moveLeste = []
        for x in range(self.x + 1, 8):
            moveLeste.append(tabuleiro.pegaQuadradoPosicao(
                (x, self.y)
            ))
        saidaMetodo.append(moveLeste)

        moveSul = []
        for y in range(self.y + 1, 8):
            moveSul.append(tabuleiro.pegaQuadradoPosicao(
                (self.x, y)
            ))
        saidaMetodo.append(moveSul)

        moveOeste = []
        for x in range(self.x)[::-1]:
            moveOeste.append(tabuleiro.pegaQuadradoPosicao(
                (x, self.y)
            ))
        saidaMetodo.append(moveOeste)

        return saidaMetodo
