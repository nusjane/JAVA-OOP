class Server {
    private final int x;
    private final double nextAvailableTime;
    private final int inQueue;
    private final int maxQueue;
    
    Server(int x, int maxQueue, double nextAvailableTime, int inQueue) {
        this.x = x;
        this.nextAvailableTime = nextAvailableTime;
        this.inQueue = inQueue;
        this.maxQueue = maxQueue;
    }

    Server(int x, int maxQueue) {
        this(x, maxQueue, 0, 0);
    }

    boolean canWait() {
        return this.inQueue < maxQueue;
    }

    boolean canServe(double time) {
        return this.nextAvailableTime <= time;
    }

    double availableTime() {
        return this.nextAvailableTime;
    }

    Server serve(double nextAvailableTime) {
        return new Server(
            this.x, maxQueue, nextAvailableTime,
            Math.max(0, this.inQueue - 1));
    }

    Server queue() {
        return new Server(
            this.x, maxQueue, this.nextAvailableTime,
            Math.min(maxQueue, this.inQueue + 1));
    }

    boolean isServerEqual(Server s) {
        return this.x == s.x;
    }

    public String toString() {
        return "server " + x;
    }
}