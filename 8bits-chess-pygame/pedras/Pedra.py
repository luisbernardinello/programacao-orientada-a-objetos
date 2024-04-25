
class Pedra:
    def __init__(self, posicao, cor, tabuleiro):
        self.posicao = posicao
        self.x = posicao[0] #garante que o x vai ser a coordenada x do vetor
        self.y = posicao[1]
        self.cor = cor
        self.movimentou = False #bool para ver se ja movimentou

    def movimenta(self, tabuleiro, quadrado):#metodo para verificar se ja movimentou
        for i in tabuleiro.quadrados:
            i.destacado = False #isso e para nao bugar os quadrados destacados, assim ele garante tirar o que nao for destacado

        if quadrado in self.pegaMovimentosValidos(tabuleiro):
            quadradoAnterior = tabuleiro.pegaQuadradoPosicao(self.posicao)
            # atualiza a posição da pedra e as coord no caso do clique.
            self.posicao, self.x, self.y = quadrado.posicao, quadrado.x, quadrado.y

             # atualiza o quadrado que a pedra estava e o quadrado para qual ela vai
            quadradoAnterior.pedraOcupando = None
            quadrado.pedraOcupando = self
            tabuleiro.pedraSelecionada = None #limpa a variavel
            self.movimentou = True

            return True
        else:
            tabuleiro.pedraSelecionada = None
            return False # movimento não é válido.
    
    def pegaMovimentos(self, tabuleiro):
    # retorna todos os movimentos que podem ser realizados para a pedra.
        saidaMetodo = []
        for direcao in self.movimentosPossiveis(tabuleiro): #chama os movimentosPossiveis e varre os quadrados na direcao
            for quadrado in direcao:
                if quadrado.pedraOcupando is not None:
                    if quadrado.pedraOcupando.cor == self.cor:
                        break # barra o movimento da pedra caso exista colisao com uma pedra da mesma cor.
                    else:
                        saidaMetodo.append(quadrado)
                        break # adiciona pelo append o quadrado para a saidaMetodo mas a continuidade da direcao de possibilidades de movimento e interrompida para nao mostrar com se a pedra pudesse continuar.
                else:
                    saidaMetodo.append(quadrado) #quadrado vazio, adicionado a lista de saidaMetodo como movimento permitido
        return saidaMetodo #possibilidade de todos os movimento

    def pegaMovimentosValidos(self, tabuleiro):
        saidaMetodo = []
        for quadrado in self.pegaMovimentos(tabuleiro):
            if not tabuleiro.emXeque(self.cor, modificacaoTabuleiro=[self.posicao, quadrado.posicao]):
                saidaMetodo.append(quadrado) #se o rei nao estiver em xeque, movimento vai para saidaMetodo

        return saidaMetodo #possibilidade de movimentos validos levando em conta se o rei esta em xeque

    def quadradosAtacando(self, tabuleiro):
        return self.pegaMovimentos(tabuleiro) #retorna todos os quadrados que a pedra esta atacando (movimentos possiveis)
