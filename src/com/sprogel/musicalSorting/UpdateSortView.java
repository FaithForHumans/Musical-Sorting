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
  private boolean sortHasBeenFinished;

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
    sortHasBeenFinished = false;
    
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
    if ( !sortHasBeenFinished ) {
      paint.setColor(Color.WHITE);
      for(int s = 0; s < arrayLength; s++) {
        if ( arrayToDisplay[s] != lastDisplayedArray[s] ) {
          paint.setColor(Color.RED);
          
          canvas.drawRect( (float)(0), (float) (s*scaleY), (float) (arrayToDisplay[s]*scaleX), (float) (s*scaleY+scaleY), paint);
          //TODO play sound
          
          paint.setColor(Color.WHITE);
          lastDisplayedArray[s] = arrayToDisplay[s];
        } //end if
        
        else {
          canvas.drawRect( (float)(0), (float) (s*scaleY), (float) (arrayToDisplay[s]*scaleX), (float) (s*scaleY+scaleY), paint);
        } // end else
      } // end for
    } // end if
    else {
      paint.setColor( Color.parseColor(HOLO_BLUE) );
      for( int s = 0; s < arrayLength; s++) {
        canvas.drawRect( (float)(0), (float) (s*scaleY), (float) ((s+1)*scaleX), (float) (s*scaleY+scaleY), paint);
        //TODO play sound
        
        try {
          wait(10); // small delay before playing next
        } catch (InterruptedException e) {
          Log.w(UPDATE_SORT_VIEW_TAG, "Unable to pause the sort completed thread. Dunno why.\n" + e.toString() );
        } // end try catch
      } // end for
    } // end else
  } // end onDraw(Canvas)
  
  public void updateArray(int[] updatedArray) { arrayToDisplay = updatedArray; }
  public void sortCompleted() { sortHasBeenFinished = true; }

} // end class
