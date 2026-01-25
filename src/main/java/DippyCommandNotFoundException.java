public class DippyCommandNotFoundException extends DippyException {
    public static final String message = """
        Oops! I don't know what that means :(
        Please give any of the following valid task commands:
            * todo [task description]
            * event [task description] /by [deadline date]
            * deadline [task description] /from [start date] to [end date]
       """;
    public DippyCommandNotFoundException() {
        super(message);
    }
}
