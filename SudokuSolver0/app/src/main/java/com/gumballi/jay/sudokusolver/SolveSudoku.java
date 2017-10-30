package com.gumballi.jay.sudokusolver;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jay Gumballi on 25/10/17.
 */


//class to solve the puzzle
public class SolveSudoku {

    public static int[][] sudokuGrid;

    public static boolean solvePuzzle(int[][] grid){
        sudokuGrid=grid;
        boolean solnExists=checkValidity();
        if(!solnExists) return solnExists;
        solnExists=solve(0,0);
        return solnExists;
    }

    //check whether no two same numbers exist in each row, column or smaller square
    public static boolean checkValidity(){
        boolean[] poss=new boolean[9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++) poss[j]=true;
            if(!horizontalCheck(poss,i)) return false;
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++) poss[j]=true;
            if(!verticalCheck(poss,i));
        }
        for(int i=0;i<9;i=i+3){
            for(int j=0;j<9;j=j+3){
                for(int k=0;k<9;k++) poss[k]=true;
                if(!squareCheck(poss,i,j)) return false;
            }
        }
        return true;
    }

    private static boolean horizontalCheck(boolean[] poss,int x){
        boolean output=true;
        for(int i=0;i<9;i++){
            if(sudokuGrid[x][i]!=0){
                if(poss[sudokuGrid[x][i]-1]){
                    poss[sudokuGrid[x][i]-1]=false;
                }else{
                    output=false;
                }
            }
        }
        return output;
    }

    private static boolean verticalCheck(boolean[] poss,int y){
        boolean output=true;
        for(int i=0;i<9;i++){
            if(sudokuGrid[i][y]!=0){
                if(poss[sudokuGrid[i][y]-1]){
                    poss[sudokuGrid[i][y]-1]=false;
                }else output=false;
            }
        }
        return output;
    }

    private static boolean squareCheck(boolean[] poss,int x,int y){
        boolean output=true;
        int startx,starty;
        startx=(x/3)*3;
        starty=(y/3)*3;
        for(int i=startx;i<startx+3;i++){
            for(int j=starty;j<starty+3;j++){
                if(sudokuGrid[i][j]!=0){
                    if(poss[sudokuGrid[i][j]-1]){
                        poss[sudokuGrid[i][j]-1]=false;
                    }else output=false;
                }
            }
        }
        return output;
    }

    public static boolean solve(int x,int y){
        if(x==9) return true;
        int ny,nx;
        if(y<8){
            ny=y+1;
            nx=x;
        }else{
            ny=0;
            nx=x+1;
        }
        if(sudokuGrid[x][y]!=0) return solve(nx,ny);
        //possible entries for current cell
        boolean [] poss=new boolean[9];
        for(int i=0;i<9;i++) poss[i]=true;
        horizontalCheck(poss,x);
        verticalCheck(poss,y);
        squareCheck(poss,x,y);
        for(int i=0;i<9;i++){
            if(poss[i]){
                sudokuGrid[x][y]=i+1;
                if(solve(nx,ny)) return true;
            }
        }
        sudokuGrid[x][y]=0;
        return false;
    }
}
