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

A-BetterGUI
- I want a background to the chat so that it looks less empty
  ChatGPT offered me two ways to do this:
  1) Do it directly in the FXML
  2) Attach a CSS file to the FXML
  I'm going with option 2) because it looks cleaner when the styling and the fxml elements
  are separated into two different files
- The CSS is not being applied?
  ChatGPT says that in JavaFX, the CSS file can be applied to the scene at
  the start function of the Main application.
  scene.getStylesheets().add(...) expects a URL String, not a URL object, so we
  need to apply the .toExternalForm method to the URL object to turn it into a
  String.
- The ScrollPane is apparently opaque by default, so its opacity has to be
  reduced as well to increase the visibility of the AnchorPane background.
- In JavaFX, many controls automatically come with a default CSS style class,
  including scroll pane. Its class is scroll-pane
- Claude told me that in CSS, the space between selectors matters a lot:
  - .scroll-pane.content means a single element that has both the scroll-pane
    class and the content class.
  - .scroll-pane .content means an element with class content that is insid
    another element with class scroll-pane. 
- In JavaFX, scroll pane itself has a lot of background layers. It has an outer
  control, then a viewport on top of it, then its children content on top of the
  viewport. The viewport and the outer control need to be transparent backgrounds
  for the background of the anchor pane to be visible.