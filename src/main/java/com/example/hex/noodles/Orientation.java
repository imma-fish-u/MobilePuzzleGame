package com.example.hex.noodles;

public class Orientation{
   private float[][] forward;
   private float[][] inverse;
   private double startAngle;
   
   public Orientation(float f0, float f1, float f2, float f3, float b0, float b1, float b2, float b3){
      this.forward = new float[][]{{f0, f1}, {f2, f3}};
      this.inverse = new float[][]{{b0, b1}, {b2, b3}};
   }
   
   //get methods
   public float[][] getForward(){return this.forward;}
   public float[][] getInverse(){return this.inverse;}
}//end Orientation class