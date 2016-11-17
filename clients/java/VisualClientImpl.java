import model.CircularUnit;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.Locale;

public class VisualClientImpl implements VisualClient {
    Socket          socket;
    OutputStream    outputStream;
    InputStream     inputStream;
    BufferedReader  reader;
    final String    DEFAULT_HOST = "127.0.0.1";
    final int       DEFAULT_PORT = 13579;//13579

    public VisualClientImpl() {
        Locale.setDefault(new Locale("en", "US"));
        try {
            socket = new Socket(DEFAULT_HOST, DEFAULT_PORT);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VisualClientImpl(String host, int port) {
        Locale.setDefault(new Locale("en", "US"));
        try {
            socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command) {
        try {
            outputStream.write((command + System.lineSeparator()).getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void beginPre() {
        sendCommand("begin pre");
    }

    @Override
    public void beginPost() {
        sendCommand("begin post");
    }

    @Override
    public void beginAbs() {
        sendCommand("begin abs");
    }

    @Override
    public void endPre() {
        sendCommand("end pre");
    }

    @Override
    public void endPost() {
        sendCommand("end post");
    }

    @Override
    public void endAbs() {
        sendCommand("end abs");
    }

    @Override
    public void circle(double x, double y, double r, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("circle %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", x, y, r, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void circle(CircularUnit unit, double radius, Color color) {
        circle(unit.getX(), unit.getY(), radius, color);
    }

    @Override
    public void fillCircle(double x, double y, double r, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("fill_circle %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", x, y, r, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void rect(double x1, double y1, double x2, double y2, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("rect %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", x1, y1, x2, y2, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void rect(CircularUnit unit, Color color) {
        final double r = unit.getRadius();
        final double x = unit.getX();
        final double y = unit.getY();
        rect(x - r, y -r, x + r, y + r, color);
    }

    @Override
    public void fillRect(double x1, double y1, double x2, double y2, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("fill_rect %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", x1, y1, x2, y2, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void arc(double centerX, double centerY, double radius, double startAngle, double arcAngle, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("arc %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", centerX, centerY, radius, startAngle, arcAngle, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void fillArc(double centerX, double centerY, double radius, double startAngle, double arcAngle, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("fill_arc %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", centerX, centerY, radius, startAngle, arcAngle, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void line(double x1, double y1, double x2, double y2, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("line %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f %1.1f", x1, y1, x2, y2, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void text(double x, double y, String msg, Color color) {
        Formatter f = new Formatter();
        sendCommand(f.format("text %1.1f %1.1f %s %1.1f %1.1f %1.1f", x, y, msg, (float) color.getRed()/255, (float) color.getGreen()/255, (float) color.getBlue()/255).toString());
    }

    @Override
    public void stop() {
        try {
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sync(int tickIndex) {
        try {
            while(true) {
                String line = reader.readLine();
                if (line.startsWith("sync ")) {
                    int tick = Integer.parseInt(line.substring(5).trim());
                    sendCommand("ack");
                    if (tick >= tickIndex) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}