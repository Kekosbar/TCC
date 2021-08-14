package script;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import script.agaprendizagem.AgEvolutivo;
import script.animal.Macaco;
import script.arquivar.Arquivar;

public class AG {

    private Ambiente ambiente;
    private final int iteracoes; // Número de vezes que cada animal se movimenta no ambiente a cada geração
    private final int eliminar;
    private AgEvolutivo evolutivo;
    private int AG_TYPE;
    // Variáveis para contabilizar tempo de execução
    private Instant start;
    private Instant finish;
    
    public AG(Ambiente ambiente, int iteracoes, int eliminar, int AG_TYPE) {
        this.ambiente = ambiente;
        this.iteracoes = iteracoes;
        this.eliminar = eliminar;
        this.AG_TYPE = AG_TYPE;
    }
    
    public void iniciar(){
        start = Instant.now();
        try {
            Arquivar arquivar = new Arquivar(ambiente);
            evolutivo = new AgEvolutivo(ambiente, arquivar, iteracoes, eliminar);
            while(true){
                // Varias interações ocorrem com movimentações dos animais, disparos de alarmes e etc.
                evolutivo.processaGeracao();
                 //Vefica se o AG já atingiu o resultado final
                if(verificaFim(ambiente.getMacacos())){
                    System.out.println("Encontrou a solução");
                    break;
                }
                evolutivo.novaGeracao();
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
        System.out.println("Total de Gerações: "+(evolutivo.getGeracao() + 1));
        System.out.println("Total de Iterações: "+evolutivo.getCountIteracoes());
        System.out.println("Tempo de execução: "+timeExecution()+"ms");
    }
    
    public long timeExecution(){
        return Duration.between(start, finish).toMillis();  //in millis
    }
}
