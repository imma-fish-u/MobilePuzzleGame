package com.example.hex.noodles;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.MotionEvent;
import android.graphics.drawable.shapes.PathShape;

import com.example.hex.R;

public abstract class PanelStrategy {
   private int rows, cols, segment;
   private Layout layout;
   private Grid grid;
   private Puzzle puzzle;
   private int[] theme;
   private PointF clickPoint, mousePoint;
   private Noodle draggedNoodle;
   private Paint paint = new Paint();
   
   public PanelStrategy(int rows, int cols, Layout layout, Grid grid, int[] theme){
      this.rows = rows;
      this.cols = cols;
      this.segment = -1;
      this.layout = layout;
      this.grid = grid;
      this.theme = theme;
   }

   //get methods
   public int getRows(){return this.rows;}
   public int getCols(){return this.cols;}
   public Layout getLayout(){return this.layout;}
   public Grid getGrid(){return this.grid;}
   public Puzzle getPuzzle(){return this.puzzle;}
   public int[] getTheme(){return this.theme;}
   
   //set methods
   public void setLayout(Layout layout){this.layout = layout;}
   public void setGrid(Grid grid){this.grid = grid;}
   public void setPuzzle(Puzzle puzzle){this.puzzle = puzzle;}
   public void setRows(int rows){
      this.rows = rows;
      this.grid.initNoodles(this.rows, this.cols);
      this.regenerate();
   }
   public void setCols(int cols){
      this.cols = cols;
      this.grid.initNoodles(this.rows, this.cols);
      this.regenerate();
   }

   public void regenerate(){
      this.puzzle.initPuzzle(this.grid);
   }//end regenerate
   
   public int getSegment(PointF p1, PointF p2){
      int segments = this.layout.getSegments();
      float angle = (float)Math.toDegrees(Math.atan2(p1.y - p2.y, p1.x - p2.x));
      angle += (360 / segments) * this.layout.getCornerAngle();
      angle %= 360;
      if(angle < 0)
         angle +=360;
      return (int)((angle / (360 / segments)) % segments);
   }
   
   public void mousePressed(MotionEvent e, NoodlePanel panel){
      if(this.puzzle.isSolved()) {
         this.puzzle.initPuzzle(this.grid);
      	 //panel.massageSolved();
      }
      else{
         PointF point = new PointF(e.getX(), e.getY());
         Noodle noodle = this.layout.pixelToNoodle(point);
         int row = this.grid.getRow(noodle);
         int col = this.grid.getCol(noodle);
         if(row >= 0 && row < this.rows && col >= 0 && col < this.cols){
            noodle = this.grid.getNoodle(row, col);
           // if(this.layout.getRegion().contains((int)point.x, (int)point.y)){
               noodle.rotate(1);
            //}
            this.puzzle.testPowered(this.grid);
         }
      }
   }//end mousePressed
   
   public void mouseReleased(){
      if(this.draggedNoodle != null){
         this.segment = -1;
         this.draggedNoodle = null;
         this.clickPoint = null;
         this.mousePoint = null;
      }
      this.puzzle.testPowered(this.grid);
   }//end mouseReleased
   
  /* public void mouseDragged(MotionEvent e){
      if(clickPoint != null){
         if(this.segment < 0)
            this.segment = this.getSegment(this.clickPoint, this.layout.noodleToPixel(draggedNoodle));
         this.mousePoint = new PointF(e.getX(), e.getY());
         int difference = this.getSegment(this.mousePoint, this.layout.noodleToPixel(draggedNoodle)) - this.segment;
         this.draggedNoodle.rotate(difference);
         this.segment += difference;
      }
   }//end mouseDragged */
   
   public void paintComponent(Canvas c){
      Noodle[][] noodles = this.grid.getNoodles();
      Noodle cur;
      for(int row = 0; row < this.rows; row++){
         for(int col = 0; col < this.cols; col++){
            cur = noodles[row][col];
            this.paintNoodle(c, cur, row, col);
         }
      }
   }//end paintComponent
   
   public void paintNoodle(Canvas c, Noodle noodle, int row, int col){
      paint.setColor(theme[2]);
      paint.setStyle(Paint.Style.FILL);
      c.drawPath(this.layout.getBase(noodle),paint);

      Paint p2 = new Paint();
      p2.setColor(noodle.getPowered() ? theme[0] : theme[1]);
      p2.setStrokeWidth((int)this.layout.getStroke());
      //p2.setStrokeWidth(5);
      p2.setStyle(Paint.Style.STROKE);
      boolean[] activeSides = noodle.getActiveSides();
      for(int i = 0; i < activeSides.length; i++){
         if(activeSides[i]){
            c.drawPath(this.layout.getNoodleSide(noodle, i), p2);
         }
      }

      /*g2.fill(this.layout.getNoodleCenter(noodle, 0.2f));
      if(this.drawSource(noodle, row, col)){
         drawable.setColor(20);
         g2.fill(this.layout.getNoodleCenter(noodle, -0.1f));
      } */


      //drawable.setColor(20);
   }//end paintNoodle
   
   public boolean drawSource(Noodle noodle, int row, int col){
      if(noodle.equals(this.puzzle.getSource()))
         return true;
      if(row == this.grid.getRow(this.puzzle.getSource()) && (col == this.grid.getNoodles()[0].length && this.grid.getCol(puzzle.getSource()) == 0))
         return true;
      if(col == this.grid.getCol(this.puzzle.getSource()) && (row == this.grid.getNoodles().length && this.grid.getRow(puzzle.getSource()) == 0))
         return true;
      return false;
   }
   
   public void setCustomSize(Point newSize){
      Point oldSize = this.getPreferredSize();
      float newRatio = newSize.x / newSize.y;
      float oldRatio = oldSize.x / oldSize.y;
      
      if(newRatio < oldRatio){
         this.layout.setSize(getCustomWidth(newSize.x));
         this.layout.setMarginX(0);
         this.layout.setMarginY((newSize.y - this.getPreferredSize().y) / 2);
      }
      else{
         this.layout.setSize(getCustomHeight(newSize.y));
         this.layout.setMarginX((newSize.x - this.getPreferredSize().x) / 2);
         this.layout.setMarginY(0);
      }
   }//end setCustomSize
   public abstract float getCustomWidth(float newWidth);
   public abstract float getCustomHeight(float newHeight);
   public abstract Point getPreferredSize();
}//end PanelStrategy class