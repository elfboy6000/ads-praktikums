package ch.zhaw.ads;

public class BracketServer implements CommandExecutor {
    ListStack stack = new ListStack();
    final char LEFT_BRACE = '\u007B';   // {
    final char RIGHT_BRACE = '\u007D';  // }

    public boolean checkBrackets(String command) {
        for (int i = 0; i < command.length(); i++) {
            char c = command.charAt(i);

            // push only opening brackets
            if (c == '(' || c == '[' || c == LEFT_BRACE || c == '<') {
                stack.push(c);
                continue;
            }

            // handle only closing brackets; ignore other chars
            switch (c) {
                case ')':
                    if (stack.isEmpty() || (char) stack.peek() != '(') return false;
                    stack.pop();
                    break;
                case ']':
                    if (stack.isEmpty() || (char) stack.peek() != '[') return false;
                    stack.pop();
                    break;
                case RIGHT_BRACE:
                    if (stack.isEmpty() || (char) stack.peek() != LEFT_BRACE) return false;
                    stack.pop();
                    break;
                case '>':
                    if (stack.isEmpty() || (char) stack.peek() != '<') return false;
                    stack.pop();
                    break;
                default:
                    // non-bracket char → ignore
            }
        }
        return stack.isEmpty();
    }

    public String execute(String command) {
        return Boolean.toString(checkBrackets(command));
    }
}