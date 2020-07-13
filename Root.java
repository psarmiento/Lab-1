/* 
    This class will define a method for each numerical method to be performed 
    on two predefined functions 
*/
package numericalmethods;
import java.lang.Math;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
public class Root {
    // Predefined a & b values (root in between 0 and 4) and c is to be calculated
    public double a = 120, b = 130, c = 0.0;
    // Arrays to hold error values for each iteration
    // Error should be calculated until less than 0.01
    private double[] relativeError = new double[100];
    private double[] absError = new double[100];
    private int iterations = 0;
    
    private double func1(double x)
    {
        return (2*x*x*x) - (11.7*x*x) + (17.7*x) - 5;
    }
    // 1st derivative of function 1
    private double derivF1(double x)
    {
        return (4*x*x) - (23.4*x) + 17.7;
    }
    private double func2(double x)
    {
        return x + 10 - x*(Math.cosh(50/x));
    }
    // 2nd derivative of function 2
    private double derivF2(double x)
    {
        return ((50 * Math.sinh(50/x)) / x) - Math.cosh(50/x)+ 1;
    }
    
    // Pass in 1 or 2 to specify which function to use
    void bisection(int func)
    {
        clear();
        // Array to hold calculated values of the function at c
        double[] valC = new double[100];
        for(int x = 0; x < 100; x++)
        {
            iterations++;
            // Compute midpoint
            c = (a + b) / 2;
            // 1st function
            if(func == 1)
            {
               double val = func1(a) * func1(c);
               valC[x] = func1(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
            
            // 2nd function
            else
            {
               double val = func2(a) * func2(c);
               valC[x] = func2(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
        }
        
        File file = new File("bisection.txt");
        writeFile(file);
                 
    }
    
    // Same method is used as bisection but c will be computed differently
    void falsePosition(int func)
    {
        clear();
         // Array to hold calculated values of the function at c
        double[] valC = new double[100];
        for(int x = 0; x < 100; x++)
        {
            iterations++;
            // 1st function
            if(func == 1)
            {
               // Use false position method to calculate c rather than midpoint
               c = (a*func1(b) - b*func1(a)) / (func1(b) - func1(a));
               double val = func1(a) * func1(c);
               valC[x] = func1(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
            
            // 2nd function
            else
            {
               // Use false position method to calculate c rather than midpoint
               c = (a*func2(b) - b*func2(a)) / (func2(b) - func2(a));
               double val = func2(a) * func2(c);
               valC[x] = func2(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
        }
     
        File file = new File("falsePosition.txt");
        writeFile(file);
                
    }

    /* To Do list:
        - come up with better variable names 
        - calculate error & store 
        - add logic to end loop if error is < 0.01
        
    */
    void newtonRaphson(int func)
    {
        clear();
        double previousRoot = 0, root = 0, funcVal = 0, derivVal = 0;
        // Guess a solution based on which function is being used 
        if(func == 1)
        {
            for(int x = 0; x < 100; x++)
            {
                iterations++;
                // Beginning of method hard code first guess 
                if(x == 0)
                {
                    previousRoot = 2;
                    absError[x] = 0;
                    relativeError[x] = 0;
                }
                
                // Call proper functions and calculate error
                else
                {
                    funcVal = func1(previousRoot);
                    derivVal = derivF1(previousRoot);
                    root = previousRoot - (funcVal / derivVal);
                    absError[x] = Math.abs(root - previousRoot);
                    relativeError[x] = Math.abs((root - previousRoot) / root);
                    previousRoot = root;    // Set the previous root to the current root
                }
                
                // Desired error obtained: e < 0.01
                if(absError[x] < 0.01 && absError[x] > 0)
                {
                    System.out.println("Root: " + root);
                    System.out.println("Iterations: " + iterations);
                    break;
                }
            }       
        }
        
        else
        {
            for(int x = 0; x < 100; x++)
            {
                iterations++;
                // Beginning of method hard code first guess 
                if(x == 0)
                {
                    previousRoot = 1;
                    absError[x] = 0;
                    relativeError[x] = 0;
                }
                
                // Call proper functions and calculate error
                else
                {
                    funcVal = func2(previousRoot);
                    derivVal = derivF2(previousRoot);
                    root = previousRoot - (funcVal / derivVal);
                    absError[x] = Math.abs(root - previousRoot);
                    relativeError[x] = Math.abs((root - previousRoot) / root);
                    previousRoot = root;    // Set the previous root to the current root
                }
                
                // Desired error obtained: e < 0.01
                if(absError[x] < 0.01 && absError[x] != 0)
                {
                    System.out.println("Root: " + root);
                    System.out.println("Iterations: " + iterations);
                    break;
                }
            }
        }
        // Send data to output file
        File file = new File("newton.txt");
        writeFile(file);
    }
   
    // Code for computing root and errors will stay the same 
    // Must check if guesses are converging
    void secant(int func)
    {
        clear();
        double[] valC = new double[100];
        for(int x = 0; x < 100; x++)
        {
            iterations++;
            // 1st function
            if(func == 1)
            {
               // Same calculation as false position
               c = (a*func1(b) - b*func1(a)) / (func1(b) - func1(a));
               double val = func1(a) * func1(c);
               valC[x] = func1(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // If solution does not appear to be diverging then break out of the loop
               // Try different values for solution?
               else if (iterations > 50 || relativeError[x] > relativeError[x - 1])
               {
                   System.out.println("Initial root guesses may not be converging to a root");
                   System.out.println("Root: " + c);
                   System.out.println("Absolute error: " + absError[x]);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
            
            // 2nd function
            else
            {
               // Same calculation as false position
               c = (a*func2(b) - b*func2(a)) / (func2(b) - func2(a));
               double val = func2(a) * func2(c);
               valC[x] = func2(c);
               // Compute and store error
               if(x == 0)
               {
                   absError[x] = 0;
                   relativeError[x] = 0;
               }
               else
               {
                   absError[x] = Math.abs(valC[x - 1] - valC[x]);
                   relativeError[x] = Math.abs((valC[x - 1] - valC[x]) / valC[x-1]);
               }
               if(val == 0 || (absError[x] < 0.01 && absError[x] > 0))
               {
                   System.out.println("The root is: " + c);
                   System.out.println("Iterations: " + iterations);
                   break;
               }
               
               // Replace b with c
               else if (val < 0)
               {
                   b = c;
               }
               
               // val > 0
               else
               {
                   a = c;
               }
            }
        }
     
        File file = new File("secant.txt");
        writeFile(file);
    }
        
    void modifiedSecant(){}
    
    // Clear values in array and reset iterations
    void clear()
    {
        for(int x = 0; x < iterations; x++)
        {
            absError[x] = 0;
            relativeError[x] = 0;
        }
        iterations = 0;
    }
    
    // Write output to specified file
    void writeFile(File file)
    {
        try
            {
                FileWriter newFile = new FileWriter(file);
                for(int x = 0; x < iterations; x ++)
                {
                    newFile.write(x + "\t" + relativeError[x] + "\t" + absError[x] + "\n");
                }
                newFile.close();
            }
            catch(IOException e)
            {
                System.out.println("Error ocurred\n");
                e.printStackTrace();
            }
    }
}
