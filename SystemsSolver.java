package project2;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class SystemsSolver {
    private double x, y, z;
    public double matrix[][];
    private double bMatrix[];
    private double error;
    public int rows;
    
    // Pass in number of equations and file containing the coefficients
    // of the matrix and assign the values to the arrays
    public SystemsSolver(int numEq, File file, double e) throws IOException
    {
        error = e;
        rows = numEq;
        double temp[][] = new double[rows][4];
        bMatrix = new double[4];
        Scanner scan = new Scanner(file);
        // Store values in a temp array and assign the coefficient and b matrix accordingly
            for(int x = 0; x < rows && scan.hasNextInt(); x++)
            {
                for(int y = 0; y < 4 && scan.hasNextInt(); y++){
                    temp[x][y] = scan.nextInt();
                }
            }
            
            matrix = new double[rows][3];
            for(int x = 0; x < rows; x++)
            {
                for(int y = 0; y < 3; y++)
                    matrix[x][y] = temp[x][y];
                    bMatrix[x] = temp[x][3];
            }
    }
    
    public void printMatrix()
    {
        for(int x = 0; x < rows; x++)
        {
            for(int y = 0; y < 4; y++){
                if(y != 3)
                    System.out.print(matrix[x][y] + " ");
                
                else
                    System.out.print(bMatrix[x]);
            }
            System.out.println("");
        }
    }
    
    // Finished implementing psuedo code need to test somehow
    // probably need to change reading file to just read entire matrix at once instead of seperate matrices
    // Scaled Partial Pivoting
    // n = rows
    public void SPP()
    {
        double r, rmax, smax, xmult;
        // index array and scale arrays
        int index[] = new int[rows];
        double scale[] = new double[rows];
        double x[] = new double[rows];  // solution arr
        double sum;
        int i, j, n, k;
        j = 0;
        // Assign values for scale array
        for(i = 0; i < rows; i++)
        {
            index[i] = i + 1;   // Initialize index array
            smax = 0;
            for(j = 0; j < (rows - 1); j++)
            {
                if(Math.abs(matrix[i][j]) >= smax)
                    smax = matrix[i][j];
            }
            // Max coefficient in row
            scale[i] = smax;
        }
        
        // From here continue psuedo code on page 90
        // Eliminate row elements
        for(k = 0; k < (rows - 2); k++)
            {
                rmax = 0;
                for(i = k; i < (rows - 1); i++)
                {
                   // System.out.println("i: " + i);
                    r = Math.abs(matrix[index[i]][k]);
                    if(r > rmax)
                    {
                        rmax = r;
                        j = i;
                    }
                }
               // System.out.println("J: " + j);
                index[j] = index[k];
                for(i = (k + 1); i < (rows - 1); i++)
                {
                    xmult = matrix[index[i]][k] / matrix[index[k]][k];
                    matrix[index[i]][k] = xmult;
                    for(j = (k + 1); j < (rows - 1); j++)
                        matrix[index[i]][j] = matrix[index[i]][j] - (xmult * matrix[index[k]][j]);
                }
            }
        
        // forward elimination
        for(k = 1; k < (rows - 2); k++)
        {
            for(i = k + 1; i < rows - 1; i++)
            {
                bMatrix[index[i]] = bMatrix[index[i]] - matrix[index[i]][k] * bMatrix[index[k]]; 
            }
        }
        n = rows - 1;
        System.out.println("n: " + n);
        x[n] = bMatrix[index[n]] / matrix[index[n]][n];
        for(i = rows - 2; i < rows - 1; i++)
        {
            sum = bMatrix[index[i]];
            for(j = (i + 1); j < (rows - 1); j++)
            {
                sum = sum - (matrix[index[i]][j] * x[j]);
            }
            x[i] = sum / matrix[index[i]][i];
        }
        printMatrix();
    }
    
    void Jacobi()
    {
        int i, j, k, n;
        final int MAX = 50;
        double var = Math.pow(10, -10);
        double x[] = new double[]{0,0,0}; // updated iterate values
        double y[] = new double [rows]; // old iterate values
        double sum, diag;
        n = rows - 1;
        
        // Max 50 iterations
        for(k = 0; k < MAX; k++)
        {
            y = x;
            for(i = 0; i <= n; i++)
            {
                sum = bMatrix[i];
                diag = matrix[i][i];
                if(Math.abs(diag) < var)
                {
                    System.out.println("Diagonal element too small");
                    break;
                }
                for(j = 0; j < n; j++)
                {
                    if(j != i)
                        sum = +sum - (matrix[i][j] * y[j]);
                }
                x[i] = sum / diag;
            }
            // output k, x
            // Error check break if calculated value is less than error
            if ((Math.abs(x[k] - y[k])) < error)
            {
                System.out.println("\nFinal x values");
                for(int z = 0; z < rows; z++)
                    System.out.print(x[z] + " ");
                System.out.println("");
                break;
            }
        }
    }
    
    // Similar code to Jacobi with an alteration to the innermost loop
    void GaussSeidel()
    {
        int i, j, k, n;
        final int MAX = 50;
        double var = Math.pow(10, -10);
        double x[] = new double[]{0,0,0}; // updated iterate values
        double y[] = new double [rows]; // old iterate values
        double sum, diag;
        n = rows - 1;
        
        // Max 50 iterations
        for(k = 0; k < MAX; k++)
        {
            // y = x??
            y = x;
            for(i = 0; i <= n; i++)
            {
                sum = bMatrix[i];
                diag = matrix[i][i];
                if(Math.abs(diag) < var)
                {
                    System.out.println("Diagonal element too small");
                    break;
                }
                for(j = 0; j < i - 1; j++)
                    sum += sum - (matrix[i][j] * x[j]);
                
                for(j = i + 1; j < n; j++)
                    sum += sum - (matrix[i][j] * x[j]);
                
                x[i] = sum / diag;
            }
            // output k, x
            // Error check break if calculated value is less than error
            if ((Math.abs(x[k] - y[k])) < error)
            {
                System.out.println("\nFinal x values");
                for(int z = 0; z < rows; z++)
                    System.out.print(x[z] + " ");
                System.out.println("");
                break;
            }
        }
    }
}
