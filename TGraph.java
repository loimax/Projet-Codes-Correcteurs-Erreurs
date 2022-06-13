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

    // First implementation of display 
    /*
    public void display()
    {
        System.out.println("Right display : ");
        System.out.println();
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
        System.out.println();
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
    }*/


    public void display()
    {
        System.out.println("Graphe de Tanner :\n\nRight tab display :\t\tLeft tab Display :\n");
        int i, j, k, l;
        for(i = 0 , k = 0; i < n_c; i++, k++)
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

            System.out.print("\t\t\t");    
            if (k < n_r)
            {
                for(l = 0; l < w_r + 1; l++)
                {
                if(l == 0)
                    System.out.print(left[k][l] + "| ");
                else if (left[k][l] >= 10)
                    System.out.print(left[k][l] + " ");
                else
                    System.out.print(" " + left[k][l] + " ");      
                }    
            }
            System.out.println();
        }
    }

    public Matrix decode(Matrix code, int rounds)
    {
        Matrix result = new Matrix(code.getRows(), code.getCols());
        boolean flag;
        int[]count = new int[this.n_c];
    
        for(int i = 0; i < this.n_c; i++)
        {
            this.right[i][0] = code.getElem(0, i);
        }

        // Boucle principale
        for(int lim = 0; lim < rounds; lim++)
        {
            flag = false;
            for(int i = 0; i < this.n_r; i++)
            {
                //Initialisation du noeud F
                this.left[i][0] = 0;

                //Calcul des parités
                for(int par = 1; par < this.w_r + 1; par++)
                {
                    this.left[i][0] += this.right[this.left[i][par]][0];
                    this.left[i][0] %= 2;
                }
                //Vérifier s'il y a une erreur
                if(this.left[i][0] != 0) 
                    flag = true;
            }
            //Vérification
            if(!flag)
            {
                for(int i = 0; i < this.n_c; i++)
                    result.setElem(0, i, (byte)this.right[i][0]);
                return result;
            }

            //On reinitialise count

            for(int i = 0; i < this.n_c; i++) 
                count[i]=0;
            //Calcul du max
            int max_error = 0;
            for(int i = 0; i < n_r ; i++)
            {
                if(this.left[i][0] != 0)
                {
                    for(int j=1; j<this.w_r+1; j++)
                    {
                        count[this.left[i][j]]++;
                        max_error = Math.max(max_error, count[this.left[i][j]]);
                    }
                }
            }
            //Renversement de bits
            for(int i=0; i<this.n_c; i++)
            {
                if(count[i] == max_error)
                    this.right[i][0] = 1-this.right[i][0];
            }
        }
        //Si on dépasse la limite d'itération on retourne un mot qui vaut -1
        for(int i = 0; i < this.n_c; i++) 
            result.setElem(0, i, (byte) -1);
        return result;
    }
}
