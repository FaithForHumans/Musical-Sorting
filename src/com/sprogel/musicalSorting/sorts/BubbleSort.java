package com.sprogel.musicalSorting.sorts;
import java.lang.Thread;

public class BubbleSort extends Thread
{
	private int length, timeDelay;
	private int[] array;
	private Object syncToken;

	public BubbleSort(int len, int[] arr, int delay, Object token)
	{
		this.length = len;
		this.timeDelay = delay;
		this.array = arr;
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
		int temp;
			for(int i = 0; i < length; i++)
			{
				for(int j = 1; j < length - i; j++)
				{
					
					if(array[j-1] > array[j])
					{
						temp = array[j-1];
						array[j-1] = array[j];
						array[j] = temp;
					}
				}
			}
	}
}
