import java.util.*;

public class MergeSort extends Thread
{
    int[] array;
    int length;
    short delay;
    
    public MergeSort(int[] a, int l, short d)
    {
        array = a;
        length = l;
        delay = d;
    }
    
    public void run()
    {
        mergeSort(array);
    }

	public void mergeSort(int [ ] a)
	{
		int[] tmp = new int[length];
		mergeSort(a, tmp,  0,  length - 1);
	}


	private void mergeSort(int [ ] a, int [ ] tmp, int left, int right)
	{
		if( left < right )
		{
			int center = (left + right) / 2;
			mergeSort(a, tmp, left, center);
			mergeSort(a, tmp, center + 1, right);
			merge(a, tmp, left, center + 1, right);
		}
	}


    private void merge(int[ ] a, int[ ] tmp, int left, int right, int rightEnd )
    {
        int leftEnd = right - 1;
        int k = left;
        int num = rightEnd - left + 1;

        while(left <= leftEnd && right <= rightEnd)
        {
            if(a[left] <= a[right])
                tmp[k++] = a[left++];
            else
                tmp[k++] = a[right++];
            try{
                Thread.sleep(delay);
            }catch(InterruptedException e){}
        }

        while(left <= leftEnd)    // Copy rest of first half
            tmp[k++] = a[left++];

        while(right <= rightEnd)  // Copy rest of right half
            tmp[k++] = a[right++];

        // Copy tmp back
        for(int i = 0; i < num; i++, rightEnd--)
            a[rightEnd] = tmp[rightEnd];
    }
 }
