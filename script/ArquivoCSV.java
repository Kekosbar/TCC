package script;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.opencsv.CSVWriter;

public class ArquivoCSV {

    private String nomeArquivo;
    private String nomePasta;
    private String[] cabecalho;
    private CSVWriter csvWriter;
    private Writer writer;

    public ArquivoCSV(String nomeArquivo, String nomePasta, String[] cabecalho) throws IOException {
        this.nomeArquivo = nomeArquivo;
        this.nomePasta = nomePasta;
        this.cabecalho = cabecalho;
        
        criarPasta(nomePasta);
        writer = Files.newBufferedWriter(Paths.get(nomePasta+"/"+nomeArquivo+".csv"));
        csvWriter = new CSVWriter(writer);
        csvWriter.writeNext(cabecalho);
    }
    
    public void escrever(String[] linha){
        csvWriter.writeNext(linha);
    }
    
    public void finalizar() throws IOException{
        csvWriter.flush();
        writer.close();
    }
    
    public void criarPasta(String nomePasta){
        File pasta = new File(nomePasta);
        if(!pasta.exists())
            pasta.mkdir();
    }
    
    public static void limparPasta(String pasta){
        File dir = new File(pasta);
        for(File file: dir.listFiles()) 
            if (!file.isDirectory()) 
                file.delete();
    }
    
    public void criarArquivo(){
        
    }
    
}
