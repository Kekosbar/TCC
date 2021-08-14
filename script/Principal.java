package script;

import java.io.IOException;

import script.agaprendizagem.AgAprendizagem;
import script.animal.Macaco;
import script.arquivar.*;

public class Principal {

    // Dados para o ambiente
    private static int n_Macacos = 10;
    private static int n_Prepadores = 10;
    private static int dimensao = 6;
    // Dados para o algoritmo evolutivo
    private static int iteracoes = 20; // Número de vezes que cada animal se movimenta no ambiente a cada geração
    private static int eliminar = 1;

    public static final int AG_TYPE = AgAprendizagem.EVOLUTIVO;
    private static final boolean SHOW_GRAFICS = false;
    
    public static void main(String[] args) {
        ArquivoCSV.limparPastasArquivosCSV(); // Limpa as pasta gerar novos arquivos
        // Inicializa um ambiente com Macacos e Predadores
        Ambiente ambiente = new Ambiente(n_Macacos, n_Prepadores, dimensao);
        AG ag = new AG(ambiente, iteracoes, eliminar);
        ag.iniciar();
        ag.imprimeDadosFinais();
        gravaResultados(ambiente.getMacacos());
        if(SHOW_GRAFICS)
            mostraGraficos();
    }
    
    private static void gravaResultados(Macaco[] macacos) {
        int i = 1;
        for(Macaco macaco : macacos){
            macaco.arquivarDados("Macaco "+(i++));
        }
    }
    
    private static void mostraGraficos(){
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "python3 /mnt/Documents/Documentos/Projetos\\ Pycharm/TCC/script/./principal.py");
        try {
            processBuilder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
