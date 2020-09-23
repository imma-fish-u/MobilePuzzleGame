package com.example.hex.noodles.square;
import com.example.hex.noodles.*;

import android.graphics.Color;
import android.graphics.Point;


public class SquarePanel extends PanelStrategy{
   
   public SquarePanel(int rows, int cols, int[] theme){
      super(rows, cols, new SquareLayout(), new SquareGrid(rows, cols), theme);
      super.setPuzzle(new SquarePuzzle(super.getGrid()));
   }
   
   public Noodle getGhost(int row, int col){
      return new Square(row, col, false, new boolean[]{false, false, false, false});
   }//end getGhostNoodle
   
   public Noodle getGhost(int row, int col, boolean powered, boolean[] activeSides){
      return new Square(row, col, powered, activeSides);
   }//end getGhostNoodle
   
   public float getCustomWidth(float newWidth){
      float size = newWidth / (super.getCols() * 2);
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomWidth
   
   public float getCustomHeight(float newHeight){
      float size = newHeight / (super.getRows() * 2);
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomHeight
   
   public Point getPreferredSize(){
      float square = (super.getLayout().getSize() + super.getLayout().getPadding()) * 2;
      int x = (int)(square * super.getCols());
      int y = (int)(square * super.getRows());
      return new Point(x, y);
   }//end getPreferredSize
}//end SquarePanel