public class Event extends Task {
    private String startDate;
    private String endDate;

    public Event(String name, String startDate, String endDate) {
        super(name);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        String out = "[E]";
        if (getIsDone()) {
            out += "[X] ";
        } else {
            out += "[ ] ";
        }
        out += "(from: " + this.startDate + " to: " + this.endDate + ")";
        return out;
    }
}
