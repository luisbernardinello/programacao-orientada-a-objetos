import pygame

class Quadrado:
    def __init__(self, x, y, largura, altura):#x e y coor , largura e altura do
        self.x = x
        self.y = y #coord do quadrado
        self.largura = largura
        self.altura = altura #altura e largura do quadrado

        self.xAbs = x * largura #multiplica coord do quadrado pela largura e altura de cada quadrado para calc coord absoluta
        self.yAbs = y * altura
        self.posicaoAbs = (self.xAbs, self.yAbs) #armazena posicao absoluta, posicao da dimensao do quadrado para a coord, preenchendo conforme a matriz do tabuleiro
        self.posicao = (x, y) #armazena posicao do quadrado
        self.cor = 'claro' if (x + y) % 2 == 0 else 'escuro' #se soma for par claro se nao escuro
        self.desenhaCor = (0, 128, 255) if self.cor == 'claro' else (53, 53, 53)
        self.corDestaque = (124, 252, 0) if self.cor == 'claro' else (170, 255, 0)
        self.pedraOcupando = None #quadrado inicial sem nenhuma pedra
        self.coord = self.pegaCoordenadas() #armazena as coord em nomenclatura oficial do xadrez, a1, b1 e etc
        self.destacado = False

        self.rect = pygame.Rect( #sera o retangulo da pygame, na verdade o quadrado da tela para verificar colisoes
            self.xAbs,
            self.yAbs,
            self.largura,
            self.altura
        )


    def pegaCoordenadas(self):
        colunas = 'abcdefgh' #string com letras da nomenclatura das casas 
        return colunas[self.x] + str(self.y + 1) #combina letra da coluna com o numero da linha


    def desenha(self, display): #recebe a superficie e desenha o quadrado
        if self.destacado:
            pygame.draw.rect(display, self.corDestaque, self.rect, 4) #desenha o quadrado em destaque da jogada
        else:
            pygame.draw.rect(display, self.desenhaCor, self.rect) #desenha o quadrado normal

        if self.pedraOcupando != None:
            centralizaRect = self.pedraOcupando.img.get_rect() #centraliza a imagem no quadrado usando o retangulo da imagem da pedra
            centralizaRect.center = self.rect.center #define o centro do retangulo da pedra para ser o mesmo do retangulo do quadrado
            display.blit(self.pedraOcupando.img, centralizaRect.topleft) #desenha a imagem da pedra com o retangulo centralizado
            #center e topleft sao metodos do pygame

            