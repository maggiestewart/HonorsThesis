
import java.io.*;

public class CopyTextFile {

    public static void main(String[] args) throws IOException {
        
        FileReader fr = new FileReader("Mercury.txt");
        BufferedReader br = new BufferedReader(fr);
        
        FileWriter fw = new FileWriter("MercuryCopy.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        
        String line = null;
        while ((line = br.readLine()) != null){
            bw.write(line);
            bw.write("\n");
            System.out.println(line);
        }
        
        br.close();
        bw.close();
                
    }
    
}
