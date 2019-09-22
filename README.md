# bird-flock-analyser

This is assignment 1 for Data Structures and Algorithms 2. The purpose of the assignment was to show our knowledge of disjoint sets
by creating our own implementation of them in a program that counts birds.

<br>

The program analyzes images of black birds, counting their number.
The program works by:
  1. Converting the image to black/white based on a treshold value, which can be configured.
  2. Creating disjoint set trees from adjacent black pixels in the converted image.
  3. Counting the number of disjoint set trees that are above the minimum required size (configurable).

<br>

The program in action:
![The program in action](https://i.gyazo.com/9a2560275dabd05e7ccdbeaa76a3df4d.png)
