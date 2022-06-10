import javax.lang.model.util.ElementScanner6;

public class TGraph {
    int[][] left = null, right = null;
    private int n_r = 0, w_r = 0, n_c = 0, w_c = 0;
    public TGraph(Matrix H, int wc, int wr){
        w_r = wr;
        w_c = wc;
        n_r = H.getRows();
        n_c = H.getCols();

        left = new int[n_r][w_r + 1];
        right = new int[n_c][w_c + 1];

        int i = 0, j = 0;
        for(i = 0 ; i < n_r; i++)
        {
                left[i][0] = 0;
                right[i][0] = 0;                  
        } 
        //left completion 
        for(i = 0 ; i < n_r; i++)
        {
            int z = 1;
            for(j = 0; j < n_c; j++)
            {
                if (H.getElem(i, j) == 1)
                {
                    left[i][z] = j;
                    z++;
                }        
                if (z == n_c)
                    break;
            }  
        }

        //right completion
        for(j = 0; j < n_c; j++)
        {
            int z = 1;
            for(i = 0 ; i < n_r; i++)
            {
                if (H.getElem(i, j) == 1)
                {
                    right[j][z] = i;
                    z++;
                }        
                if (z == n_c)
                    break;
            }  
        }
    }


    public void display(){
        System.out.println("Right display : ");
        int i, j;
        for(i = 0 ; i < n_c; i++)
        {
            for(j = 0; j < w_c + 1; j++)
            {
                if(j == 0)
                    System.out.print(right[i][j] + "| ");
                    else if (right[i][j] >= 10)
                        System.out.print(right[i][j] + " ");
                        else
                            System.out.print(" " + right[i][j] + " ");
            }    
            System.out.println();
        }
        System.out.println();
        System.out.println();
        System.out.println("Left display : ");
        for(i = 0 ; i < n_r; i++)
        {
            for(j = 0; j < w_r + 1; j++)
            {
		        if(j == 0)
                    System.out.print(left[i][j] + "| ");
                    else if (left[i][j] >= 10)
                        System.out.print(left[i][j] + " ");
                        else
                            System.out.print(" " + left[i][j] + " ");

                    
            }    
            System.out.println();
        }
    }
}
