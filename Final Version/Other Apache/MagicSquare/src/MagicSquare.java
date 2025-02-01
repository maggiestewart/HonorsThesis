import java.io.*;
// Maggie Stewart
public class MagicSquare {
    public static void main(String[] args) throws IOException {
        File file = new File("Mercury.txt");
        System.out.println(file + "'s row, column, diagonal calcuations: ");
        
        String myLine;
        int dimension = 8;
        int[] rowTotal = new int[dimension];
        int[] columnTotal = new int[dimension];
        int leftDiagonalTotal = 0;
        int rightDiagonalTotal = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
       
        while ((myLine = br.readLine()) != null) {
            String[] values = myLine.split("\t");
           
            for (int i = 0; i < dimension; i++){
                int val = Integer.parseInt(values[i]);
                // Calculate row total
                rowTotal[i] += val;
                // Calculate column total
                columnTotal[i] += val;
                // Calculate diagonal total in this direction \
                if (dimension - values.length == i){
                    leftDiagonalTotal += val;
                }
                // Calculate diagonal total in this direction /
                if (dimension - values.length == (dimension - i - 1)){
                    rightDiagonalTotal += val;
                }
            }
        }
        for (int i = 0; i< dimension; i++){
            System.out.println("Row " + (i+1) + " total: " + rowTotal[i]);
        }
        for (int i = 0; i < dimension; i++){
            System.out.println("Column " + (i+1) + " total: " + columnTotal[i]);
        }
        System.out.printf("Left Diagonal Total: %d \nRight Diagonal Total: %d\n", leftDiagonalTotal, rightDiagonalTotal);
        System.out.println("Since ROW = COLUMN = DIAGONALS: MAGIC SQUARE!!");
              
        System.out.println();
                
        file = new File("Luna.txt");
        System.out.println(file + "'s row, column, diagonal calcuations: ");
        br = new BufferedReader(new FileReader(file));
        
        dimension = 9;
        rowTotal = new int[dimension];
        columnTotal = new int[dimension];
        leftDiagonalTotal = 0;
        rightDiagonalTotal = 0;
        
        while ((myLine = br.readLine()) != null) {
            String[] values = myLine.split("\t");
            
            for (int i = 0; i < dimension; i++){
                int val = Integer.parseInt(values[i]);
                // Calculate row total
                rowTotal[i] += val;
                // Calculate column total
                columnTotal[i] += val;
                // Calculate diagonal total in this direction \
                if (dimension - values.length == i){
                    leftDiagonalTotal += val;
                }
                // Calculate diagonal total in this direction /
                if (dimension - values.length == (dimension - i - 1)){
                    rightDiagonalTotal += val;
                }
            }
        }
        for (int i = 0; i< dimension; i++){
            System.out.println("Row " + (i+1) + " total: " + rowTotal[i]);
        }
        for (int i = 0; i < dimension; i++){
            System.out.println("Column " + (i+1) + " total: " + columnTotal[i]);
        }
        System.out.printf("Left Diagonal Total: %d \nRight Diagonal Total: %d\n", leftDiagonalTotal, rightDiagonalTotal);
        System.out.println("Since ROW = COLUMN = DIAGONALS: MAGIC SQUARE!!");   
    }   
}