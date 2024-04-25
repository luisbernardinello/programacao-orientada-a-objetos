import pygame

from pedras.Pedra import Pedra

class Cavalo(Pedra):
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)

        caminhoImagem = 'img/' + cor[0] + ' cavalo.png'#esquema que vi em um video para criar o caminho do arquivo com base na cor, sendo o indice         
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'C'
       
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
        if tempo_atual - self.frameContador > 200:  
            self.frameAtual = (self.frameAtual + 1) % len(self.tiles)
            self.frameContador = tempo_atual
            self.img = self.tiles[self.frameAtual]  



    def movimentosPossiveis(self, tabuleiro):
        saidaMetodo = []
        movimentos = [
            (1, -2), # norte
            (2, -1),
            (2, 1),
            (1, 2),
            (-1, 2),
            (-2, 1),
            (-2, -1),
            (-1, -2)
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
    
