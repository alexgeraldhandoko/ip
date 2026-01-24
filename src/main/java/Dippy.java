public class Dippy {
    public static void main(String[] args) {
        greet();
        sayFarewell();
    }

    public static void greet() {
        String greeting = "Hello! I'm Dippy\nWhat can I do for you?\n";
        String horizontalLine = "__________________________________________________";
        System.out.println(horizontalLine);
        System.out.println(greeting);
    }

    public static void sayFarewell() {
        String farewell = "Bye. Hope to see you again soon!\n";
        String horizontalLine = "__________________________________________________";
        System.out.println(horizontalLine);
        System.out.println(farewell);
        System.out.println(horizontalLine);
    }
}
