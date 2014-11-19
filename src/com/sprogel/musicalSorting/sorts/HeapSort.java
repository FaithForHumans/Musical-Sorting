package com.sprogel.musicalSorting.sorts;

import android.util.Log;


public class HeapSort extends Thread
{
  private int length, timeDelay;
  private int[] array;
  private Object syncToken;
  private final String HEAPSORT_TAG = "Heap Sort"; 

  public HeapSort(int len, int[] arr, int delay, Object token)
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
		
		heapify(array, length);
	 
		int end = length - 1;
		while(end > 0){
			try{
          Thread.sleep(timeDelay);
      }catch(InterruptedException e){
        Log.w(HEAPSORT_TAG, "Unable to pause Heap Sort.\n" + e.toString() );
      }
			int tmp = array[end];
			array[end] = array[0];
			array[0] = tmp;
			siftDown(array, 0, end - 1);
			end--;
		}
	}

	private void heapify(int[] arr, int len)
	{
		int start = (len - 2) / 2;

		while(start >= 0){
			siftDown(arr, start, len - 1);
			start--;
		}
	}

	private void siftDown(int[] arr, int start, int end)
	{
		int root = start;
	 
		while((root * 2 + 1) <= end){ 
			int child = root * 2 + 1;           
			if(child + 1 <= end && arr[child] < arr[child + 1])
				child = child + 1;           
			if(arr[root] < arr[child]){     
				int tmp = arr[root];
				arr[root] = arr[child];
				arr[child] = tmp;
				root = child;                
			}else
				return;
		}
	}
}
