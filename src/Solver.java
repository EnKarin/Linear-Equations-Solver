import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

public class Solver {
    public static double[][] fillMat(Scanner scan, int n){
        double[][] mat = new double[n][n + 1];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n + 1; j++){
                mat[i][j] = scan.nextDouble();
            }
        }
        return mat;
    }

    public static double[][] oneVar(double[][] mat, int n, int step){
        double temp;
        boolean flag = true;
        for(int i = step; i < n && flag; i++) {
            if (mat[i][step] == 1) {
                for (int j = 0; j <= n; j++) {
                    temp = mat[step][j];
                    mat[step][j] = mat[i][j];
                    mat[i][j] = temp;
                }
                flag = false;
            }
        }
        temp = mat[step][step];
        for(int i = step; i <= n && flag; i++) {
            mat[step][i] = mat[step][i] / temp;
        }
        return mat;
    }

    public static double[][] delVar(double[][] mat, int n, int step){
        double temp;
        for(int i = step + 1; i < n; i++) {
            temp = mat[i][step];
            for (int j = step; j <= n; j++) {
                mat[i][j] = mat[i][j] - mat[step][j] * temp;
            }
        }
        return mat;
    }

    public static double[] solSys(double[][] mat, int n){
        double[] sol = new double[n];
        sol[n - 1] = mat[n - 1][n] / mat[n - 1][n - 1];
        for(int i = n - 2; i >= 0; i--){
            sol[i] = mat[i][n];
            for(int j = n - 1; j > i; j--){
                sol[i] = sol[i] - mat[i][j] * sol[j];
            }
            sol[i] /= mat[i][i];
        }
        return sol;
    }

    public static void main(String[] args) throws Exception{
        File inFile = new File(args[1]);
        File outFile = new File(args[3]);
        Scanner scan = new Scanner(inFile);
        int n = scan.nextInt();
        double[][] matrix;
        double[] sol;
        matrix = fillMat(scan, n);
        scan.close();
        for(int i = 0; i < n; i++){
            if(matrix[i][i] != 1) {
                matrix = oneVar(matrix, n, i);
            }
            matrix = delVar(matrix, n, i);
        }
        sol = solSys(matrix, n);
        try (PrintWriter printWriter = new PrintWriter(outFile)) {
            for (int i = 0; i < n; i++) {
                printWriter.println(sol[i]);
            }

        }
        catch (IOException e) {
            System.out.printf("An exception occurs %s", e.getMessage());
        }
    }
}