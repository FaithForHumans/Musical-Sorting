package com.sprogel.musicalSorting;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sprogel.musicalSorting.sorts.*;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {
  
  private Random randomGenerator; // random number generator
  private final String MAIN_ACTIVITY_TAG = "Main Activity"; // log purposes
  private final short UDPATE_MAX = 20;
  
  public int[] arrayToSort; // array that needs to be sorted
  public int arrayLength; // array to be sorted length
  public short threadUpdateLength; // the delay in which the screen is updated
  
  private EditText numElements; // text field for number of elements to be sorted
  private EditText updateLength; // text field update delay length
  private RadioGroup selectedSort; // group for the sort to be used
  private RadioGroup selectedItemOrder; // group for the original item order
  private SeekBar numElem; // Seekbar for the number of elements
  private SeekBar updateLen; // Seekbar for the update length
  private Button submitButton;
  
  private Thread sortThread; // the thread that contains the sort to be ran
  public Object syncToken; // the token that controls sortThread wait and notify
  private UpdateSortView sortView; // the view that draws the visual representation o the screen

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_main);
    
    //get the elements of the view to be displayed
    numElements = (EditText) findViewById(R.id.numElements);
    updateLength = (EditText) findViewById(R.id.updateLength);
    selectedSort = (RadioGroup) findViewById(R.id.selectedSort);
    selectedItemOrder = (RadioGroup) findViewById(R.id.selectedItemOrder);
    numElem = (SeekBar) findViewById(R.id.totalElements);
    updateLen = (SeekBar) findViewById(R.id.updateInterval);
    submitButton = (Button) findViewById(R.id.submitButton);
    
    //Set the max values of the seekbars
    Display display = getWindowManager().getDefaultDisplay();
    Point pointSize = new Point();
    display.getSize(pointSize);
    numElem.setMax( pointSize.y );
    updateLen.setMax(UDPATE_MAX);
    
    //Set the listeners for the seekbars
    // listener for the number of elements seekbar
    numElem.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      int currentProgress = 0;
      
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentProgress = (progress < 1) ? 1 : progress;
      } // end onProgressChanged(SeekBar, int, boolean)

      //No need to do anything when start touched
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) { }

      //Update the values when released
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        numElements.setText( String.valueOf(currentProgress) );
      } // end onStopTrackingTouch(SeekBar)
    }); // end OnSeekBarChangeListner
    // listener for update Length seek bar
    updateLen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      int currentProgress = 0;
      
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        currentProgress = progress;
      } // end onProgressChanged(SeekBar, int, boolean)

      //No need to do anything when start touched
      @Override
      public void onStartTrackingTouch(SeekBar seekBar) { }

      //Update the values when released
      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        updateLength.setText( String.valueOf(currentProgress) );
      } // end onStopTrackingTouch(SeekBar)
    }); // end OnSeekBarChangeListner

    //listeners for text boxes
    // number of elements text box
    numElements.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
          numElem.setProgress( Integer.parseInt( numElements.getText().toString() ) );
          
          return true;
        } // end if
        return false;
      } // end onEditorAction(TextView, int, KeyEvent)
    }); // end OnEditorActionListner()
    updateLength.setOnEditorActionListener(new OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
          updateLen.setProgress( Integer.parseInt( updateLength.getText().toString() ) );
          
          return true;
        } // end if
        return false;
      } // end onEditorAction(TextView, int, KeyEvent)
    }); // end OnEditorActionListner()
    
    //Listener for the on submit button
    submitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        onSubmit();
      } // end onClick(View)
    }); // end setOnClickListner(onClickListener)
    
    numElem.setProgress( 500 );
    updateLen.setProgress( 3 );
    
    //initialize the array to sort to null
    arrayLength = 0;
    arrayToSort = null;
    //seed the random number generator
    randomGenerator = new Random();
  } // end on create(Bundle)

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  } // end onCreateOptionsMenu(Menu)

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    } // end if
    return super.onOptionsItemSelected(item);
  } // end onOptionsSelected(MenuItem)
  
//  @Override
//  public boolean onKeyDown(int keyCode, KeyEvent event) {
//    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//      if(sortView.getVisibility() == View.VISIBLE) {
//        sortView.setVisibility(View.GONE);
//        setContentView(R.layout.activity_main);
//      }
//      else {
//        finish();
//      }
//      
//      return true;
//    }
//    
//    return super.onKeyDown(keyCode, event);
//  }
  
  private void onSubmit() {
    arrayLength = Integer.parseInt( numElements.getText().toString() );
    arrayToSort = new int[arrayLength];
    threadUpdateLength = Short.parseShort( updateLength.getText().toString() );
    
    switch ( selectedItemOrder.getCheckedRadioButtonId() ) {
    case (R.id.randomSorted):
      makeRandomSortedArray();
      Log.v(MAIN_ACTIVITY_TAG, "Made random sorted array");
      break;
    case (R.id.reverseSorted):
      makeReverseSortedArray();
      Log.v(MAIN_ACTIVITY_TAG, "Made reverse sorted array");
      break;
    case (R.id.nearSorted):
      makeNearlySortedArray();
      Log.v(MAIN_ACTIVITY_TAG, "Made nearly array");
      break;
    case (R.id.sorted):
      makeSortedArray();
      Log.v(MAIN_ACTIVITY_TAG, "Made sorted array");
      break;
    default:
      Toast.makeText(this, "You broke something... :D", Toast.LENGTH_SHORT).show();
    } // end switch(itemOrder)
    
    syncToken = new Object();
    
    
    switch ( selectedSort.getCheckedRadioButtonId() ) {
//    case (R.id.bitonicSort):
//      //TODO
//      break;
    case (R.id.bogoSort):
      sortThread = new BogoSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Bogo Sort");
      break;
    case (R.id.bubbleSort):
      sortThread = new BubbleSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Bubble Sort");
      break;
    case (R.id.heapSort):
      sortThread = new HeapSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      break;
    case (R.id.insertionSort):
      sortThread = new InsertionSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      break;
    case (R.id.mergeSort):
      sortThread = new MergeSort(arrayToSort, arrayLength, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Merge Sort");
      break;
    case (R.id.quickSort):
      sortThread = new QuickSort(arrayToSort, arrayLength, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Quick Sort");
      break;
    case (R.id.radixSort):
      sortThread = new RadixSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Radix Sort");
      break;
//    case (R.id.selectionSort):
//      sortThread = new SelectionSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
//      Log.v(MAIN_ACTIVITY_TAG,"Selection Sort");
//      break;
    case (R.id.shellSort):
      sortThread = new ShellSort(arrayLength, arrayToSort, threadUpdateLength, syncToken);
      Log.v(MAIN_ACTIVITY_TAG,"Shell Sort");
      break;
    default:
      Toast.makeText(this, "Umm... You broke something! :D", Toast.LENGTH_SHORT).show();
      Log.v(MAIN_ACTIVITY_TAG,"Bogo Sort");
    } // end switch(selectedSort)
    
    sortView = new UpdateSortView(this, arrayToSort, arrayLength);
    sortView.setBackgroundColor( Color.BLACK );
    setContentView(sortView);
    
    //start the sorting thread
    sortThread.start();
    
    new updateUiThread().execute();

  } // end onSubmit
  
  private void makeSortedArray() {
    for(int s = 0; s < arrayLength; s++) {
      arrayToSort[s] = (s + 1);
    } // end for
  } // end makeSortedArray()
  
  private void makeReverseSortedArray() {
    for (int s = 0,t=arrayLength+1; s < arrayLength; s++) {
      arrayToSort[s] = (--t);
    } // end for
  } // end makeReverseSortedArray()
  
  private void makeRandomSortedArray() {
    makeSortedArray();
    for(int s = 0; s < arrayLength*10; s++) {
      swap( nextRandom(0,arrayLength-1), nextRandom(0,arrayLength-1) );
    } // end for
  } // end makeRandomSortedArray()

  private void makeNearlySortedArray() {
    final short MAX_RANDOM_OFFSET = 4;
    
    makeSortedArray();
    for(int s = 0; s < arrayLength - MAX_RANDOM_OFFSET; s++) {
      swap(arrayToSort[s], arrayToSort[ s + nextRandom(0, MAX_RANDOM_OFFSET) ] );
    } // end for
  } // end makeNearlySortedArray
  
  private void swap(int first, int second) {
    int temp = arrayToSort[first];
    arrayToSort[first] = arrayToSort[second];
    arrayToSort[second] = temp;
  } // end swap(int, int)
  
  private int nextRandom(int min, int max) { return randomGenerator.nextInt(max - min + 1) + min; }
  
  private class updateUiThread extends AsyncTask<Void, Void, Void> {
    /** The system calls this to perform work in a worker thread and
      * delivers it the parameters given to AsyncTask.execute() */
    @Override
    protected Void doInBackground(Void... arg0) {
      return null;
    }
    
    /** The system calls this to perform work in the UI thread and delivers
      * the result from doInBackground() */
    protected void onPostExecute(Void arg0) {
      if ( sortThread.isAlive() ) {
        //redraw the view
        sortView.invalidate();
        
        //request a new view update
        new updateUiThread().execute();
        
        return;
      } // end if

      sortView.invalidate();
      
      new updateUiThread().execute();
      
      return;
    } // end onPostExecute(Void)
  } // end class updateUiThread
} // end class MainActivity
