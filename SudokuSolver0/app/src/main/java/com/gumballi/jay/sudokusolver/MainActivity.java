package com.gumballi.jay.sudokusolver;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout=(GridLayout)findViewById(R.id.sudokuGrid);
        //get screen size in pixels to adjust size of sudoku cells
        Display display=getWindowManager().getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        int dimensions=size.x/11;
        SudokuGrid.initGrid(this,gridLayout,dimensions);

        final Button solveButton=(Button) findViewById(R.id.solveButton);
        solveButton.setBackgroundColor(Color.parseColor("#ffff8800"));
        solveButton.setTextColor(Color.WHITE);

        solveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SudokuGrid.getCellValues();
                if(!SudokuGrid.getSolution()){
                    Toast.makeText(getApplicationContext(),"Solution does not exist",Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Solution Found",Toast.LENGTH_SHORT).show();
                SudokuGrid.updateSolution();
            }
        });

        Button clearButton=(Button) findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SudokuGrid.clearGrid();
            }
        });
        clearButton.setBackgroundColor(Color.parseColor("#ffff8800"));
        clearButton.setTextColor(Color.WHITE);
    }

}
