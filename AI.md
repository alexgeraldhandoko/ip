AI Recommendations/Lessons

Level-0
- br.readLine() returns the line without newline character

Level-1
- br.readLine() is blocking. That means when the code calls br.readLine(),
  it is waiting for the next line from standard input (keyboard in this case)

Level-2
- Strings can be written in blocks as well, just like comments, if they get too
  long

Level-5
- In Java, the call to super(...) must be the first thing the constructor does, 
  and you’re not allowed to read instance fields (like message) before the 
  superclass constructor has run.

Formatting of messages
- Some messages were written in string blocks in Java which greatly improves readability 
  for the developer. However, this causes the indentations to appear as well in the
  resulting string. 
- ChatGPT recommends to use the stripIndent method on the string to get rid of the
  indentations to better format the message for the end user.

Adding extension feature: C-Sort
- In order for an object of a class to be sortable, Java needs either:
  1) The class needs to be a Comparable to itself
  2) The call to Collections.sort() needs a comparable
- I think that the 2) option is better, because in the future we might want to sort it
  by different criteria, so we can just create custom comparators for each criterion,
  instead of having one single ordering for every object of that class.

How to create a copy of a Java Collection
- Sometimes I want to create a copy of a Java Collection, meaning I don't want to have
  another reference to that collection.
- For the sorting feature, I just need to work on a shallow copy. I don't want to modify
  the original list, but I also don't need new Task objects.
- ChatGPT recommends that I can just pass in the original ArrayList to the constructor 
  for an array list.

How to compare two strings lexicographically while ignoring the cases in the string?
- ChatGPT recommends to use the compareToIgnoreCase method for strings