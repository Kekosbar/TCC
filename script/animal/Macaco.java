package script.animal;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import script.arquivar.*;

public class Macaco extends Animal implements Comparable<Macaco>{

    private float signos[][]; // Uma matriz 10x3 que relaciona os signos (i) com os predadores (j)
    /*
     * 0 -> Tigre
     * 1 -> Aguia
     * 2 -> Cobra
    */
    private int mortes = 0; // Quantidade de vezes que o macaco interpreta incorretamente um alarme
    private int id; // Apenas uma forma de diferenciar os macacos
    private static int contMacacos = 0; // Usado apenas para criar macacos com ids diferentes
    
    // As variáveis a seguir são apenas gerar arquivos sobre o decorrer dos processos, permitindo a geração de n graficos
    //=====================================================================================================================
    private static int[] contUsoSignos = new int[] {0,0,0,0,0,0,0,0,0,0};
    private static int[] contUsoSignosPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    
    private static int[] contUsoSignosParaTigre = new int[] {0,0,0,0,0,0,0,0,0,0};
    private static int[] contUsoSignosParaAguia = new int[] {0,0,0,0,0,0,0,0,0,0};
    private static int[] contUsoSignosParaCobra = new int[] {0,0,0,0,0,0,0,0,0,0};
    
    private static int[] contUsoSignosParaTigrePorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    private static int[] contUsoSignosParaAguiaPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    private static int[] contUsoSignosParaCobraPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    //======================================================================================================================
    // A seguir salva dados individuais
    private int[] contUsoSignosPorIteracaoIndivi = new int[] {0,0,0,0,0,0,0,0,0,0};
    private ArquivoCSV arquivoTabelaSignoAguia;
    private ArquivoCSV arquivoTabelaSignoCobra;
    private ArquivoCSV arquivoTabelaSignoTigre;

    public Macaco(int i, int j) {
        super(i, j);
        this.signos = instanciaSignos();
        // Dados a seguir apenas para arquivar informações
        this.id = ++contMacacos;
        try {
            String[] cabecalho = new String[]{"Iteração", "s1", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10"};
            arquivoTabelaSignoAguia = new ArquivoCSV("Macaco_"+id+"_Aguia", Arquivar.ROOT_PATH + "Macacos/SignosPorPredador", cabecalho);
            arquivoTabelaSignoCobra = new ArquivoCSV("Macaco_"+id+"_Cobra", Arquivar.ROOT_PATH + "Macacos/SignosPorPredador", cabecalho);
            arquivoTabelaSignoTigre = new ArquivoCSV("Macaco_"+id+"_Tigre", Arquivar.ROOT_PATH + "Macacos/SignosPorPredador", cabecalho);
        } catch (IOException ex) {
            Logger.getLogger(Macaco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static float[][] instanciaSignos(){
        // Função para instanciar signos de forma aleatória
        float[][] signos = new float[10][3];
        Random r = new Random();
        for(int i=0; i<10; i++){
            for(int j=0; j<3; j++){
                signos[i][j] = (float) r.nextInt(10)/10;
            }
        }
        return signos;
    }
    
    public void recebeAlarme(int signo, Predador predador){
        // Função na qual um macaco recebe um alarte de outro macaco sobre um predador
        int predadorParaMacaco = 0; // Representa o predador ao qual o macaco ira reconhecer
        // Procura pelo maior signo q corresponderá a um dos predadores
        for(int j=1; j<3; j++){
            if(this.signos[signo][predadorParaMacaco] < this.signos[signo][j])
                predadorParaMacaco = j;
        }
        int idPredadorCorreto = predador.getId();
        if(predadorParaMacaco != idPredadorCorreto){ // Se errar o predador contabiliza mais uma morte para o macaco
            mortes++;
        }
        reforco(signo, idPredadorCorreto);
    }
    
    public int alarme(Predador predador){
        int maiorSigno = maiorSigno(predador);
        // Dados a ser arquivados no arquivo CSV
        contUsoSignos[maiorSigno]++; // Incrementa a contagem do uso deste signo q será usado 
        contUsoSignosPorIteracao[maiorSigno]++; // Incrementa a contagem do uso deste signo q será usado 
        contUsoSignosPorIteracaoIndivi[maiorSigno]++; // Incrementa a contagem do uso deste signo q será usado 
        if(predador instanceof Tigre){
            contUsoSignosParaTigre[maiorSigno]++;
            contUsoSignosParaTigrePorIteracao[maiorSigno]++;
        }else if(predador instanceof Aguia){
            contUsoSignosParaAguia[maiorSigno]++;
            contUsoSignosParaAguiaPorIteracao[maiorSigno]++;
        }else{
            contUsoSignosParaCobra[maiorSigno]++;
            contUsoSignosParaCobraPorIteracao[maiorSigno]++;
        }
        
        return maiorSigno;
    }
    
    public int maiorSigno(Predador predador){
        // Retorna o maior signo que se refere a um determinado predador
        int maiorSigno = 0;
        int idPredador = predador.getId();
        for(int i=1; i<10; i++){
            if(signos[i][idPredador] > signos[maiorSigno][idPredador])
                maiorSigno = i;
        }
        return maiorSigno;
    }
    
    public void reforco(int signo, int idPredadorCorreto){
        // Função reforço
        for(int j=0; j<3; j++){
            if(idPredadorCorreto != j){
                if(this.signos[signo][j] > 0)
                    this.signos[signo][j] -= 0.1;
            }else
                if(this.signos[signo][j] < 1)
                    this.signos[signo][j] += 0.1;
        }
    }
    
    public void zerarMortes(){
        mortes = 0;
    }

    public float[][] clonar(){
        float[][] clone = new float[10][3];
        // Random r = new Random();
        for(int i=0; i<10; i++){
            for(int j=0; j<3; j++){
                clone[i][j] = signos[i][j];
            }
        }
        return clone;
    }
    
    public void arquivarTabelaSignosPorPredador(int iteracao){
        Predador[] predadores = new Predador[] {new Tigre(0, 0), 
                                                new Aguia(0, 0), 
                                                new Cobra(0, 0)};
        for(Predador p : predadores){
            String[] linha = new String[11];
            linha[0] = String.valueOf(iteracao);
            for(int j=0; j<10; j++){
                linha[j+1] = String.valueOf(signos[j][p.getId()]);
            }
            if(p instanceof Tigre)
                arquivoTabelaSignoTigre.escrever(linha);
            else if(p instanceof Aguia)
                arquivoTabelaSignoAguia.escrever(linha);
            else if(p instanceof Cobra)
                arquivoTabelaSignoCobra.escrever(linha);
        }
    }
    
    public void finalizaArquivosCSV() throws IOException{
        arquivoTabelaSignoAguia.finalizar();
        arquivoTabelaSignoCobra.finalizar();
        arquivoTabelaSignoTigre.finalizar();
    }
    
    public void arquivarDados(String nomeMacaco) {
        arquivarDadosUsoSigno(nomeMacaco);
    }
    
    private void arquivarDadosUsoSigno(String nomeMacaco){
        String[] cabecalho = new String[]{"Signos","P1","P2","P3"};
        try {
            ArquivoCSV arquivo = new ArquivoCSV(nomeMacaco, Arquivar.ROOT_PATH + "Macacos", cabecalho);
            for(int i=0; i<10; i++){
                String[] linha = new String[4];
                linha[0] = "S" + (i+1);
                for(int j=0; j<3; j++){
                    linha[j+1] = String.valueOf(signos[i][j]);
                }
                arquivo.escrever(linha);
            }
            arquivo.finalizar();
        } catch (IOException ex) {
            Logger.getLogger(Macaco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setSignos(float[][] signos) {
        this.signos = signos;
    }

    public float[][] getSignos() {
        return signos;
    }

    public int getMortes() {
        return mortes;
    }

    public void imprimirSignos(){
        System.out.print("\n |  P1  |  P2  |  P3  |");
        for(int i=0; i<10; i++){
            System.out.print("\n---------------------------------\n |  ");
            for(int j=0; j<3; j++){
                System.out.print(signos[i][j]+" |  ");
            }
        }
        System.out.print("\n---------------------------------\n");
    }
    
    public static void zerarVetorContSignos(){
        contUsoSignos = new int[] {0,0,0,0,0,0,0,0,0,0};
    }
    
    public static void zerarVetorContSignosPorIteracao(){
        contUsoSignosPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    }

    public static int[] getContUsoSignos() {
        return contUsoSignos;
    }

    public static int[] getContUsoSignosParaTigre() {
        return contUsoSignosParaTigre;
    }

    public static int[] getContUsoSignosParaAguia() {
        return contUsoSignosParaAguia;
    }

    public static int[] getContUsoSignosParaCobra() {
        return contUsoSignosParaCobra;
    }
    
    public static void zerarCountUsoSignoPorPredador(){
        contUsoSignosParaTigre = new int[] {0,0,0,0,0,0,0,0,0,0};
        contUsoSignosParaAguia = new int[] {0,0,0,0,0,0,0,0,0,0};
        contUsoSignosParaCobra = new int[] {0,0,0,0,0,0,0,0,0,0};
    }
    
    public static void zerarCountUsoSignoPorPredadorPorIteracao(){
        contUsoSignosParaTigrePorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
        contUsoSignosParaAguiaPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
        contUsoSignosParaCobraPorIteracao = new int[] {0,0,0,0,0,0,0,0,0,0};
    }

    public static int[] getContUsoSignosPorIteracao() {
        return contUsoSignosPorIteracao;
    }

    public static int[] getContUsoSignosParaTigrePorIteracao() {
        return contUsoSignosParaTigrePorIteracao;
    }

    public static int[] getContUsoSignosParaAguiaPorIteracao() {
        return contUsoSignosParaAguiaPorIteracao;
    }

    public static int[] getContUsoSignosParaCobraPorIteracao() {
        return contUsoSignosParaCobraPorIteracao;
    }
    
    @Override
    public String toString() {
        // String s = "S1 S2 S3 S4 S5 S6 S7 S8 S9 S10";
        return "M";
    }

    @Override
    public int compareTo(Macaco o) {
        return Integer.compare(this.mortes, o.mortes);
        //return this.mortes > o.mortes ? 1 : -1;
    }
    
}
