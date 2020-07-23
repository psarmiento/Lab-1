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
            for(int y = 0; y < 3; y++){
                System.out.print(matrix[x][y] + " ");
            }
            System.out.print(bMatrix[x]);
            System.out.println("");
        }
    }
    
    // Finished implementing psuedo code need to test somehow
    // probably need to change reading file to just read entire matrix at once instead of seperate matrices
    // Scaled Partial Pivoting
    // n = rows
    public void Gauss()
    {
        double r, rmax, smax, xmult;
        // index array and scale arrays
        int index[] = new int[rows];
        double scale[] = new double[rows];
        int i, j, n;
        j = 0;
        for(i = 0; i < rows; i++)
        {
            index[i] = i + 1;   // Initialize index array
            smax = 0;
            for(j = 0; j < rows; j++)
            {
                if(Math.abs(matrix[i][j]) >= smax)
                    smax = matrix[i][j];
            }
            // Max coefficient in row
            scale[i] = smax;
        }
        
        // From here continue psuedo code on page 90
        for(int k = 0; k < (rows - 1); k++)
            {
                rmax = 0;
                for(i = k; i < rows; i++)
                {
                    r = Math.abs(matrix[index[i]][k]);
                    if(r > rmax)
                    {
                        rmax = r;
                        j = i;
                    }
                }
                index[j] = index[k];
                for(i = k + 1; i < rows; i++)
                {
                    xmult = matrix[index[i]][k] / matrix[index[k]][k];
                    matrix[index[i]][k] = xmult;
                    for(j = (k + 1); j < rows; j++)
                        matrix[index[i]][j] = matrix[index[i]][j] - (xmult * matrix[index[k]][j]);
                }
            }
    }
}
