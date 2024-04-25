import pygame

from pedras.Pedra import Pedra

#subclasse de Pedra, herda Pedra
class Peao(Pedra):
    def __init__(self, posicao, cor, tabuleiro):
        super().__init__(posicao, cor, tabuleiro)#chama o construtor da classe Pedra e inicializa a classe

     
        caminhoImagem = 'img/' + cor[0] + ' peao.png'#esquema que vi em um video para criar o caminho do arquivo com base na cor, sendo o indice 0         
        self.img = pygame.image.load(caminhoImagem)
        self.elemento = 'P'
        # largura e altura da tile 
        tileLargura = 16
        tileAltura = 16

        # quantidade de tiles
        numeroTiles = 5

        # lista para armazenar as tiles
        self.tiles = []

        for i in range(numeroTiles-1):
            x = i * tileLargura
            y = 0 #nao tem y por eu ter cortado a imagem

            if x + tileLargura <= self.img.get_width():
                tileRect = pygame.Rect(x, y, tileLargura, tileAltura)
                tile = self.img.subsurface(tileRect)
                tile = pygame.transform.scale(tile, (tabuleiro.tileLargura - 20, tabuleiro.tileAltura - 20))
                self.tiles.append(tile)

        self.frameAtual = 0
        self.frameContador = 0

    def atualizarFrame(self, tempo_atual):
        if tempo_atual - self.frameContador > 200:  # atualize com o valor de tempo
            self.frameAtual = (self.frameAtual + 1) % len(self.tiles) #frame incrementado em 1 e o len garante que o range vai permanecer dentro da quantidade de tiles
            self.frameContador = tempo_atual
            self.img = self.tiles[self.frameAtual]  #a tualiza a imagem para o prox tile


    def movimentosPossiveis(self, tabuleiro):
        saidaMetodo = []
        movimentos = []

        # move forward
        if self.cor == 'white':
            movimentos.append((0, -1))
            if not self.movimentou:
                movimentos.append((0, -2))

        elif self.cor == 'black':
            movimentos.append((0, 1))
            if not self.movimentou:
                movimentos.append((0, 2))

        for movimento in movimentos:
            novaPosicao = (self.x, self.y + movimento[1])
            if novaPosicao[1] < 8 and novaPosicao[1] >= 0: #verifica se a nova posicao ta dentro do limite do tabuleiro
                saidaMetodo.append(
                    tabuleiro.pegaQuadradoPosicao(novaPosicao)
                )

        return saidaMetodo

   #o peao possui mais um metodo por conta de ser uma pedra que nao possui o movimento definido, comeca andando duas casas depois somente uma e somente uma direcao caso seja brancas se move para cima, caso sejam pretas se move para baixo. por isso difere movimentos possiveis e movimentos validos

    def pegaMovimentos(self, tabuleiro): 
        saidaMetodo = [] #lista vazia
        for quadrado in self.movimentosPossiveis(tabuleiro): #varre a lista dos movimentosPossiveis
            if quadrado.pedraOcupando != None: #verifica se o quadrado esta ocupado por uma pedra
                break
            else:
                saidaMetodo.append(quadrado) #quadrado vazio, movimento valido

        if self.cor == 'white':
            if self.x + 1 < 8 and self.y - 1 >= 0: #se a pedra do peao for branca verifica se pode se mover para cima diagonal e para direita
                quadrado = tabuleiro.pegaQuadradoPosicao(
                    (self.x + 1, self.y - 1)
                )
                if quadrado.pedraOcupando != None:  #verifica se existe pedra no quadrado
                    if quadrado.pedraOcupando.cor != self.cor: #verifica se a pedra e de outra cor
                        saidaMetodo.append(quadrado) #adiciona o quadrado para a lista de movimentos validos caputrando na diagonal

            if self.x - 1 >= 0 and self.y - 1 >= 0: #se a pedra do peao for branca verifica se pode se mover para cima diagonal e para esquerda 
                quadrado = tabuleiro.pegaQuadradoPosicao(
                    (self.x - 1, self.y - 1)
                )
                if quadrado.pedraOcupando != None:
                    if quadrado.pedraOcupando.cor != self.cor:
                        saidaMetodo.append(quadrado) #quadrado na lista saidaMetodo como moviemnto valido capturando na diagonal

        elif self.cor == 'black': #mesma logica do peao branco porem movimento contrario, ou seja, para baixo ao inves de para cima
            if self.x + 1 < 8 and self.y + 1 < 8:
                quadrado = tabuleiro.pegaQuadradoPosicao(
                    (self.x + 1, self.y + 1)
                )
                if quadrado.pedraOcupando != None:
                    if quadrado.pedraOcupando.cor != self.cor:
                        saidaMetodo.append(quadrado)
            if self.x - 1 >= 0 and self.y + 1 < 8:
                quadrado = tabuleiro.pegaQuadradoPosicao(
                    (self.x - 1, self.y + 1)
                )
                if quadrado.pedraOcupando != None:
                    if quadrado.pedraOcupando.cor != self.cor:
                        saidaMetodo.append(quadrado)

        return saidaMetodo

