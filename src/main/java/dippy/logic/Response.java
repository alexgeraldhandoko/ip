package dippy.logic;

import dippy.task.Task;

import java.util.ArrayList;

/**
 * Class representing Dippy's response
 */
public class Response {
    private final String replyString;
    private ResponseType responseType;
    private ArrayList<Task> responseTaskList;
    private boolean shouldExit = false;

    public Response(String replyString, ResponseType responseType, ArrayList<Task> responseTaskList,
        boolean shouldExit) {
        this.replyString = replyString;
        this.responseType = responseType;
        this.responseTaskList = responseTaskList;
        this.shouldExit = shouldExit;
    }

    public Response(String replyString, boolean shouldExit) {
        this.replyString = replyString;
        this.shouldExit = shouldExit;
    }

    public Response(String replyString) {
        this.replyString = replyString;
    }

    public String getReply() {
        return replyString;
    }

    public boolean getShouldExit() {
        return shouldExit;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public ArrayList<Task> getResponseTaskList() {
        return responseTaskList;
    }
}
