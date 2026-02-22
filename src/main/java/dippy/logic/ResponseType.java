package dippy.logic;

/**
 * This enum indicates what type of response it is since there could be many different types
 * of responses. This is preferred over inheritance into many types of responses since inheritance
 * of data types into the response scatters the data into more places, since some would be held
 * by the parent and some would be held by children, and it's difficult to consolidate this data
 * when we want to extract out the data from a single Response object into an HBox eventually
 */
public enum ResponseType {
    TASK_RESPONSE,
    SIMPLE_RESPONSE;
}
