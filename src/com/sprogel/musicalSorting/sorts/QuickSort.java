package com.sprogel.musicalSorting.sorts;

public class QuickSort extends Thread
{
  public Object syncToken;
  int[] array;
  int length;
  short delay;

  int partition(int arr[], int left, int right)
  {
    int i = left, j = right;
    int tmp;
    int pivot = arr[(left + right) / 2];
    
    while (i <= j)
    {
      while (arr[i] < pivot)
        i++;
        while (arr[j] > pivot)
          j--;
          
        if (i <= j)
        {
          tmp = arr[i];
          arr[i] = arr[j];
          arr[j] = tmp;
          i++;
          j--;
          try{
            Thread.sleep(delay);
            }catch(InterruptedException e){}
        }
      };
     
      return i;
  }

  void sort(int arr[], int left, int right)
  {
    int index = partition(arr, left, right);
    if (left < index - 1)
      sort(arr, left, index - 1);
  
    if (index < right)
      sort(arr, index, right);
  }
  
  public void run()
  {
    synchronized (syncToken) {
      sort(array, 0, length-1);
    }
  }
  
  public QuickSort(int[] a, int l, short d, Object token)
  {
    array = a;
    length = l;
    delay = d;
    syncToken = token;
  }
}
