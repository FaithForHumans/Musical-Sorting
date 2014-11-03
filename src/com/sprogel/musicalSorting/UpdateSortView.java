package com.sprogel.musicalSorting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class UpdateSortView extends View {

  private Paint paint;
  private int[] arrayToDisplay;
  private int arrayLength;
  private int[] lastDisplayedArray;
  
  private double scaleX,scaleY;
  
  public UpdateSortView(Context context, int[] origionalArray, int length) {
    super(context);
    arrayLength = length;
    lastDisplayedArray = new int[arrayLength];
    for(int s = 0; s < arrayLength; s++) {
      lastDisplayedArray[s] = origionalArray[s];
    }
    
    paint = new Paint();
    
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    scaleX = (double) (size.x) / arrayLength;
    scaleY = (double) (size.y) / arrayLength;
  } 

  @Override
  public void onDraw(Canvas canvas) {
    //TODO
    paint.setColor(Color.WHITE);
    paint.setStrokeWidth(0);
    for(int s = 0; s < arrayLength; s++) {
      if ( arrayToDisplay[s] != lastDisplayedArray[s] ) {
        paint.setColor(Color.RED);
        //TODO play sound
        canvas.drawRect( (float)(0), (float) (s*scaleY), (float) (arrayToDisplay[s]*scaleX), (float) (s*scaleY+scaleY), paint);
        paint.setColor(Color.WHITE);
        lastDisplayedArray[s] = arrayToDisplay[s];
      } //end if
      else {
        canvas.drawRect( (float)(0), (float) (s*scaleY), (float) (arrayToDisplay[s]*scaleX), (float) (s*scaleY+scaleY), paint);
      } // end else
    } // end for
  } // end onDraw(Canvas)
  
  public void updateArray(int[] updatedArray) { arrayToDisplay = updatedArray; }

} // end class
