package script;

import java.io.IOException;

public class Arquivar {
    
    // As variáveis a seguir são apenas gerar arquivos sobre o decorrer dos processos, permitindo a geração de n graficos
    //=====================================================================================================================
    private ArquivoCSV arquivoMortesGeracao;
    
    private ArquivoCSV arquivoProbabilidadeUsoSignoTigreGeracao;
    private ArquivoCSV arquivoProbabilidadeUsoSignoAguiaGeracao;
    private ArquivoCSV arquivoProbabilidadeUsoSignoCobraGeracao;
    
    private ArquivoCSV arquivoProbabilidadeUsoSignoTigreIteracao;
    private ArquivoCSV arquivoProbabilidadeUsoSignoAguiaIteracao;
    private ArquivoCSV arquivoProbabilidadeUsoSignoCobraIteracao;
    
    private ArquivoCSV arquivoUsoSignosPorGeracao;
    private ArquivoCSV arquivoUsoSignosIteracao;
    
    private ArquivoCSV arquivoTigreSignosPorGeracao;
    private ArquivoCSV arquivoAguiaSignosPorGeracao;
    private ArquivoCSV arquivoCobraSignosPorGeracao;
    
    private ArquivoCSV arquivoTigreSignosPorIteracao;
    private ArquivoCSV arquivoAguiaSignosPorIteracao;
    private ArquivoCSV arquivoCobraSignosPorIteracao;
    
    private int countEscreveIteracao = 0;

    public Arquivar(Ambiente ambiente) throws IOException {
        // Esta função prepara as variáveis que irão escrever os arquivos CSV
        // Arquivo de geração por mortes
        String[] cabecalhoMortesGeracao = new String[]{"Geração","Mortes"};
        arquivoMortesGeracao = new ArquivoCSV("MortesPorGeracao", "Dados/Geracao", cabecalhoMortesGeracao);
        // Arquivo de probabilidade por geração
        String[] cabecalhoProbabilidadeGeracao = new String[] {"Geração", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10"};
        arquivoProbabilidadeUsoSignoTigreGeracao = new ArquivoCSV("probabilidadeUsoSignosTigre", "Predador/Geracao/Probabilidade", cabecalhoProbabilidadeGeracao);
        arquivoProbabilidadeUsoSignoAguiaGeracao = new ArquivoCSV("probabilidadeUsoSignosAguia", "Predador/Geracao/Probabilidade", cabecalhoProbabilidadeGeracao);
        arquivoProbabilidadeUsoSignoCobraGeracao = new ArquivoCSV("probabilidadeUsoSignosCobra", "Predador/Geracao/Probabilidade", cabecalhoProbabilidadeGeracao);
        // Arquivo de probabilidade por iteração
        String[] cabecalhoProbabilidadeIteracao = new String[] {"Iteração", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10"};
        arquivoProbabilidadeUsoSignoTigreIteracao = new ArquivoCSV("probabilidadeUsoSignosTigre", "Predador/Iteracao/Probabilidade", cabecalhoProbabilidadeIteracao);
        arquivoProbabilidadeUsoSignoAguiaIteracao = new ArquivoCSV("probabilidadeUsoSignosAguia", "Predador/Iteracao/Probabilidade", cabecalhoProbabilidadeIteracao);
        arquivoProbabilidadeUsoSignoCobraIteracao = new ArquivoCSV("probabilidadeUsoSignosCobra", "Predador/Iteracao/Probabilidade", cabecalhoProbabilidadeIteracao);
        // Arquivo de uso signos por geração
        String[] cabecalhoUsoSignos = new String[]{"Geração","s1","s2","s3","s4","s5","s6","s7","s8","s9","s10"};
        arquivoUsoSignosPorGeracao = new ArquivoCSV("UsoSignosGeracao", "Dados/Geracao", cabecalhoUsoSignos);
        // Arquivo de uso signos por iteração
        String[] cabecalhoUsoSignosIteracao = new String[]{"Iteração","s1","s2","s3","s4","s5","s6","s7","s8","s9","s10"};
        arquivoUsoSignosIteracao = new ArquivoCSV("UsoSignosIteracao", "Dados/Iteracao", cabecalhoUsoSignosIteracao);
        // Arquivo de Predador uso signo
        String[] cabecalhoPredadorSignos = new String[]{"Geração","s1","s2","s3","s4","s5","s6","s7","s8","s9","s10"};

        arquivoTigreSignosPorGeracao = new ArquivoCSV("TigreUsoSignos", "Predador/Geracao", cabecalhoPredadorSignos);
        arquivoAguiaSignosPorGeracao = new ArquivoCSV("AguiaUsoSignos", "Predador/Geracao", cabecalhoPredadorSignos);
        arquivoCobraSignosPorGeracao = new ArquivoCSV("CobraUsoSignos", "Predador/Geracao", cabecalhoPredadorSignos);

        String[] cabecalhoPredadorSignosPorIteracao = new String[]{"Iteração","s1","s2","s3","s4","s5","s6","s7","s8","s9","s10"};
        arquivoTigreSignosPorIteracao = new ArquivoCSV("TigreUsoSignos", "Predador/Iteracao", cabecalhoPredadorSignosPorIteracao);
        arquivoAguiaSignosPorIteracao = new ArquivoCSV("AguiaUsoSignos", "Predador/Iteracao", cabecalhoPredadorSignosPorIteracao);
        arquivoCobraSignosPorIteracao = new ArquivoCSV("CobraUsoSignos", "Predador/Iteracao", cabecalhoPredadorSignosPorIteracao);
        
        escreveArquivosIteracao(ambiente);
    }
    
    public void escreveArquivosGeracao(Ambiente ambiente, int geracao){
        escreveNoArquivoMorteGeracao(ambiente, geracao);
        escreveNoArquivoUsoSignosPorGeracao(geracao);
        escreveNoArquivoDosPredadoresPorGeracao(geracao);
        escreveProbabilidadeSignosPorPredadorGeracao(ambiente.getMacacos(), ambiente.getMacacos().length, geracao);
    }
    
    public void escreveArquivosIteracao(Ambiente ambiente){
        escreveNoArquivoUsoSignosPorIteracao(countEscreveIteracao);
        escreveNoArquivoDosPredadoresPorIteracao(countEscreveIteracao);
        escreveProbabilidadeSignosPorPredadorIteracao(ambiente.getMacacos(), ambiente.getMacacos().length, countEscreveIteracao);
        escreverTabelaSignoMacacoPorPredador(ambiente.getMacacos());
        countEscreveIteracao++;
    }
    
    private void escreveNoArquivoMorteGeracao(Ambiente ambiente, int geracao){
        String g = String.valueOf(geracao);
        String m = String.valueOf(ambiente.getTotalMortes());
        String[] linha = new String[]{g, m};
        arquivoMortesGeracao.escrever(linha);
    }
    
    private void escreveNoArquivoUsoSignosPorGeracao(int geracao){
        int[] signos = Macaco.getContUsoSignos();
        // Converte em string para escrever no arquivo
        String[] ss = new String[signos.length];
        // converte o vetor de signos para string
        for(int i=0; i<signos.length; i++)
            ss[i] = String.valueOf(signos[i]);
        String[] linha = new String[signos.length + 1];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoUsoSignosPorGeracao.escrever(linha);
        Macaco.zerarVetorContSignos();
    }
    
    private void escreveNoArquivoUsoSignosPorIteracao(int iteracao){
        int[] signos = Macaco.getContUsoSignosPorIteracao();
        // Converte em string para escrever no arquivo
        String[] ss = new String[signos.length];
        // converte o vetor de signos para string
        for(int i=0; i<signos.length; i++)
            ss[i] = String.valueOf(signos[i]);
        String[] linha = new String[signos.length + 1];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoUsoSignosIteracao.escrever(linha);
        Macaco.zerarVetorContSignosPorIteracao();
    }
    
    private void escreveNoArquivoDosPredadoresPorGeracao(int geracao){
        // Escreve no arquivo do TIGRE
        int[] countSignosTigre = Macaco.getContUsoSignosParaTigre();
        // Converte em string para escrever no arquivo
        String[] ss = new String[countSignosTigre.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosTigre.length; i++)
            ss[i] = String.valueOf(countSignosTigre[i]);
        String[] linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoTigreSignosPorGeracao.escrever(linha);
        
        // Escreve no arquivo do AGUIA
        int[] countSignosAguia = Macaco.getContUsoSignosParaAguia();
        // Converte em string para escrever no arquivo
        ss = new String[countSignosAguia.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosAguia.length; i++)
            ss[i] = String.valueOf(countSignosAguia[i]);
        linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoAguiaSignosPorGeracao.escrever(linha);
        
        // Escreve no arquivo do COBRA
        int[] countSignosCobra = Macaco.getContUsoSignosParaCobra();
        // Converte em string para escrever no arquivo
        ss = new String[countSignosCobra.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosCobra.length; i++)
            ss[i] = String.valueOf(countSignosCobra[i]);
        linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoCobraSignosPorGeracao.escrever(linha);
        
        Macaco.zerarCountUsoSignoPorPredador();
    }
    
    private void escreveNoArquivoDosPredadoresPorIteracao(int iteracao){
        // Escreve no arquivo do TIGRE
        int[] countSignosTigre = Macaco.getContUsoSignosParaTigrePorIteracao();
        // Converte em string para escrever no arquivo
        String[] ss = new String[countSignosTigre.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosTigre.length; i++)
            ss[i] = String.valueOf(countSignosTigre[i]);
        String[] linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoTigreSignosPorIteracao.escrever(linha);
        
        // Escreve no arquivo do AGUIA
        int[] countSignosAguia = Macaco.getContUsoSignosParaAguiaPorIteracao();
        // Converte em string para escrever no arquivo
        ss = new String[countSignosAguia.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosAguia.length; i++)
            ss[i] = String.valueOf(countSignosAguia[i]);
        linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoAguiaSignosPorIteracao.escrever(linha);
        
        // Escreve no arquivo do COBRA
        int[] countSignosCobra = Macaco.getContUsoSignosParaCobraPorIteracao();
        // Converte em string para escrever no arquivo
        ss = new String[countSignosCobra.length];
        // converte o vetor de signos para string
        for(int i=0; i<countSignosCobra.length; i++)
            ss[i] = String.valueOf(countSignosCobra[i]);
        linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++)
            linha[i] = ss[i - 1];
        arquivoCobraSignosPorIteracao.escrever(linha);
        
        Macaco.zerarCountUsoSignoPorPredadorPorIteracao();
    }
    
    private void escreveProbabilidadeSignosPorPredadorGeracao(Macaco[] macacos, int n_Macacos, int geracao){
        int[][] prob = new int[10][3]; // Probabilidade de usar um determinado signo para um determinado predador
        Predador[] predadores = new Predador[] {new Tigre(0, 0), 
                                                new Aguia(0, 0), 
                                                new Cobra(0, 0)};
        for(Predador p : predadores){
            for(Macaco m : macacos){
                int signo = m.maiorSigno(p);
                prob[signo][p.getId()]++;
            }
        }
        // TIGRE
        String[] linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[0].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoTigreGeracao.escrever(linha);
        // AGUIA
        linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[1].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoAguiaGeracao.escrever(linha);
        // COBRA
        linha = new String[11];
        linha[0] = String.valueOf(geracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[2].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoCobraGeracao.escrever(linha);
    }
    
    private void escreveProbabilidadeSignosPorPredadorIteracao(Macaco[] macacos, int n_Macacos, int iteracao){
        int[][] prob = new int[10][3]; // Probabilidade de usar um determinado signo para um determinado predador
        Predador[] predadores = new Predador[] {new Tigre(0, 0), 
                                                new Aguia(0, 0), 
                                                new Cobra(0, 0)};
        for(Predador p : predadores){
            for(Macaco m : macacos){
                int signo = m.maiorSigno(p);
                prob[signo][p.getId()]++;
            }
        }
        // TIGRE
        String[] linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[0].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoTigreIteracao.escrever(linha);
        // AGUIA
        linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[1].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoAguiaIteracao.escrever(linha);
        // COBRA
        linha = new String[11];
        linha[0] = String.valueOf(iteracao);
        for(int i=1; i<linha.length; i++){
            float probabilidade = (float) prob[i - 1][predadores[2].getId()] / n_Macacos;
            linha[i] = String.valueOf(probabilidade);
        }
        arquivoProbabilidadeUsoSignoCobraIteracao.escrever(linha);
    }
    
    private void escreverTabelaSignoMacacoPorPredador(Macaco[] macacos){
        for(Macaco m : macacos){
            m.arquivarTabelaSignosPorPredador(countEscreveIteracao);
        }
    }
    
    public void finalizaArquivosCSV(Macaco[] macacos) throws IOException{
        arquivoMortesGeracao.finalizar();
        
        arquivoProbabilidadeUsoSignoTigreGeracao.finalizar();
        arquivoProbabilidadeUsoSignoAguiaGeracao.finalizar();
        arquivoProbabilidadeUsoSignoCobraGeracao.finalizar();

        arquivoProbabilidadeUsoSignoTigreIteracao.finalizar();
        arquivoProbabilidadeUsoSignoAguiaIteracao.finalizar();
        arquivoProbabilidadeUsoSignoCobraIteracao.finalizar();
    
        arquivoUsoSignosPorGeracao.finalizar();
        arquivoUsoSignosIteracao.finalizar();

        arquivoTigreSignosPorGeracao.finalizar();
        arquivoAguiaSignosPorGeracao.finalizar();
        arquivoCobraSignosPorGeracao.finalizar();

        arquivoTigreSignosPorIteracao.finalizar();
        arquivoAguiaSignosPorIteracao.finalizar();
        arquivoCobraSignosPorIteracao.finalizar();
        
        for(Macaco m : macacos){
            m.finalizaArquivosCSV();
        }
    }

    public int getCountEscreveIteracao() {
        return countEscreveIteracao;
    }
}
