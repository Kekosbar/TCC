package script;

public abstract class Predador extends Animal{

    private final int id;
    
    public Predador(int i, int j, int id) {
        super(i, j);
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
}
