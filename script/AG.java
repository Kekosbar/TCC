package script;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AG {

    private Ambiente ambiente;
    private final int iteracoes; // Número de vezes que cada animal se movimenta no ambiente a cada geração
    private int contIteracoes = 0; // Contabiliza o total de iterações efetuadas por cada animal
    private final int eliminar;
    private int geracao = 0; // Contabiliza as gerações do algoritmo evolutivo
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
            while(true){
                // Varias interações ocorrem com movimentações dos animais, disparos de alarmes e etc.
                for(int i=0; i<iteracoes; i++){
                    ambiente.moverTodosAnimaisAleatorio();
                    if(contIteracoes % 1 == 0){
                        arquivar.escreveArquivosIteracao(ambiente);
                    }
                    contIteracoes++;
                }
                arquivar.escreveArquivosGeracao(ambiente, geracao);
                 //Vefica se o AG já atingiu o resultado final
                if(verificaFim(ambiente.getMacacos())){
                    System.out.println("Encontrou a solução");
                    break;
                }
                novaGeracao();
                zerarMortesMacacos();
            }
            arquivar.finalizaArquivosCSV(ambiente.getMacacos());
        } catch (IOException ex) {
            Logger.getLogger(AG.class.getName()).log(Level.SEVERE, null, ex);
        }
        finish = Instant.now();
    }
    
    private void novaGeracao(){
        // Esta função irá ordenar o vetor de macacos do mais apto ao menos apto
        Arrays.sort(ambiente.getMacacos());
        // Esta função ira gerar uma nova geração de individuos mantendo os mais aptos e substituindo os menos aptos
        int n_Macacos = ambiente.getN_Macacos(); // Numero de macacos no 
        //int eliminar = n_Macacos * corte / 100; // Numero de individuos que serão eliminados
        int index = n_Macacos - eliminar;
        Macaco macacos[] = ambiente.getMacacos();
        Random r = new Random();
        for(int i=0; i<eliminar; i++){
            int m1 = r.nextInt(n_Macacos - eliminar);
            int m2 = r.nextInt(n_Macacos - eliminar);
            if(macacos[index].getMortes() != 0){
                macacos[index].zerarMortes();
                float[][] novoSigno = cruzamento(macacos[m1], macacos[m2]);
                macacos[index++].setSignos(novoSigno);
            }
        }
        geracao++;
    }
    
    private float[][] cruzamento(Macaco m1, Macaco m2){
        // Função que irá cruzar dois individuos e mistura seus DNAs, no caso o vetor "signos"
        float[][] novo = new float[10][3];
        float[][] s1 = m1.getSignos();
        float[][] s2 = m2.getSignos();
        // Seleciona o maior signo em cada posição da matriz
        for(int i=0; i<10; i++){
            for(int j=0; j<3; j++){
                if(s1[i][j] > s2[i][j])
                    novo[i][j] = s1[i][j];
                else
                    novo[i][j] = s2[i][j];
            }
        }
        return novo;
    }
    
    private void mutacao(Macaco macaco){
        // Esta função ira fazer uma mutação no individuo q tera novo vetor signos
        macaco.instanciaSignos();
        macaco.zerarMortes();
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
        System.out.println("Total de Gerações: "+geracao);
        System.out.println("Total de Iterações: "+contIteracoes);
        System.out.println("Tempo de execução: "+timeExecution()+"ms");
    }
    
    public long timeExecution(){
        return Duration.between(start, finish).toMillis();  //in millis
    }
}
