package ch.zhaw.ads;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;

public class BracketServer implements CommandExecutor {
    private String imp; // String to check
    private int pos; // actual position
    private final static String EOT = "\0"; // end of text
    private final static List<String> OBRACKETS = Arrays.asList("(", "[", "{", "<", "/*");
    private final static List<String> CBRACKETS = Arrays.asList(")", "]", "}", ">", "*/");
    private final static List<String> BRACKETS = new LinkedList<>();

    public BracketServer () {
        BRACKETS.clear();
        BRACKETS.addAll(OBRACKETS);
        BRACKETS.addAll(CBRACKETS);
    }

    private String getNextBracket() {
        while (pos < imp.length()) {
            for (String b : BRACKETS) {
                if (imp.startsWith(b, pos)) {
                    pos += b.length();
                    return b;
                }
            }
            pos++;
        }
        return EOT;
    }

    public boolean checkBrackets(String command) {
        Stack stack = new ListStack();
        imp = command;
        pos = 0;
        String c = getNextBracket();
        while (c != EOT) {
            // opening bracket?
            if (OBRACKETS.contains(c)) {
                stack.push(c);
                // closing bracket?
            } else if (CBRACKETS.contains(c)) {
                if (stack.isEmpty()) { // too many closing brackets
                    return false;
                }
                String c1 = (String) stack.pop();
                // brackets correspond?
                if (OBRACKETS.indexOf(c1) != CBRACKETS.indexOf(c)) {
                    return false;
                }
            }
            c = getNextBracket();
        }
        return stack.isEmpty();
    }

    public String execute(String command) {
        return checkBrackets(command) ? "ok\n" : "error at " + pos + "\n";
    }
}