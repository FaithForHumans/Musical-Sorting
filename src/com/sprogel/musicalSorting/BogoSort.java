public class BogoSort extends Thread
{
  int length;
  int[] array;
  short delay;
  
  public BogoSort(int l, int[] a, short d)
	{
    length = l;
    array = a;
    delay = d;
	}
  
  public void run()
  {
    while(!isSorted(array))
    {
	    shuffle(array);
        try{
        Thread.sleep(delay);
        }catch(InterruptedException e){}
    }
  }
  
	void shuffle(int[] array)
	{
		//Standard Fisher-Yates shuffle algorithm
		int i = array.length-1;
		while(i > 0)
			swap(array, i--, (int)(Math.random()*i));
	}
  
	void swap(int[] array,int i,int j)
	{
		int temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}
  
	boolean isSorted(int[] array)
	{
		for(int i = 1; i < length; i++)
    {
			if(array[i] < array[i-1])
				return false;
    }
		return true;
	}
}
