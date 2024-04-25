import pygame

from Quadrado import Quadrado
from pedras.Torre import Torre
from pedras.Bispo import Bispo
from pedras.Cavalo import Cavalo
from pedras.Rainha import Rainha
from pedras.Rei import Rei
from pedras.Peao import Peao

class Tabuleiro:
    def __init__(self, largura, altura):
        # inicializa o tabuleiro com a largura e altura especificadas
        self.largura = largura
        self.altura = altura
        self.tileLargura = largura // 8  # calcula o tamanho dos quadrados do tabuleiro
        self.tileAltura = altura // 8
        self.pedraSelecionada = None  #nenhuma pedra selecionada
        self.turno = 'white'  # comeca com as brancas
        self.turno_mensagem = None
        self.turno_mensagem_timer = 0
        self.pedras = []
        
        # config do tabuleiro que serao colocadas as pedras pela sting
        self.config = [
            ['bT', 'bC', 'bB', 'bQ', 'bK', 'bB', 'bC', 'bT'],
            ['bP', 'bP', 'bP', 'bP', 'bP', 'bP', 'bP', 'bP'],
            [''  ,''   ,''   ,''   ,''   ,''   ,''   ,''   ],
            [''  ,''   ,''   ,''   ,''   ,''   ,''   ,''   ],
            [''  ,''   ,''   ,''   ,''   ,''   ,''   ,''   ],
            [''  ,''   ,''   ,''   ,''   ,''   ,''   ,''   ],
            ['wP', 'wP', 'wP', 'wP', 'wP', 'wP', 'wP', 'wP'],
            ['wT', 'wC', 'wB', 'wQ', 'wK', 'wB', 'wC', 'wT'],
        ]

        # cria os  quadrados do tabuleiro
        self.quadrados = self.criaQuadrados()

        # configuração inicial do tabuleiro
        self.configTabuleiro()

    def criaQuadrados(self):
        # cria os quadrados do tabuleiro como objetos da classe Quadrado
        saidaMetodo = []
        for y in range(8):
            for x in range(8):
                saidaMetodo.append(
                    Quadrado(x,  y, self.tileLargura, self.tileAltura)
                )
        return saidaMetodo

    def pegaQuadradoPosicao(self, posicao):
        # retorna o objeto quadrado referente a uma posição (x, y)
        for quadrado in self.quadrados:
            if (quadrado.x, quadrado.y) == (posicao[0], posicao[1]):
                return quadrado

    def pegaPedraPosicao(self, posicao):
        # retorna a pedra (objeto de pedra) em uma posição (x, y)
        return self.pegaQuadradoPosicao(posicao).pedraOcupando

    def configTabuleiro(self):
        # configura  o tabuleiro pela config inicial
        for y, linha in enumerate(self.config):
            for x, pedra in enumerate(linha):
                if pedra:
                    quadrado = self.pegaQuadradoPosicao((x, y))
                    
                    # cria objetos de pedra (Torre, Cavalo, Bispo, Rainha, Rei, Peão) com base na config e joga no tabuleiro da config
                    if pedra[1] == 'T':
                        quadrado.pedraOcupando = Torre(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    elif pedra[1] == 'C':
                        quadrado.pedraOcupando = Cavalo(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    elif pedra[1] == 'B':
                        quadrado.pedraOcupando = Bispo(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    elif pedra[1] == 'Q':
                        quadrado.pedraOcupando = Rainha(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    elif pedra[1] == 'K':
                        quadrado.pedraOcupando = Rei(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    elif pedra[1] == 'P':
                        quadrado.pedraOcupando = Peao(
                            (x, y), 'white' if pedra[0] == 'w' else 'black', self
                        )
                    self.pedras.append(quadrado.pedraOcupando)

    def manipulaCliqueMouse(self, mx, my):
        # coord do clique do mouse
        x = mx // self.tileLargura
        y = my // self.tileAltura
        quadradoClicado = self.pegaQuadradoPosicao((x, y))

        if self.pedraSelecionada is None:
            # se nenhuma pedra selecionada, verifica se tem uma pedra na posicao clicada
            if quadradoClicado.pedraOcupando is not None:
                if quadradoClicado.pedraOcupando.cor == self.turno:
                    self.pedraSelecionada = quadradoClicado.pedraOcupando
        elif self.pedraSelecionada.movimenta(self, quadradoClicado):
            # se uma pedra tiver sido selecionada, tenta movimentar para a posicao clicada
            self.turno = 'white' if self.turno == 'black' else 'black'
            self.turno_mensagem = f"{self.turno} turn: Select a stone to move"
            self.turno_mensagem_timer = pygame.time.get_ticks() + 1500  # 1500 ms (1.5 segundos)
        elif quadradoClicado.pedraOcupando is not None:
            # verifica se a pedra clicada e da cor do jogador
            if quadradoClicado.pedraOcupando.cor == self.turno:
                self.pedraSelecionada = quadradoClicado.pedraOcupando

    def emXeque(self, cor, modificacaoTabuleiro=None): 
        # verifica se o rei está em xeque
        saidaMetodo = False
        posicaoRei = None

        alterandoPedra = None
        quadradoAntigo = None
        quadradoNovo = None
        quadradoNovoPedraAntiga = None

        if modificacaoTabuleiro is not None:
            # Se houver modific no tabuleiro
            for quadrado in self.quadrados:
                if quadrado.posicao == modificacaoTabuleiro[0]:
                    alterandoPedra = quadrado.pedraOcupando
                    quadradoAntigo = quadrado
                    quadradoAntigo.pedraOcupando = None
            for quadrado in self.quadrados:
                if quadrado.posicao == modificacaoTabuleiro[1]:
                    quadradoNovo = quadrado
                    quadradoNovoPedraAntiga = quadradoNovo.pedraOcupando
                    quadradoNovo.pedraOcupando = alterandoPedra
        #pega todas as pedras do tabuleiro
        pedras = [i.pedraOcupando for i in self.quadrados if i.pedraOcupando is not None]
        #se a pedra sendo movida for o rei, armazena a posicao
        if alterandoPedra is not None:
            if alterandoPedra.elemento == 'K':
                posicaoRei = quadradoNovo.posicao
        if posicaoRei is None:
            for pedra in pedras:
                if pedra.elemento == 'K' and pedra.cor == cor:
                    posicaoRei = pedra.posicao
        #verifica se existe pedra adversaria dando xeque no rei            
        for pedra in pedras:
            if pedra.cor != cor:
                for quadrado in pedra.quadradosAtacando(self):
                    if quadrado.posicao == posicaoRei:
                        saidaMetodo = True

        if modificacaoTabuleiro is not None:
            # anula modificacao temporaria que existiu para validacoes
            quadradoAntigo.pedraOcupando = alterandoPedra
            quadradoNovo.pedraOcupando = quadradoNovoPedraAntiga
                        
        return saidaMetodo

    def emXequeMate(self, cor):
        # Verifica se o jogador está em xeque-mate
        saidaMetodo = False
        #seleciona o rei pela cor
        for pedra in [i.pedraOcupando for i in self.quadrados]:
            if pedra is not None:
                if pedra.elemento == 'K' and pedra.cor == cor:
                    rei = pedra
        #rei sem moviemntos validos e em xeque, xeque mate
        if rei.pegaMovimentosValidos(self) == []:
            if self.emXeque(cor):
                saidaMetodo = True

        return saidaMetodo

    def desenha(self, display):
        # Desenha o tabuleiro e as peças
        if self.pedraSelecionada is not None:
            # se uma pedra estiver selecionada destaca o quadrado pela borda e os movimentos valido.
            self.pegaQuadradoPosicao(self.pedraSelecionada.posicao).destacado = True
            for quadrado in self.pedraSelecionada.pegaMovimentosValidos(self):
                quadrado.destacado = True

        for quadrado in self.quadrados:
            # varre todos os quadrados do tabuleiro e desenha todos
            quadrado.desenha(display)
            texto = None 
        if self.turno_mensagem_timer > pygame.time.get_ticks():
            # se existir msg para exibir
            font = pygame.font.Font(None, 36)  #tamanho 36, sem fonte especifica
            texto = font.render(self.turno_mensagem, True, (124, 252, 0))
            textoRect = texto.get_rect()
            textoRect.center = (self.largura // 2, self.altura // 2)  # posiciona o texto no centro da tela

        if texto is not None:  # se o texto não for nulo
            display.blit(texto, textoRect)
