package ch.zhaw.ads;

public class HilbertServer implements CommandExecutor {
    Turtle turtle;

    public HilbertServer() {
        turtle = new Turtle();
    }

    @Override
    public String execute(String command) {
        int depth = Integer.parseInt(command);
        double dist = 0.8 / (Math.pow(2,depth+1)-1);
        turtle = new Turtle(0.1, 0.1);
        hilbert(depth, dist, -90);
        return turtle.getTrace();
    }

    private void hilbert(int depth, double dist, double angle) {
        turtle.turn(-angle);
        // draw recursive
        if (depth > 0) hilbert(depth-1, dist, -angle);
        turtle.move(dist);
        turtle.turn(angle);
        // draw recursive
        if (depth > 0) hilbert(depth-1, dist, angle);
        turtle.move(dist);
        // draw recursive
        if (depth > 0) hilbert(depth-1, dist, angle);
        turtle.turn(angle);
        turtle.move(dist);
        // draw recursive
        if (depth > 0) hilbert(depth-1, dist, -angle);
        turtle.turn(-angle);
    }
}