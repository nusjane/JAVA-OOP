class Server {
    private final int x;
    private final double nextAvailableTime;

    Server(int x) {
        this.x = x;
        this.nextAvailableTime = 0;
    }

    Server(int x, double nextAvailableTime) {
        this.x = x;
        this.nextAvailableTime = nextAvailableTime;
    }

    Server serve(Customer c) {    //c has arrival time
        return new Server(this.x, c.serveTill());
    }

    public String toString() {
        return "server " + x;
    }

    boolean canServe(Customer c) {    //c has arrival time
        return c.canBeServed(nextAvailableTime);
    }

    boolean isServerEqual(Server s) {
        return this.x == s.x;
    }
}