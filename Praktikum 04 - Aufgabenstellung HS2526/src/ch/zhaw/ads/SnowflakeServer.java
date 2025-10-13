package ch.zhaw.ads;

public class SnowflakeServer implements CommandExecutor {
    Turtle turtle;

    public SnowflakeServer() {
        turtle = new Turtle();
    }

    @Override
    public String execute(String command) {
        turtle.clear();
        snowflake(Integer.parseInt(command), 0.7);
        snowflake(Integer.parseInt(command), 0.7);
        snowflake(Integer.parseInt(command), 0.7);
        return turtle.getTrace();
    }

    private void snowflake(int level, double dist) {
        if (level == 0) {
            turtle.move(dist);
        } else {
            level--;
            dist = dist / 3;
            snowflake(level, dist);
            turtle.turn(60);
            snowflake(level, dist);
            turtle.turn(-120);
            snowflake(level, dist);
            turtle.turn(60);
            snowflake(level, dist);
        }
    }
}