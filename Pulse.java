public class Pulse {
    private String source;
    private String destination;
    private int type;
    private boolean processed;

    public Pulse(String source, String destination, int type) {
        this.source = source;
        this.destination = destination;
        this.type = type;
        processed = false;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public String toString() {
        return "Source: " + source + ", Destination: " + destination + ", Type: " + type + ", Processed: " + processed;
    }
}
