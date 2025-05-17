class WaitEvent extends Event {
    private final Server server;
    private final boolean addToLog;
    private final double arrivalTime;

    WaitEvent(
        Customer customer, 
        double eventTime, 
        Server server, 
        boolean addToLog, 
        double arrivalTime) {
        super(customer, eventTime);
        this.server = server;
        this.addToLog = addToLog;
        this.arrivalTime = arrivalTime;
    }
    
    // if allocated server is open, create serve event, otherwise, return new wait event
    Pair<Event, Shop> next(Shop shop) {
        return shop.findParticularServer(this.server) //returns Optional<Server>
            .map(s -> { // make it generate another waitEvent
                if (s.canServe(super.eventTime)) {
                    double durationWaited = super.eventTime - arrivalTime;
                    Event serveEvent = new ServeEvent(
                        this.customer, this.eventTime, s, durationWaited);
                    return new Pair<>(serveEvent, shop);
                } else {
                    Event waitEvent = new WaitEvent(
                        this.customer, s.availableTime(), s, false, this.arrivalTime);
                    return new Pair<>(waitEvent, shop);
                }
            })
            .orElse(new Pair<>(this, shop));
    }

    public Pair<Pair<Integer, Double>, Integer> getRecord() {
        return new Pair<Pair<Integer, Double>, Integer>(new Pair<>(0, 0.0), 0);
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean addToLog() {
        return addToLog;
    }

    // boolean equals(Event event2) {
    //     if (event2 instanceof WaitEvent) {
    //         if (super.equals(event2)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public String toString() {
        return String.format("%.3f", super.eventTime) + " "  + customer + " waits at " + server;
    }
}