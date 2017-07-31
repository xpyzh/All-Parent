package udp;

/**
 * Created by youzhihao on 2017/7/26.
 */
public class LogEvent {

    //需要发送到的机器ip
    private String ip;

    private int port;

    //日志时间戳
    private String time;

    //日志内容
    private String message;

    public LogEvent() {
    }

    public LogEvent(String ip, int port, String time, String message) {
        this.ip = ip;
        this.port = port;
        this.time = time;
        this.message = message;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
