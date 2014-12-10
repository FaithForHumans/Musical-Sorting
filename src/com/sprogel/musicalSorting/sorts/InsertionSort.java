package com.sprogel.musicalSorting.sorts;
public class InsertionSort extends Thread
{
  private int length, timeDelay;
  private int[] array;
  private Object syncToken;
  private final String INSERTION_SORT_TAG = "Insertion Sort"; 

  public InsertionSort(int len, int[] arr, int delay, Object token)
  {
    length = len;
    timeDelay = delay;
    array = arr;
    syncToken = token;
  }
 
  public void run()
  {
    synchronized (syncToken)
    {
      sort();
    }
  }

  private void sort()
  {
    for(int i = 1; i < length; i++)
    {
    	try{
          Thread.sleep(delay);
      }catch(InterruptedException e){
        Log.w(INSERTION_SORT_TAG, "Unable to pause Insertion Sort.\n" + e.toString() );
      }
	    int value = array[i];
	    int j = i - 1;
	    while(j >= 0 && array[j] > value)
	    {
	      array[j + 1] = array[j];
	      j = j - 1;
	    }
	    array[j + 1] = value;
  	}
  }
}
