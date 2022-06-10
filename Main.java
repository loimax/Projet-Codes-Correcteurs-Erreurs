import java.util.*;
import java.io.*;

public class Main {
    
    public static Matrix loadMatrix(String file, int r, int c) {
        byte[] tmp =  new byte[r * c];
        byte[][] data = new byte[r][c];
        try {
            FileInputStream fos = new FileInputStream(file);
            fos.read(tmp);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < r; i++)
            for (int j = 0; j< c; j++)
                data[i][j] = tmp[i * c + j];
            return new Matrix(data);
    }
    
    public static void main(String[] arg){
        
        // byte[][] tab = {{1,0,1,0},{0,1,0,1}};
        // Matrix m = new Matrix(tab);  
        // m.display();
        // // m.addRow(0, 2);
        // // m.addCol(1, 0); 
        // // m.sysTransform(); 
        // m.genG();
        
        Matrix hbase = loadMatrix("data/matrix-15-20-3-4", 15, 20);
        hbase.display();
        
        // byte[][] mot = {{1,0,1,0,1}};
        // Matrix encode = new Matrix(mot).multiply(hbase.genG());
        // encode.display();
        TGraph tg = new TGraph(hbase, 3, 4);
        tg.display();
    }
}

