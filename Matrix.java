import java.util.*;
import java.io.*;

public class Matrix {
    byte[][] data = null;
    private int rows = 0, cols = 0;
    
    public Matrix(int r, int c) {
        data = new byte[r][c];
        rows = r;
        cols = c;
    }
    
    public Matrix(byte[][] tab) {
        rows = tab.length;
        cols = tab[0].length;
        data = new byte[rows][cols];
        for (int i = 0 ; i < rows ; i ++)
            for (int j = 0 ; j < cols ; j ++) 
                data[i][j] = tab[i][j];
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public byte getElem(int i, int j) {
        return data[i][j];
    }
    
    public void setElem(int i, int j, byte b) {
        data[i][j] = b;
    }
    
    public boolean isEqualTo(Matrix m){
        if ((rows != m.rows) || (cols != m.cols))
            return false;
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                if (data[i][j] != m.data[i][j])
                    return false;
                return true;
    }
    
    public void shiftRow(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < cols; i++){
            tmp = data[a][i];
            data[a][i] = data[b][i];
            data[b][i] = tmp;
        }
    }
    
    public void shiftCol(int a, int b){
        byte tmp = 0;
        for (int i = 0; i < rows; i++){
            tmp = data[i][a];
            data[i][a] = data[i][b];
            data[i][b] = tmp;
        }
    }
     
    public void display() {
        System.out.print("[");
        for (int i = 0; i < rows; i++) {
            if (i != 0) {
                System.out.print(" ");
            }
            
            System.out.print("[");
            
            for (int j = 0; j < cols; j++) {
                System.out.printf("%d", data[i][j]);
                
                if (j != cols - 1) {
                    System.out.print(" ");
                }
            }
            
            System.out.print("]");
            
            if (i == rows - 1) {
                System.out.print("]");
            }
            
            System.out.println();
        }
        System.out.println();
    }
    
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                result.data[j][i] = data[i][j];
    
        return result;
    }
    
    public Matrix add(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if ((m.rows != rows) || (m.cols != cols))
            System.out.printf("Erreur d'addition\n");
        
        for (int i = 0; i < rows; i++) 
            for (int j = 0; j < cols; j++) 
                r.data[i][j] = (byte) ((data[i][j] + m.data[i][j]) % 2);
        return r;
    }
    
    public Matrix multiply(Matrix m){
        Matrix r = new Matrix(rows,m.cols);
        
        if (m.rows != cols)
            System.out.printf("Erreur de multiplication\n");
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < m.cols; j++) {
                r.data[i][j] = 0;
                for (int k = 0; k < cols; k++){
                    r.data[i][j] =  (byte) ((r.data[i][j] + data[i][k] * m.data[k][j]) % 2);
                }
            }
        }
        
        return r;
    }
    public void addRow(int a, int b) {
        for (int i = 0; i < cols; i++)
            data[b][i] = (byte) ((data[b][i] + data[a][i]) % 2);
    }
    public void addCol(int a, int b) {
        for (int i = 0; i < rows; i++){
            data[i][b] = (byte) ((data[i][b] + data[i][a]) % 2);
        }
    }
    public Matrix sysTransform() {
        Matrix r = new Matrix(data);
        // System.out.println("Initialisation :\n");
        // r.display();
    	for (int i = 0, col = cols - rows; i < rows && col < cols; i++, col++) 
        { 
            if (r.data[i][col] == 0)
            {
                for (int j = i + 1; j < rows; j++)
                {
                    if (r.data[j][col] == 1)
                    {
                        r.shiftRow(i, j);
                        
                        // System.out.println("Permutation des lignes " + 
                        //                    (i+1) + " et " + (j+1) + " :\n");
                        // r.display();
                        break;
                    }
                        
                }   
            }
            for (int k = i + 1; k < rows; k++)
            {
                if (r.data[k][col] == 1)
                {
                    r.addRow(i, k);
                    
                    // System.out.println("Ajout des lignes " + 
                    //                     (i+1) + " et " + (k+1) + " à la ligne " + (k+1) + " :\n");
                    // r.display();
                }
            }
        }
        for (int j = cols - 1, i = rows - 1; j >= cols - rows && i >= 0; j--, i--) 
        {
            for(int k = i - 1; k >= 0; k--)
            {
                // System.out.println("value" + r.data[k][j] + " ligne " + (k+1) + " colonne " + (j+1)); debug
                if (r.data[k][j] == 1)
                {
                    r.addRow(i, k);
                    
                    // System.out.println("Ajout des lignes " + 
                    //                     (i+1) + " et " + (k+1) + " à la ligne " + (k+1) + 
                    //                     " et à la colonne " + (j+1) + " :\n");
                    // r.display();
                }
            }
        }
        r.display();
        return r;
    }   
    public Matrix genG(){
        Matrix a = new Matrix(data).sysTransform();
        Matrix b = new Matrix(a.data).transpose();
        //G = (Ik P) où Ik est la matrice identité à k lignes et k colonnes et 
        //P est la matrice de parité qui comporte k lignes et (n−k) colonnes avec n nombre colonnes de M.
        Matrix g = new Matrix(a.cols-a.rows,a.rows+(a.cols-a.rows));
        for (int i = 0; i < g.rows; i++)
        {
            g.data[i][i] = 1;
        }
        for (int i = 0; i < g.rows; i++)
        {
            for (int j_g = g.rows, j = 0; j_g < g.cols && j < b.cols; j_g++, j++)
            {
                g.data[i][j_g] = b.data[i][j];
            }
        }
        g.display();
        return g;
    }
}

