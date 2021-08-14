package script;

import java.io.IOException;

public class Principal {

    // Dados para o ambiente
    private static int n_Macacos = 10;
    private static int n_Prepadores = 10;
    private static int dimensao = 6;
    // Dados para o algoritmo evolutivo
    private static int iteracoes = 60; // Número de vezes que cada animal se movimenta no ambiente a cada geração
    private static int eliminar = 1;
    private static final boolean SHOW_GRAFICS = false;
    
    public static void main(String[] args) {
        limparPastasArquivosCSV(); // Limpa as pasta gerar novos arquivos
        // Inicializa um ambiente com Macacos e Predadores
        Ambiente ambiente = new Ambiente(n_Macacos, n_Prepadores, dimensao);
        AG ag = new AG(ambiente, iteracoes, eliminar);
        ag.iniciar();
        ag.imprimeDadosFinais();
        gravaResultados(ambiente.getMacacos());
        if(SHOW_GRAFICS)
            mostraGraficos();
    }
    
    private static void limparPastasArquivosCSV(){
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Macacos");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Macacos/SignosPorPredador");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Predador/Geracao/Probabilidade");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Predador/Iteracao/Probabilidade");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Dados/Geracao");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Dados/Iteracao");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Predador/Geracao");
        ArquivoCSV.limparPasta(Arquivar.ROOT_PATH + "Predador/Iteracao");
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
