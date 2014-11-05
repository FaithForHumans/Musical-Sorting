#NOTE: THINGS HAVE CHANGED, LOOK AT THE BOTTOM

##SoftwareEngineeringProject

Drop all sorting classes (named appropriately) into the folder:
  src/com/sprogel/musicalSorting
  
I'll make sure any/all includes and package definitions are taken care off. I'll open issues with anything I encounter.

Lets say each class should extend Thread, and your class should overwrite the run() method should be the actual method to sort your code. If you want to make your run class call another function because you're to lazy to copy and paste, be proud of yourself.

The constructor of each sort should take three arguments, in order:
  int: length of array (java does this, blah blah blah, makes things easier to read)
  int[]: the array to be sorted of size length, unknown order. User selects the order, currently random, sorted, reverse, or semi-sorted.
  short: the millisecond delay between each iteration of the sort.
  
Any questions, let me know. Opening issues would be the easiest way to track things.

One more note, commit to your own branch! Name it whatever your heart desires, but please don't commit to master. Only I get to do that :)

Ohh, and don't forget to create a pull requeset. I trust that you are all grown men and can handle conflicts.

#####Updated stuff 
Okay, all of your constructors need to accept an additonal object, which is of type Object. Confused yet? eg:
```
public Object syncToken; // declaration
```
and then in your constructor
```
Constructor(int, int[], short, Object token) {
  //do your stuff
  syncToken = token;
}
```

Q. Okay Spencer you may be asking, we added this cool syncToken, so what's it for? Does it just sit there?
A. Well I'm glad you asked! I'm about to tell you! in your run method, modify it like so:
```
public void run() {
  synchronized (syncToken) {
    Your sort method here.
  } // end synchronized
} // end run()
```

Q. Great! That's it?
A. Heh. Nice try. Two more quick things.
1. I've moved where you're dropping sorts. Same directory as before, just drop it in an internal sorts folder. so the updated directory is /src/com/sprogel/musicalSorting/sorts
2. At the top of your code when you push it, include the following line at the very top above your imports / constructor so I don't have to add it.
```
package com.sprogel.musicalSorting;
```

Q. Ugh. Stop changing stuff. Is that it?
A. If someone wants to do my sorts for me, that would be great :)
