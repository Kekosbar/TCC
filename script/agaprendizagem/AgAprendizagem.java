package script.agaprendizagem;

import script.Ambiente;
import script.arquivar.Arquivar;

public abstract class AgAprendizagem {

  public static final int EVOLUTIVO = 0;
  public static final int REFORCO = 1;
  public static final int HIBRIDO = 2;

  protected Ambiente ambiente;
  protected Arquivar arquivar;
  protected final int iteracoes; // Número de vezes que cada animal se movimenta no ambiente a cada geração
  protected int contIteracoes = 0; // Contabiliza o total de iterações efetuadas por cada animal
  protected int geracao = 0; // Contabiliza as gerações do algoritmo evolutivo
  
  public abstract void novaGeracao();

  public AgAprendizagem(Ambiente ambiente, Arquivar arquivar, int iteracoes){
    this.ambiente = ambiente;
    this.arquivar = arquivar;
    this.iteracoes = iteracoes;
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

  public int getGeracao(){
    return geracao;
  }

  public int getCountIteracoes(){
    return contIteracoes;
  }
}
