package ch.zhaw.ads;

public class WellformedXmlServer implements CommandExecutor {
    ListStack tagStack = new ListStack();

    public boolean checkWellformed(String command) {
        StringBuilder commandBuilder = new StringBuilder(command);
        String token;

        while (!(token = getNextToken(commandBuilder)).isEmpty()) {
            if (token.startsWith("</")) { // Closing tag
                if (tagStack.isEmpty() || !tagStack.pop().equals(token.substring(2, token.length() - 1))) {
                    return false; // Mismatched or extra closing tag
                }
            } else if (token.startsWith("<") && !token.endsWith("/>")) { // Opening tag
                tagStack.push(token.substring(1, token.length() - 1));
            }
        }

        return tagStack.isEmpty(); // Well-formed if stack is empty
    }

    private String getNextToken(StringBuilder command) {
        int start = command.indexOf("<");
        if (start == -1) return ""; // No more tokens
        int end = command.indexOf(">", start);
        if (end == -1) return ""; // Malformed token

        String token = command.substring(start, end + 1);
        command.delete(0, end + 1); // Remove the processed token

        // Strip attributes for opening tags
        if (token.startsWith("<") && !token.startsWith("</") && !token.endsWith("/>")) {
            int spaceIndex = token.indexOf(" ");
            if (spaceIndex != -1) {
                token = token.substring(0, spaceIndex) + ">";
            }
        }

        return token;
    }

    @Override
    public String execute(String command) {
        return Boolean.toString(checkWellformed(command));
    }

    public static void main(String[] args) {
        WellformedXmlServer server = new WellformedXmlServer();
        System.out.println(server.checkWellformed("<a href=\"sugus\"></a>"));
    }
}