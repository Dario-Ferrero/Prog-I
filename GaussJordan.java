public class GaussJordan{

    public static void main(String[] args){

        double[][] matrix = {{2.0, 4.0, 0.0, 8.0, 2.0},
                             {4.0, 0.0, 0.0, 2.0, 2.0},
                             {6.0, 6.0, 2.0, 2.0, 2.0}};

        System.out.println();
        stampa(matrix);
        matrix = ModGaussJordan(matrix);       
        stampa(matrix);                    
    }

    private static double[][] ModGaussJordan(double[][] m){

        for(int i = 0; i < m.length; i++){            // Per ogni elemento di pivot (i, i) 

            if(!verifyBases(m, i)){                    // Se la colonna i non è una base ammissibile
                m[i] = simplify(m[i], i);             // semplifico la riga di pivot
                stampa(m);
            }
            for(int j = 0; j < m.length; j++)         // poi, per le restanti righe diverse da i
                if(j != i){                           
                    m = nullify(m, i, j);             // azzero le posizioni nella colonna considerata e riduco le altre
                    stampa(m);
                }                                              
        }

        return m;
    }

    private static boolean verifyBase(double[][] m, int i){     // "base" == la colonna è una base ammissibile
                                                                // "one" == esiste un 1.0 ed è unico
        boolean base = false, zero = true, one = false;         // "zero" == ogni posizione diversa da i è uguale a 0.0
        for(int j = 0; j < m.length && !one && zero; j++){

            if(!one)
                one = m[i][j] == 1.0;
            if(m[i][j] != 1.0 && zero)
                zero = m[i][j] == 0.0;                      
        }

        base = one && zero;
        
        return base;
    }

    private static double[] simplify(double[] r, int i){

        double k = r[i];
        r[i] = 1.0;
        for(int j = 0; j < r.length; j++)
            if(j != i)
                r[j] = r[j]/k;
            
        return r;
    }

    private static double[][] nullify(double[][] m, int i, int j){

        double c = (m[j][i] >= m[i][i])? -(m[j][i]) : m[j][i];

        for(int k = 0; k < m[j].length; k++){
            m[j][k] = m[j][k] + c * m[i][k];
            if(m[j][k] == -0.0)
                m[j][k] = 0.0;
        }
        
        return m;
    }

    private static double[][] change(double[][] m, int r, int s){

        double[] aus = m[r];
        m[r] = m[s];
        m[s] = aus;
        
        return m;       
    }

    private static void stampa(double[][] m){

        for(int i = 0; i < m.length; i++){                  //Stampa matrice attuale, lasciando una linea a capo
            for(int j = 0; j < m[i].length; j++)
                Display.print(m[i][j] + " ");
            Display.println();
        }
        Display.println();
    }




    private static boolean verifyBases(double[][] m, int i){

        boolean uno = false, zeri = false;

        for(int j = 0; j < m.length; j++){

            if(m[i][j] != 0.0 && m[i][j] != 1.0)  
                return false;

            if(!uno)
                uno = m[i][j] == 1.0;
            else if(m[i][j] == 1.0)
                return false; 
            else    
                zeri = true;

            zeri = zeri && m[i][j] == 0.0;                                          
        }

        return uno && zeri;
    }





























}