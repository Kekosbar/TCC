package script;

import java.util.Random;

import script.animal.*;

public class Ambiente {

    private Animal[][] matriz; // Esta matriz 50x50 representa o ambiente com Macacos, Predadores e Campos Vazios
    private final int dimensao; // Número de linhas e colunas da matriz do ambiente
    private final int n_Macacos; // Total de Macacos no ambiente
    private final int n_Prepadores; // Total de Predadores no ambiente
    private Animal[] animais; // Este vetor irá quardar as instancias dos animais no ambiente para rápido acesso;
    private Macaco[] macacos; // Vetor com todos os macacos do ambiente
    private final int raio = 1;// Tamanho do raio de percepção

    public Ambiente(int n_Macacos, int n_Prepadores, int dimensao) {
        this.n_Macacos = n_Macacos;
        this.n_Prepadores = n_Prepadores;
        this.dimensao = dimensao;
        instanciaMatriz();
    }
    
    private void instanciaMatriz(){
        // Instancia a matriz com os Animais que pertenceram ao ambiente
        matriz = new Animal[dimensao][dimensao];
        animais = new Animal[n_Macacos + n_Prepadores];
        macacos = new Macaco[n_Macacos];
        int count_animais = 0;
        Random r = new Random(); // Posições aleatorias
        // Macacos
        for(int i=0; i<n_Macacos; i++){
            while(true){
                int linha = r.nextInt(dimensao);
                int coluna = r.nextInt(dimensao);
                // Primeiro verifica se a posição esta livre
                if(matriz[linha][coluna] == null){
                    animais[count_animais] = matriz[linha][coluna] = new Macaco(linha, coluna);
                    macacos[count_animais] = (Macaco) animais[count_animais];
                    count_animais++;
                    break;
                }
            }
        }
        // Predadores
        int tipo = 0; // Define qual sera o predador (Tigre, Aguia, Cobra)
        for(int i=0; i<n_Prepadores; i++){
            while(true){
                int linha = r.nextInt(dimensao);
                int coluna = r.nextInt(dimensao);
                // Primeiro verifica se a posição esta livre
                if(matriz[linha][coluna] == null){
                    switch (tipo) {
                        case 0:
                            animais[count_animais++] = matriz[linha][coluna] = new Tigre(linha, coluna);
                            tipo++;
                            break;
                        case 1:
                            animais[count_animais++] = matriz[linha][coluna] = new Aguia(linha, coluna);
                            tipo++;
                            break;
                        default:
                            animais[count_animais++] = matriz[linha][coluna] = new Cobra(linha, coluna);
                            tipo = 0;
                            break;
                    }
                    break; // Finaliza a busca por uma posição
                }
            }
        }
    }

    public void moverTodosAnimaisAleatorio(){
        // Move os animais pelo ambiente
        Random r = new Random(); // Movimento aleatorio
        for(Animal animal : animais){
            int x = r.nextInt(4);
            switch(x){
                case 0:
                    animal.mover_cima(matriz);
                    break;
                case 1:
                    animal.mover_baixo(matriz, dimensao);
                    break;
                case 2:
                    animal.mover_esquerda(matriz);
                    break;
                case 3:
                    animal.mover_direita(matriz, dimensao);
                    break;
            }
        }
        // Percorre o raio de todos os macacos em busca de predadores
        analisaRaioTodosMacacos();
    }
    
    private void analisaRaioTodosMacacos() {
        // Verifica no raio de ação de cada macaco se há predadores, se sim dispara alarmes
        for (Macaco macaco : macacos) {
            int pos_i = macaco.getI();
            int pos_j = macaco.getJ();
            // As repetições abaixo são para percorre o raio de ação de um macaco
            for(int i = pos_i - raio; i <= pos_i + raio; i++){
                for(int j = pos_j - raio; j <= pos_j + raio; j++){
                    // Evita acessar uma área fora da matriz e a tentativa de acessar a posição do própio macaco
                    if(!(((i < 0 || j < 0) || (i >= dimensao || j >= dimensao)) || (i == pos_i && j == pos_j))){
                        if(matriz[i][j] instanceof Predador){
                            alertaMacacosNoRaio(macaco, (Predador) matriz[i][j]);
                        }
                    }
                }
            }
        }
    }
    
    private void alertaMacacosNoRaio(Macaco macaco, Predador predador){
        // Esta função ira alertas os outros macacos sobre a presenção de um predador
        //int idPredador = predador.getId(); // Saber qual predador esta na redondesa
        int signo = macaco.alarme(predador); // Signo ou alarme que sera disparado para os outros macacos
        
        int pos_i = macaco.getI();
        int pos_j = macaco.getJ();
        // As repetições abaixo são para percorre o raio de ação de um macaco
        for(int i = pos_i - raio; i <= pos_i + raio; i++){
            for(int j = pos_j - raio; j <= pos_j + raio; j++){
                // Evita acessar uma área fora da matriz e a tentativa de acessar a posição do própio macaco
                if(!(((i < 0 || j < 0) || (i >= dimensao || j >= dimensao)) || (i == pos_i && j == pos_j))){
                    if(matriz[i][j] instanceof Macaco){ // Se encontrar um macaco na vizinhaça
                        Macaco macacoVizinho = (Macaco) matriz[i][j];
                        macacoVizinho.recebeAlarme(signo, predador); // Macaco capta alarme
                    }
                }
            }
        }
    }

    public int getDimensao() {
        return dimensao;
    }

    public Animal[][] getMatriz() {
        return matriz;
    }

    public Macaco[] getMacacos() {
        return macacos;
    }

    public int getN_Macacos() {
        return n_Macacos;
    }
    
    public int getTotalMortes(){
        int total = 0;
        for(Macaco macaco : macacos){
            total += macaco.getMortes();
        }
        return total;
    }
    
    @Override
    public String toString() {
        String s = "\n==================\n";
        for(int i=0; i<dimensao; i++){
            s += "|";
            for(int j=0; j<dimensao; j++){
                if(matriz[i][j] == null)
                    s += " |";
                else
                    s += matriz[i][j] + "|";
            }
            s += "\n----------------------\n";
        }
        s += "====================";
        return s;
    }
    
}
