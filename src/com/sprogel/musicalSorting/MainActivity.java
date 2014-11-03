package com.sprogel.musicalSorting;

import java.util.Random;
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
  
  private Random randomGenerator;
  private String MAIN_ACTIVITY_TAG = "Main Activity";
  
  public int[] arrayToSort;
  public int arrayLength;
  public short threadUpdateLength;
  
  private EditText numElements;
  private EditText updateLength;
  private RadioGroup selectedSort;
  private RadioGroup selectedItemOrder;
  
  private SortController sortController;
  private Thread sortThread;
  private UpdateSortView sortingView;
  public Object syncToken;

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
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
  
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
    }
    
    syncToken = new Object();
    
    switch ( selectedSort.getCheckedRadioButtonId() ) {
    case (R.id.bitonicSort):
      //TODO
      break;
    case (R.id.bogoSort):
      //TODO
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
      //TODO
      break;
    case (R.id.selectionSort):
      //TODO
      break;
    case (R.id.shellSort):
      //TODO
      break;
    default:
      Toast.makeText(this, "Umm... You broke something! :D", Toast.LENGTH_SHORT).show();
    }
  }
  
  private void makeSortedArray() {
    for(int s = 0; s < arrayLength; s++) {
      arrayToSort[s] = (s + 1);
    }
  }
  
  private void makeReverseSortedArray() {
    for (int s = 0,t=arrayLength+1; s < arrayLength; s++) {
      arrayToSort[s] = (--t);
    }
  }
  
  private void makeRandomSortedArray() {
    makeSortedArray();
    for(int s = 0; s < arrayLength*10; s++) {
      swap( arrayToSort[nextRandom(1,arrayLength)], arrayToSort[nextRandom(1,arrayLength)] );
    }
  }

  private void makeNearlySortedArray() {
    final short RANDOM_OFFSET = 4;
    
    makeSortedArray();
    for(int s = 0; s < arrayLength - RANDOM_OFFSET; s++) {
      swap(arrayToSort[s], arrayToSort[ s + nextRandom(0, RANDOM_OFFSET) ] );
    }
  }
  
  private void swap(int a, int b) {
    int c = a;
    a = b;
    b = c;
  }
  
  private int nextRandom(int min, int max) {
    return randomGenerator.nextInt(max - min + 1) + min;
  }
  
  private class SortController extends TimerTask {
    
    @Override
    public void run() {
      try {
        if( sortThread.isAlive() ) {
          sortThread.wait();
          sortingView.invalidate();
          
        } // end if
      } catch ( InterruptedException e ) {
        Toast.makeText(getApplicationContext(), "Failed to pause the sorting thread.", Toast.LENGTH_SHORT).show();
        Log.e( MAIN_ACTIVITY_TAG, "Failed to pause sorting thread.\n" + e.toString() );
      }
    }
  }
}
