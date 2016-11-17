import model.CircularUnit;

import java.awt.*;

public interface VisualClient {
    /**
     * start queueing commands to be displayed either before main drawing
     */
    void beginPre();

    /**
     * start queueing commands to be displayed either after main drawing
     */
    void beginPost();

    /**
     * start queueing commands to be displayed on the absolute coordinates
     */
    void beginAbs();

    /**
     * mark either "pre" queue of commands as ready to be displayed
     */
    void endPre();

    /**
     * mark either "post" queue of commands as ready to be displayed
     */
    void endPost();

    /**
     * mark either "abs" queue of commands as ready to be displayed
     */
    void endAbs();

    /**
     * draw a circle at (x, y) with radius r and color color
     */
    void circle(double x, double y, double r, Color color);

    /**
     * draw a circle at (x, y) with radius r and color color
     */
    void circle(CircularUnit unit, double v, Color color);

    /**
     * draw a filled circle at (x, y) with radius r and color color
     */
    void fillCircle(double x, double y, double r, Color color);

    /**
     * draw a rect with corners at (x, y) to (x, y) with color color
     */
    void rect(double x1, double y1, double x2, double y2, Color color);

    /**
     * draw a rect with corners at (x, y) to (x, y) with color color
     */
    void rect(CircularUnit unit, Color color);

    /**
     * draw a filled rect with corners at (x1, y1) to (x2, y2) with color color
     */
    void fillRect(double x1, double y1, double x2, double y2, Color color);

    /**
     * draw a arc with center at (centerX, centerY), radius radius and angle arcAngle, started from startAngle with color color, angles in radians
     */
    void arc(double centerX, double centerY, double radius, double startAngle, double arcAngle, Color color);

    /**
     * draw a filled arc with center at (centerX, centerY), radius radius and angle arcAngle, started from startAngle with color color, angles in radians
     */
    void fillArc(double centerX, double centerY, double radius, double startAngle, double arcAngle, Color color);

    /**
     * draw a line from (x1, y1) to (x2, y2) with color color
     */
    void line(double x1, double y1, double x2, double y2, Color color);

    /**
     * show msg at coordinates (x, y) with color color
     */
    void text(double x, double y, String msg, Color color);

    void stop();

    /**
     * synchronizes local-runner with the render commands from bot, call AFTER you have sent
     * render commands for tick=tickIndex
     */
    void sync(int tickIndex);
}
