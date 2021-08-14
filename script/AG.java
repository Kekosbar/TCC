package script;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import script.agaprendizagem.AgAprendizagem;
import script.agaprendizagem.AgEvolutivo;
import script.agaprendizagem.AgHibrido;
import script.agaprendizagem.AgReforco;
import script.animal.Macaco;
import script.arquivar.Arquivar;

public class AG {

    private Ambiente ambiente;
    private final int iteracoes; // Número de vezes que cada animal se movimenta no ambiente a cada geração
    private final int eliminar;
    private AgAprendizagem agAprendizagem;
    // Variáveis para contabilizar tempo de execução
    private Instant start;
    private Instant finish;
    
    public AG(Ambiente ambiente, int iteracoes, int eliminar) {
        this.ambiente = ambiente;
        this.iteracoes = iteracoes;
        this.eliminar = eliminar;
    }
    
    public void iniciar(){
        start = Instant.now();
        try {
            Arquivar arquivar = new Arquivar(ambiente);
            agAprendizagem = instanciaAlgoritmo(arquivar);
            while(true){
                // Varias interações ocorrem com movimentações dos animais, disparos de alarmes e etc.
                agAprendizagem.processaGeracao();
                 //Vefica se o AG já atingiu o resultado final
                if(verificaFim(ambiente.getMacacos())){
                    System.out.println("Encontrou a solução");
                    break;
                }
                agAprendizagem.novaGeracao();
                zerarMortesMacacos();
            }
            arquivar.finalizaArquivosCSV(ambiente.getMacacos());
        } catch (IOException ex) {
            Logger.getLogger(AG.class.getName()).log(Level.SEVERE, null, ex);
        }
        finish = Instant.now();
    }
    
    private boolean verificaFim(Macaco macacos[]){
        for(Macaco macaco : macacos)
            if(macaco.getMortes() > 0)
                return false;
        return true;
    }
    
    private void zerarMortesMacacos(){
        Macaco[] macacos = ambiente.getMacacos();
        for(Macaco m : macacos){
            m.zerarMortes();
        }
    }
    
    public void imprimeDadosFinais(){
        System.out.println();
        System.out.println("Total de Gerações: "+(agAprendizagem.getGeracao() + 1));
        System.out.println("Total de Iterações: "+agAprendizagem.getCountIteracoes());
        System.out.println("Tempo de execução: "+timeExecution()+"ms");
    }
    
    public AgAprendizagem instanciaAlgoritmo(Arquivar arquivar){
        return Principal.AG_TYPE == AgAprendizagem.EVOLUTIVO ? new AgEvolutivo(ambiente, arquivar, iteracoes, eliminar)
             : Principal.AG_TYPE == AgAprendizagem.REFORCO ? new AgReforco(ambiente, arquivar, iteracoes)
             : new AgHibrido(ambiente, arquivar, iteracoes, eliminar);
    }

    public long timeExecution(){
        return Duration.between(start, finish).toMillis();  //in millis
    }
}
