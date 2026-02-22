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
- I asked ChatGPT about appropriate colour scheme for the dialog box to match
  the background image so that they contrast well and improve readability.
- There were some issues with adding styling to each dialog box to create the 
  text bubble effect. Apparently the css couldn't identify the fxml element to be
  styled. ChatGPT recommends that I declare a class for the dialog box that I
  could directly refer to in the css.
- ChatGPT says that VBox can be styled to vertically separate its contents
- What is the difference between spacing and padding?
  Spacing is the space between children, while padding is the space between the
  outer edge of the container and its contents
- I wanted to re-style the way that Dippy outputs the list of tasks in his reply
  to go beyond just simple text. This didn't come to me naturally, so I made some sketches 
  on how I am supposed to change the current codebase to implement this. Then 
  I drafted this plan and sent to ChatGPT:
  
  "Current UI looks like this. I plan to instead of using a label in the dialog box, 
  transform it into a vbox of hboxes, one hbox per task. This is my rough idea:
  1. Instead of a String reply, Response shld contain arrlist<hbox> reply
  2. Then, displayList shld craft these hboxes and return the response
  3. This response will be returned by Parser.displayList and passed to getDippyDialog, 
     which will output the dialogBox with the vbox of hboxes.
  4. which will be used by dippyText in handleUserInput and displayed onto the 
     dialogContainer.

  I should also create a task hbox class so that I can reuse the same styling and such
  for every task I can just pass it to the constructor so that i dont need to manually 
  style each task you know?

  Don't tell me the exact solution, just if this is the right idea or not i want to do 
  myself"

  - After several discussions with ChatGPT, this is the final version that I came up with:

    "So the order of changes would be:

      1. Change the dialog box to have also a vbox instead of just a label.

         2. Create a template hbox for this

         3. Create a method in UI that takes in a list of tasks and converts them to the 
            template.

         4. Change the Response class to have an arrlist of tasks in addition to a String
            reply, and also an enum to indicate whether the response contains a task list 
            that needs to be displayed or not, or it should display something else (good 
            for future modifs to DialogBox formats)

         5. Create extra method getTaskDippyDialog call to pass it an entire response 
            object.

         6. Inside getDippyDialog, return getTaskDippyDialog instead if the Response is 
            flagged as shouldDisplayTasks

         7. Make getTaskDippyDialog method to call on the UI method to build the arrlist
            of hboxes.

         8. add these hboxes to the addAll method of the dialog box's vbox

         9. keep getTaskDippyDialog to still return a dialog box"

- What are layouts, controls, and views in JavaFX? 
  According to ChatGPT:
  Layouts are containers that arrange other nodes on the screen. They don't display
  content themselves, but instead control positioning, spacing, alignment, and resizing
  Controls are nodes that users can interact with. They handle input, focus, clicks,
  typing.
  Views are display-only nodes. They display information but are not interactive.