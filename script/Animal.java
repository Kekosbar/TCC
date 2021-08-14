package script;

public abstract class Animal {
    
    // Posição i e j do animal na matriz Ambiente
    private int i;
    private int j;

    public Animal(int i, int j) {
        this.i = i;
        this.j = j;
    }
    
    public void mover_esquerda(Animal[][] matriz){
        if(j > 0 && matriz[i][j-1] == null){
            matriz[i][j] = null;
            matriz[i][--j] = this;
        }
    }
    
    public void mover_direita(Animal[][] matriz, int dimensao){
        if(j+1 < dimensao && matriz[i][j+1] == null){
            matriz[i][j] = null;
            matriz[i][++j] = this;
        }
    }
    
    public void mover_cima(Animal[][] matriz){
        if(i > 0 && matriz[i-1][j] == null){
            matriz[i][j] = null;
            matriz[--i][j] = this;
        }
    }
    
    public void mover_baixo(Animal[][] matriz, int dimensao){
        if(i+1 < dimensao && matriz[i+1][j] == null){
            matriz[i][j] = null;
            matriz[++i][j] = this;
        }
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
    
}
