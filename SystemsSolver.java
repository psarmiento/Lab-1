package project2;
import java.util.Scanner;
import java.lang.Math;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class SystemsSolver {
    private double x, y, z;
    private double matrix[][];
    private double bMatrix[];
    private int rows;
    
    // Pass in number of equations and file containing the coefficients
    // of the matrix and assign the values to the arrays
    public SystemsSolver(int numEq, File file) throws IOException
    {
        rows = numEq;
        matrix = new double[rows][4];
        bMatrix = new double[4];
        Scanner scan = new Scanner(file);
        int rowTemp = 0;
        int column = 0;
        System.out.println("test");
        // Store the values of the text file in the arrays
        while(scan.hasNextInt())
        {
            if(column == 4)
            {
                bMatrix[rowTemp] = scan.nextInt();
                rowTemp++;
                column = 0;
            }
            
            else
            {
                matrix[rowTemp][column] = scan.nextInt();
                column++;
            }
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
}
