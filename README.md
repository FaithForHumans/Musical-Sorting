SoftwareEngineeringProject
==========================
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
