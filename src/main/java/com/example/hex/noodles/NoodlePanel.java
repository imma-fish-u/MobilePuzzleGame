package com.example.hex.noodles;
import android.content.res.Resources;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.content.Context;

import com.example.hex.R;
import com.example.hex.noodles.hexagon.HexPanel;
import com.example.hex.noodles.square.SquarePanel;

public class NoodlePanel extends View {
   private PanelStrategy strategy;
   private int[] theme;
   private int width;
   private int height;

   public NoodlePanel(Context context, int w, int h) {
      super(context);
      Resources res = context.getResources();
      this.width = w;
      this.height = h;
      this.theme = new int[]{res.getColor(R.color.colorPower),res.getColor(R.color.colorNoodle),res.getColor(R.color.colorBase)};
      this.strategy = new SquarePanel(3, 3, theme);
      this.strategy.setCustomSize(new Point((int)(this.width),(int)(this.height)));
   }
   
   //get methods
   public PanelStrategy getStrategy(){return this.strategy;}
   public int getRows(){return this.strategy.getRows();}
   public int getCols(){return this.strategy.getCols();}
   public int[] getTheme(){return this.theme;}
   
   //set methods
   public void setRows(int rows){
      this.strategy.setRows(rows);
      this.resetView();
   }
   public void setCols(int cols){
      this.strategy.setCols(cols);
      this.resetView();
   }
   public void setStrategy(PanelStrategy strategy){
      this.strategy = strategy;
      this.resetView();
   }
   
   public void regenerate(){
      this.strategy.regenerate();
      this.invalidate();
   }//end regenerate
   
  public void resetView(){
      this.strategy.setCustomSize(new Point(this.width,this.height));
      this.invalidate();
   }//end resetView

   @Override
   public boolean onTouchEvent(MotionEvent e){
       if (e.getAction() == MotionEvent.ACTION_DOWN) {
           this.strategy.mousePressed(e, this);
       }
       return true;
   }

   @Override
   public void onDraw(Canvas canvas){
       canvas.drawColor(theme[0]);
      strategy.paintComponent(canvas);
      invalidate();

   }//end paintComponent
   
   public Point getPreferredSize(){
      return this.strategy.getPreferredSize();
   }//end getPreferredSize
}//end NoodlePanel class