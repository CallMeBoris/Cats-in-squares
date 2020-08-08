package boris.narodov.catsinsquares;

import android.widget.ImageButton;

import java.util.Random;

public class MatrixWithEverything {
    private int[][] images ={{R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14},
            {R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24},
            {R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34},
            {R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44}};

    private int[][] matrix={{11,12,13,14},{21,22,23,24},{31,32,33,34},{41,42,43,44}};

    public int[][] getImages() {
        return images;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public boolean usloviyeWin(int[][] matr){
        try{
            for (int j=0; j<4;j++){
                if(matr[0][j]==matr[1][j]&&matr[2][j] == matr[3][j]&&matr[1][j]==matr[3][j]){return true;}
                else if(matr[j][0]==matr[j][1]&&matr[j][2] == matr[j][3]&&matr[j][1]==matr[j][3]){return true;}
                else if(matr[0][j]==matr[1][j]&&matr[0][j+1] == matr[1][j+1]&&matr[1][j]==matr[1][j+1]){return true;}
                else if(matr[1][j]==matr[2][j]&&matr[1][j+1] == matr[2][j+1]&&matr[2][j]==matr[2][j+1]){return true;}
                else if(matr[2][j]==matr[3][j]&&matr[2][j+1] == matr[3][j+1]&&matr[3][j]==matr[3][j+1]){return true;}
            }
        }catch (Exception ignored){}
        return false;
    }

    public boolean uslovieZam(int a, int b){
        return a / 10 == b / 10 || a % 10 == b % 10;
    }

    public int counter(int count){
        if (count%2==0){
            return 2;
        }else {return 1;}
    }

    public  void random(){
        Random random = new Random();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                int index1 = random.nextInt(i+1);
                int index2 = random.nextInt(j+1);
                int a=images[index1][index2];
                int b=matrix[index1][index2];
                images[index1][index2]=images[i][j];
                matrix[index1][index2]=matrix[i][j];
                images[i][j]=a;
                matrix[i][j]=b;
            }
        }
    }
}