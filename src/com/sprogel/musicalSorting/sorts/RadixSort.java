package com.sprogel.musicalSorting.sorts;

import java.util.Arrays;
import java.util.LinkedList;

import android.util.Log;

public class RadixSort extends Thread
{
  public Object syncToken;
  int length;
  int[] array;
  short delay;
  
  private final String RADIX_SORT_TAG = "Radix Sort";
  
  int maxDigitSymbols;
 
  @SuppressWarnings("unchecked")
  // base 10
  // LinkedList is also a Queue
  private LinkedList<Integer>[] counter = new LinkedList[] {
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>(),
    new LinkedList<Integer>()
  };
 
  public RadixSort(int l, int[] a, short d, Object token)
  {
    length = l;
    array = a;
    delay = d;
    syncToken = token;
    
    String temp = length + "";
    
    maxDigitSymbols = temp.length();
  }
 
  public void run()
  {
    synchronized (syncToken) {
      int mod = 10;
      int dev = 1;
      for (int i = 0; i < maxDigitSymbols; i++, dev *= 10, mod *= 10) {
        System.out.println(Arrays.toString(array));
        for(int j = 0; j < array.length; j++) {
          int bucket = (array[j] % mod) / dev;
          counter[bucket].add(array[j]);
        }
        int pos = 0;
        for(int j = 0; j < counter.length; j++) {
          Integer value = null;
          while ((value = counter[j].poll()) != null) {
            try{
              Thread.sleep(delay);
            }catch(InterruptedException e){
              Log.w(RADIX_SORT_TAG, "Unable to pause Radix Sort.\n" + e.toString() );
            } // end try catch
            array[pos++] = value;
          } // end while
        } // end for
      } // end for
    } // end synchronized
  } // end run()
} // end class RadixSort()