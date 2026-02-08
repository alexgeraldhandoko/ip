package dippy.logic;

/**
 * Class representing Dippy's response
 */
public class Response {
    private final String reply;
    private boolean shouldExit = false;

    public Response(String reply, boolean shouldExit) {
        this.reply = reply;
        this.shouldExit = shouldExit;
    }

    public Response(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }

    public boolean getShouldExit() {
        return shouldExit;
    }
}
