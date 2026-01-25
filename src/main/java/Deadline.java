public class Deadline extends Task {
    private String date;

    public Deadline(String name, String date) {
        super(name);
        this.date = date;
    }

    @Override
    public String toString() {
        String out = "[D]";
        if (getIsDone()) {
            out += "[X] " + getName();
        } else {
            out += "[ ] " + getName();
        }
        out += " (by: " + this.date + ")";
        return out;
    }
}
