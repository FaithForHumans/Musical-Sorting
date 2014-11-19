package com.sprogel.musicalSorting.sorts;
import java.lang.Thread;

import android.util.Log;

public class SelectionSort extends Thread
{
  private int length, timeDelay;
  private int[] array;
  private Object syncToken;
  private final String SELECTION_SORT_TAG = "Selection Sort";

  public SelectionSort(int len, int[] arr, int delay, Object token)
  {
    length = len;
    timeDelay = delay;
    array = arr;
    syncToken = token;
  }

  public void run(int length, int[] array, int timeDelay)
  {
    synchronized(syncToken) 
    {
      sort();
    }
  }

  private void sort()
  {
    int tmp, minIndex;
    for (int i = 0; i < length - 1; i++) 
    {
      minIndex = i;
      for (int j = i + 1; j < length; j++)
      {
        try{
              Thread.sleep(timeDelay);
        }catch(InterruptedException e){
          Log.w(SELECTION_SORT_TAG, "Unable to pause Selection Sort.\n" + e.toString() );
        }
        if (array[j] < array[minIndex])
        {
          minIndex = j;
        }
      }
      if (minIndex != i) 
      {
        tmp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = tmp;
      }
    }
  }
}