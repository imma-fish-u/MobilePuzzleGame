package com.example.hex.noodles.hexagon;
import android.graphics.PointF;

import com.example.hex.noodles.*;

public class HexLayout extends Layout{
   public static final Orientation FLAT = new Orientation(3/2, 0, (float)Math.sqrt(3)/2, (float)Math.sqrt(3), 2/3, 0, -1/3, (float)Math.sqrt(3)/3);
   public static final Orientation POINTY = new Orientation((float)Math.sqrt(3), (float)Math.sqrt(3)/2, 0, 3/2, (float)Math.sqrt(3)/3, -1/3, 0, 2/3);
   
   public HexLayout(){
      super((float)Math.sqrt(3)/2, 1, 0, 0.5f, 6); //new
   }
   //Буду изменять!!!!
   public PointF getOrigin(){
      float xOrigin = (super.getSize() + super.getPadding() + super.getMarginX());
      float yOrigin = (float)(2*((super.getSize() + super.getPadding()) * Math.sqrt(3)/2f)) + super.getMarginY();
      return new PointF(xOrigin, yOrigin);
   }//end getOrigin
   
   public float getStroke(){
      return (float)super.getSize() * Layout.NOODLERATIO > 0 ? (int)(super.getSize() * Layout.NOODLERATIO) : 1;
   }//end getStroke
   
   /*public Shape getNoodleCenter(Noodle noodle, float scale){
      PointF center = noodleToPixel(noodle);
      float size = super.getSize() * (Layout.NOODLERATIO + scale);
      return new OvalShape(center.x - size/2, center.y - size/2, size, size);
   }//end getNoodleCenter */
   
   public PointF noodleToPixel(Noodle noodle){
      Hex hex = (Hex)noodle;
      float[][] matrix = HexLayout.FLAT.getForward();
      float x = (matrix[0][0] * hex.getQ() + matrix[0][1] * hex.getR()) * (super.getSize() + super.getPadding());
      float y = (matrix[1][0] * hex.getQ() + matrix[1][1] * hex.getR()) * (super.getSize() + super.getPadding());
      return new PointF(x + this.getOrigin().x, y + this.getOrigin().y);
   }//end noodleToPixel
      
   public Noodle pixelToNoodle(PointF point){
      float x = (point.x - this.getOrigin().x) / (super.getSize() + super.getPadding());
      float y = (point.y - this.getOrigin().y) / (super.getSize() + super.getPadding());
      float[][] matrix = this.FLAT.getInverse();
      float q = (matrix[0][0] * x + matrix[0][1] * y);
      float r = (matrix[1][0] * x + matrix[1][1] * y);
      return FractionalHex.hexRound(new FractionalHex(q, r));
   }//end pixelToNoodle
}//end HexLayout class