package com.sprogel.musicalSorting;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
  
  private Random randomGenerator; // random number generator
  private final String MAIN_ACTIVITY_TAG = "Main Activity"; // log purposes
  
  public int[] arrayToSort; // array that needs to be sorted
  public int arrayLength; // array to be sorted length
  public short threadUpdateLength; // the delay in which the screen is updated
  
  private EditText numElements; // text field for number of elements to be sorted
  private EditText updateLength; // text field update delay length
  private RadioGroup selectedSort; // group for the sort to be used
  private RadioGroup selectedItemOrder; // group for the original item order
  
  private Timer sortTimer; // the timer that sets the update lentgh
  private Thread sortThread; // the thread that contains the sort to be ran
  public Object syncToken; // the token that controls sortThread wait and notify
  private UpdateSortView sortView; // the view that draws the visual representation o the screen

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    
    //get the elements of the view to be displayed
    numElements = (EditText) findViewById(R.id.numElements);
    updateLength = (EditText) findViewById(R.id.updateLength);
    selectedSort = (RadioGroup) findViewById(R.id.selectedSort);
    selectedItemOrder = (RadioGroup) findViewById(R.id.selectedItemOrder);
    
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
  
  //TODO
  private void onSubmit() {
    arrayLength = Integer.parseInt( numElements.getText().toString() );
    arrayToSort = new int[arrayLength];
    threadUpdateLength = Short.parseShort( updateLength.getText().toString() );
    
    switch ( selectedItemOrder.getCheckedRadioButtonId() ) {
    case (R.id.randomSorted):
      makeRandomSortedArray();
      break;
    case (R.id.reverseSorted):
      makeReverseSortedArray();
      break;
    case (R.id.nearSorted):
      makeNearlySortedArray();
      break;
    case (R.id.sorted):
      makeSortedArray();
      break;
    default:
      Toast.makeText(this, "You broke something... :D", Toast.LENGTH_SHORT).show();
    } // end switch(itemOrder)
    
    syncToken = new Object();
    
    switch ( selectedSort.getCheckedRadioButtonId() ) {
    case (R.id.bitonicSort):
      //TODO
      break;
    case (R.id.bogoSort):
      sortThread = new BogoSort(arrayLength, arrayToSort, threadUpdateLength);
      break;
    case (R.id.bubbleSort):
      //TODO
      break;
    case (R.id.heapSort):
      //TODO
      break;
    case (R.id.insertionSort):
      //TODO
      break;
    case (R.id.mergeSort):
      //TODO
      break;
    case (R.id.quickSort):
      //TODO
      break;
    case (R.id.radixSort):
      sortThread = new RadixSort(arrayLength, arrayToSort, threadUpdateLength);
      break;
    case (R.id.selectionSort):
      //TODO
      break;
    case (R.id.shellSort):
      //TODO
      break;
    default:
      Toast.makeText(this, "Umm... You broke something! :D", Toast.LENGTH_SHORT).show();
    } // end switch(selectedSort)
    
    sortThread.run();
    sortTimer = new Timer();
    sortTimer.scheduleAtFixedRate(new SortController(), 0, threadUpdateLength);
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
      swap( arrayToSort[nextRandom(1,arrayLength)], arrayToSort[nextRandom(1,arrayLength)] );
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
    int temp = first;
    first = second;
    second = temp;
  } // end swap(int, int)
  
  private int nextRandom(int min, int max) { return randomGenerator.nextInt(max - min + 1) + min; }
  
  private class SortController extends TimerTask {
    
    @Override
    public void run() {
      try {
        if( sortThread.isAlive() ) {
          sortThread.wait();
          sortView.invalidate();
        } // end if
        else {
          sortView.sortCompleted();
          sortView.invalidate();
          sortTimer.cancel();
        } // end else
        
      } catch ( InterruptedException e ) {
        Toast.makeText(getApplicationContext(), "Failed to pause the sorting thread.", Toast.LENGTH_SHORT).show();
        Log.e( MAIN_ACTIVITY_TAG, "Failed to pause sorting thread.\n" + e.toString() );
      } // end try catch
    } // end run()
  } // end class SortController
} //end class MainActivity
