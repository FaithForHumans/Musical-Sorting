import java.util.Arrays;
import java.util.LinkedList;

public class RadixSort extends Thread
{
    int length;
    int[] array;
    short delay;
    
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
 
    public RadixSort(int l, int[] a, short d)
    {
        length = l;
        array = a;
        delay = d;
        
        String temp = length + "";
        
        maxDigitSymbols = temp.length();
    }
 
    public void run()
    {
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
                    }catch(InterruptedException e){}
                    array[pos++] = value;
                }
            }
        }
    }
}
