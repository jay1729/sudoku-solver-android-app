package com.gumballi.jay.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.icu.lang.UCharacter;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Space;
import android.widget.TextView;

import java.nio.file.CopyOption;

import static android.content.ContentValues.TAG;

/**
 * Created by Jay Gumballi on 24/10/17.
 */

public class SudokuGrid {

    //contains reference to the sudoku cells
    private static EditText[][] gridCell;
    //contains value of integer in cells, if blank then 0
    private static int[][] cellValues=new int[9][9];
    private static int cellDimensions;

    public static void initGrid(Context context,GridLayout gridLayout,int dimensions){
        cellDimensions=dimensions;
        gridCell=initCell(context);
        int i=0;
        int j;
        for(int a=0;a<11;a++){
            j=0;
            for(int b=0;b<11;b++){
                GridLayout.Spec rowSpan=GridLayout.spec(GridLayout.UNDEFINED,1);
                GridLayout.Spec colSpan=GridLayout.spec(GridLayout.UNDEFINED,1);

                GridLayout.LayoutParams layoutParams=new GridLayout.LayoutParams(rowSpan,colSpan);

                Space space=new Space(context);

                if(((a==3)||(a==7))&&((b==3)||(b==7))){
                    space.setMinimumWidth(cellDimensions/10);
                    space.setMinimumHeight(cellDimensions/10);
                    gridLayout.addView(space,layoutParams);
                    continue;
                }

                if((a==3)||(a==7)){
                    space.setMinimumWidth(cellDimensions);
                    space.setMinimumHeight(cellDimensions/10);
                    gridLayout.addView(space,layoutParams);
                    continue;
                }

                if((b==3)||(b==7)){
                    space.setMinimumWidth(cellDimensions/10);
                    space.setMinimumHeight(cellDimensions);
                    gridLayout.addView(space,layoutParams);
                    continue;
                }

                gridLayout.addView(gridCell[i][j],layoutParams);
                j++;
            }
            if((a==3)||(a==7)) continue;
            i++;
        }
    }

    //initializes each cell with appropriate settings
    private static EditText[][] initCell(Context context) {
        final EditText[][] sudokuCell = new EditText[9][9];

        for (int i = 0; i < 9; i++) {
            for(int j=0;j<9;j++){
                sudokuCell[i][j] = new EditText(context);
                sudokuCell[i][j].setCursorVisible(false);
                sudokuCell[i][j].setBackgroundResource(R.drawable.edit_text_style);
                sudokuCell[i][j].setMinimumHeight(cellDimensions);
                sudokuCell[i][j].setMinimumWidth(cellDimensions);
                sudokuCell[i][j].setTextSize(15);
                sudokuCell[i][j].setPadding(0,0,0,0);
                sudokuCell[i][j].setGravity(Gravity.CENTER);
                sudokuCell[i][j].setClickable(true);
                sudokuCell[i][j].setFocusable(true);
                sudokuCell[i][j].setFocusableInTouchMode(true);
                sudokuCell[i][j].setInputType(InputType.TYPE_CLASS_NUMBER);
                sudokuCell[i][j].addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    //does not allow any integers other than 1-9
                    @Override
                    public void afterTextChanged(Editable s) {
                        if((s.length()==1)&&(Integer.parseInt(s.toString())!=0)) return;
                        if(s.length()==0) return;
                        if(Integer.parseInt(s.toString())==0){
                            s.clear();
                            return;
                        }
                        //if two digit integer is entered, take the last entered digit
                        s.replace(0,s.length(),String.valueOf(s.toString().charAt(s.length()-1)));
                    }
                });

                //change cursor position to end of text
                sudokuCell[i][j].setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
                            final EditText et = (EditText) v;
                            et.post(new Runnable() {
                                @Override
                                public void run() {
                                    et.setSelection(et.length());
                                }
                            });
                        }
                    }
                });

                sudokuCell[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText et = (EditText) v;
                        et.post(new Runnable() {
                            @Override
                            public void run() {
                                et.setSelection(et.length());
                            }
                        });
                    }
                });
            }

        }
        return sudokuCell;
    }

    public static void getCellValues(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(!gridCell[i][j].getText().toString().isEmpty()){
                    cellValues[i][j]=Integer.parseInt(gridCell[i][j].getText().toString());
                }else cellValues[i][j]=0;
            }
        }
    }

    public static boolean getSolution(){
        boolean solnExists=SolveSudoku.solvePuzzle(cellValues);
        if(solnExists) cellValues=SolveSudoku.sudokuGrid;
        Log.d(TAG,String.valueOf(solnExists));
        return solnExists;
    }


    public static void updateSolution(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                gridCell[i][j].setText(String.valueOf(cellValues[i][j]));
            }
        }
    }

    public static void clearGrid(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                cellValues[i][j]=0;
                gridCell[i][j].setText("");
            }
        }
    }
}
