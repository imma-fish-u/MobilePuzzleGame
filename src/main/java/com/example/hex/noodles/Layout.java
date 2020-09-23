
package com.example.hex.noodles;
import android.graphics.PointF;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;

public abstract class Layout{
   public static final float PADDINGRATIO = 0.05f;
   public static final float NOODLERATIO = 0.375f;
   private float size, marginX, marginY, innerScale, outerScale, cornerAngle, edgeAngle;
   private int segments;
   private Region region;

   public Layout(float innerScale, float outerScale, float cornerAngle, float edgeAngle, int segments){
      this.size = 27;
      this.marginX = 0;
      this.marginY = 0;
      this.innerScale = innerScale;
      this.outerScale = outerScale;
      this.cornerAngle = cornerAngle;
      this.edgeAngle = edgeAngle;
      this.segments = segments;
   }
   
   //get methods
   public float getSize(){return this.size;}
   public float getPadding(){return this.size * PADDINGRATIO;}
   public float getMarginX(){return this.marginX;}
   public float getMarginY(){return this.marginY;}
   public float getCornerAngle(){return this.cornerAngle;}
   public int getSegments(){return this.segments;}
   public Region getRegion() {return this.region;}
   
   //set methods
   public void setSize(float size){this.size = (size >= 1 ? size : 1);}
   public void setMarginX(float marginX){this.marginX = marginX;}
   public void setMarginY(float marginY){this.marginY = marginY;}
   public void setSegments(int segments){this.segments = segments;}
   public void setRegion(Path path) {
      RectF rectF = new RectF();
      path.computeBounds(rectF, true);
      Region reg = new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
      this.region.setPath(path, reg);
   }
   
   public Path getBase(Noodle noodle){
      PointF[] corners = this.getCorners(this.cornerAngle, noodle, this.outerScale);
      //PointF path = new PointF();
      Path path = new Path();
      path.reset(); //?
      path.moveTo(corners[0].x, corners[0].y);
      for(int i = 1; i < segments; i++){
         path.lineTo(corners[i].x, corners[i].y);
      }
      path.lineTo(corners[0].x, corners[0].y);
     // setRegion(path);
      return path;
   }//end getBase
   
   private PointF[] getCorners(float startAngle, Noodle noodle, float scale){
      PointF[] corners = new PointF[this.segments];
      PointF center = noodleToPixel(noodle);
      for(int i = 0; i < this.segments; i++){
         PointF offset = cornerOffset(startAngle, i, scale);
          corners[i] = new PointF(center.x + offset.x, center.y + offset.y);
      }
      return corners;
   }//end getCorners
   
   //Draw line
   public Path getNoodleSide(Noodle noodle, int side){
      PointF[] edges = this.getCorners(this.edgeAngle, noodle, this.innerScale);
      PointF center = noodleToPixel(noodle);
      Path edge = new Path();
      edge.moveTo(center.x, center.y);
      edge.lineTo(edges[side].x,edges[side].y);
      return edge;
   }//end getNoodleSide
   
   //get hex corner points	
   private PointF cornerOffset(float startAngle, int corner, float scale){
      float angle = (float)(2 * Math.PI * (corner + startAngle) / segments);
      return new PointF((float)(this.size * scale * Math.cos(angle)), (float)(this.size * scale * Math.sin(angle)));
   }//end hexCornerOffset
   
   public abstract PointF getOrigin();
   public abstract float getStroke();
  // public abstract Shape getNoodleCenter(Noodle noodle, float scale);
   public abstract PointF noodleToPixel(Noodle noodle);
   public abstract Noodle pixelToNoodle(PointF point);
}//end Layout class