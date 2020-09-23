package com.example.hex.noodles.hexagon;
import com.example.hex.noodles.*;

import android.graphics.Color;
import android.graphics.Point;

public class HexPanel extends PanelStrategy{
   
   public HexPanel(int rows, int cols, int[] theme){
      super(rows, cols, new HexLayout(), new HexGrid(rows, cols), theme);
      super.setPuzzle(new HexPuzzle(super.getGrid()));
   }
   
   public float getCustomWidth(float newWidth){
      float size = (float)(newWidth / ((((3/4d) * (super.getCols() + 1))) * 2));
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomWidth
   
   public float getCustomHeight(float newHeight){
      float size = (float)(newHeight / (Math.sqrt(3) * (super.getRows() + 1/2d)));
      return size / (Layout.PADDINGRATIO + 1);
   }//end getCustomHeight
   
   public Point getPreferredSize(){
      float width = (super.getLayout().getSize() + super.getLayout().getPadding())*2;
      float height = (float)(Math.sqrt(3)/2d) * width;
      int x = (int)(width * (((3/4d) * (super.getCols()))) + 1);
      int y = (int)((height * super.getRows()) + (height / 2));
      return new Point(x, y);
   }//end getPreferredSize
}//end HexPanel class