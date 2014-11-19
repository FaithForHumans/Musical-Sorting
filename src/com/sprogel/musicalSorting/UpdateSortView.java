package com.sprogel.musicalSorting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class UpdateSortView extends View {

  private int[] arrayToDisplay;
  private int arrayLength;
  private int[] lastDisplayedArray;

  private Paint paint;
  private double scaleX,scaleY;
  private final String HOLO_BLUE = "#33B5E5";
  private final String UPDATE_SORT_VIEW_TAG = "Update Sort View";
  
  public UpdateSortView(Context context, int[] origionalArray, int length) {
    super(context);
    arrayLength = length;
    arrayToDisplay = origionalArray;
    lastDisplayedArray = new int[arrayLength];
    for(int s = 0; s < arrayLength; s++) {
      lastDisplayedArray[s] = origionalArray[s];
    } // end for
    
    paint = new Paint();
    
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    scaleX = (double) (size.x) / arrayLength;
    scaleY = (double) (size.y) / arrayLength;
  } //end UpdateSortView(context, int[], int)

  @Override
  public void onDraw(Canvas canvas) {
    paint.setStrokeWidth(0);
    
    paint.setColor(Color.WHITE);
    for(int s = 0; s < arrayLength; s++) {
      if ( arrayToDisplay[s] != lastDisplayedArray[s] ) {
        paint.setColor(Color.RED);
        
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
