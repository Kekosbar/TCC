package script.agaprendizagem;

import script.Ambiente;
import script.arquivar.Arquivar;

public class AgHibrido extends AgAprendizagem {

  private AgEvolutivo agEvolutivo;
  
  public AgHibrido(Ambiente ambiente, Arquivar arquivar, int iteracoes, int eliminar) {
    super(ambiente, arquivar, iteracoes);
    this.agEvolutivo = new AgEvolutivo(ambiente, arquivar, iteracoes, eliminar);
  }

  @Override
  public void novaGeracao() {
    agEvolutivo.novaGeracao();
    geracao++;
  }
}
