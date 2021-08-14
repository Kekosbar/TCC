package script.agaprendizagem;

import java.util.Arrays;
import java.util.Random;

import script.Ambiente;
import script.animal.Macaco;
import script.arquivar.Arquivar;

public class AgEvolutivo {

  private Ambiente ambiente;
  private Arquivar arquivar;
  private final int iteracoes; // Número de vezes que cada animal se movimenta no ambiente a cada geração
  private final int eliminar;
  private int contIteracoes = 0; // Contabiliza o total de iterações efetuadas por cada animal
  private int geracao = 0; // Contabiliza as gerações do algoritmo evolutivo

  public AgEvolutivo(Ambiente ambiente, Arquivar arquivar, int iteracoes, int eliminar) {
    this.ambiente = ambiente;
    this.arquivar = arquivar;
    this.iteracoes = iteracoes;
    this.eliminar = eliminar;
  }

  public void processaGeracao() {
    // Varias interações ocorrem com movimentações dos animais, disparos de alarmes e etc.
    for (int i = 0; i < iteracoes; i++) {
      ambiente.moverTodosAnimaisAleatorio();
      arquivar.escreveArquivosIteracao(ambiente);
      contIteracoes++;
    }
    arquivar.escreveArquivosGeracao(ambiente, geracao);
  }

  public void novaGeracao() {
    // Esta função irá ordenar o vetor de macacos do mais apto ao menos apto
    Arrays.sort(ambiente.getMacacos());
    // Esta função ira gerar uma nova geração de individuos mantendo os mais aptos e
    // substituindo os menos aptos
    int n_Macacos = ambiente.getN_Macacos(); // Numero de macacos no
    // int eliminar = n_Macacos * corte / 100; // Numero de individuos que serão
    // eliminados
    int index = n_Macacos - eliminar;
    Macaco macacos[] = ambiente.getMacacos();
    Random r = new Random();
    for (int i = 0; i < eliminar; i++) {
      int m1 = r.nextInt(n_Macacos - eliminar);
      int m2 = r.nextInt(n_Macacos - eliminar);
      if (macacos[index].getMortes() != 0) {
        macacos[index].zerarMortes();
        float[][] novoSigno = cruzamento(macacos[m1], macacos[m2]);
        macacos[index++].setSignos(novoSigno);
      }
    }
    geracao++;
  }

  private float[][] cruzamento(Macaco m1, Macaco m2) {
    // Função que irá cruzar dois individuos e mistura seus DNAs, no caso o vetor
    // "signos"
    float[][] novo = new float[10][3];
    float[][] s1 = m1.getSignos();
    float[][] s2 = m2.getSignos();
    // Seleciona o maior signo em cada posição da matriz
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 3; j++) {
        if (s1[i][j] > s2[i][j])
          novo[i][j] = s1[i][j];
        else
          novo[i][j] = s2[i][j];
      }
    }
    return novo;
  }

  public int getGeracao(){
    return geracao;
  }

  public int getCountIteracoes(){
    return contIteracoes;
  }

}
