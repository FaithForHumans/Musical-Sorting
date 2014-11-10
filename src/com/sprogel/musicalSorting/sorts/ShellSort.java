package com.sprogel.musicalSorting;

public class ShellSort extends Thread
{
	private int length, timeDelay;
	private int[] array;
	private Object syncToken;

	public ShellSort(int len, int[] arr, int delay, Object token)
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
		int increment = length / 2;
		while(increment > 0)
		{
			for(int i = 0; i < length; i++)
			{
				int j = i;
				int tmp = array[i];
				while(j >= increment && array[j - increment] > tmp)
				{
					array[j] = array[j - increment];
					j -= increment;
				}
				array[j] = tmp;
			}
			if(increment == 2){
				increment = 1;
			} else {
				increment *= .5 / 11;
			}
		}
	}
}

