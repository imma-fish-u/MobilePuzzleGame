package com.example.hex.noodles.square;
import com.example.hex.noodles.*;
import android.graphics.*;
import android.graphics.drawable.shapes.OvalShape;


public class SquareLayout extends Layout{

   public SquareLayout(){
      super(1, (float)Math.sqrt(2), 0.5f, 0, 4);
   }
   
   public PointF getOrigin(){
      float xOrigin = super.getSize() + super.getPadding() + super.getMarginX();
      float yOrigin = super.getSize() + super.getPadding() + super.getMarginY();
      return new PointF(xOrigin, yOrigin);
   }//end getOrigin
   
   public float getStroke(){
      return super.getSize() * 1.5 * Layout.NOODLERATIO > 0 ? (int)(super.getSize() * 1.5 * Layout.NOODLERATIO) : 1;
   }//end getStroke
   
/*   public Shape getNoodleCenter(Noodle noodle, float scale){
      PointF center = this.noodleToPixel(noodle);
      float size = super.getSize() * 1.5f * (Layout.NOODLERATIO + scale);
      float round = size - this.getStroke();
      return new OvalShape(center.x - size/2, center.y - size/2, size, size, round, round);
   }//end getNoodleCenter*/
   
   public PointF noodleToPixel(Noodle noodle){
      Square square = (Square)noodle;
      float x = square.getCol() * ((super.getSize() + super.getPadding()) * 2);
      float y = square.getRow() * ((super.getSize() + super.getPadding()) * 2);
      return new PointF(x + this.getOrigin().x, y + this.getOrigin().y);
   }//end noodleToPixel
   
   public Noodle pixelToNoodle(PointF point){
      float x = (point.x - this.getOrigin().x) / ((super.getSize() + super.getPadding()) * 2);
      float y = (point.y - this.getOrigin().y) / ((super.getSize() + super.getPadding()) * 2);
      int row = (int)(Math.round(y));
      int col = (int)(Math.round(x));
      return new Square(row, col);
   }//end pixelToNoodle
}//end SquareLayout class