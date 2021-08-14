package script.agaprendizagem;

import script.Ambiente;
import script.arquivar.Arquivar;

public class AgHibrido extends AgAprendizagem {
  
  public AgHibrido(Ambiente ambiente, Arquivar arquivar, int iteracoes) {
    super(ambiente, arquivar, iteracoes);
    //TODO Auto-generated constructor stub
  }

  @Override
  public void novaGeracao() {
    // TODO Auto-generated method stub
    geracao++;
  }
}
