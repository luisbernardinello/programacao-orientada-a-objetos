import pygame

from pedras.Pedra import Pedra

class Rei(Pedra): #mesmo esquema das demais classes 
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)

        caminhoImagem = 'img/' + cor[0] + ' rei.png'
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'K'
        
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
        if tempo_atual - self.frameContador > 200:  # atualize com o valor de tempo escolhido
            self.frameAtual = (self.frameAtual + 1) % len(self.tiles)
            self.frameContador = tempo_atual
            self.img = self.tiles[self.frameAtual]  # atualiza a imagem para a prox tile

    def movimentosPossiveis(self, tabuleiro):
        saidaMetodo = []
        movimentos = [
            (0,-1), # norte
            (1, -1), # nordeste
            (1, 0), # leste
            (1, 1), # sudeste
            (0, 1), # sul
            (-1, 1), # sudoeste
            (-1, 0), # oeste
            (-1, -1), # noroeste
        ]

        for movimento in movimentos:
            novaPosicao = (self.x + movimento[0], self.y + movimento[1])
            if (
                novaPosicao[0] < 8 and
                novaPosicao[0] >= 0 and 
                novaPosicao[1] < 8 and 
                novaPosicao[1] >= 0
            ):
                saidaMetodo.append([
                    tabuleiro.pegaQuadradoPosicao(
                        novaPosicao
                    )
                ])

        return saidaMetodo

