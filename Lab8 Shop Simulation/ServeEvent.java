import java.util.Optional;

class ServeEvent extends Event {
    private final Server server;
    private final double durationWaited;

    ServeEvent(Customer customer, double eventTime, Server server, double durationWaited) {
        super(customer, eventTime);
        this.server = server;
        this.durationWaited = durationWaited;
    }

    public Pair<Event, Shop> next(Shop shop) {
        double serviceTime = shop.getServiceTime();
        Event doneEvent = new DoneEvent(customer, super.eventTime + serviceTime);
        Shop updatedShop = shop.update(server.serve(super.eventTime + serviceTime));
        return new Pair<>(doneEvent, updatedShop);
    }

    public Pair<Pair<Integer, Double>, Integer> getRecord() {
        return new Pair<Pair<Integer, Double>, Integer>(new Pair<>(1, durationWaited), 0);
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean addToLog() {
        return true;
    }

    // boolean equals(Event event2) {
    //     if (event2 instanceof ServeEvent) {
    //         if (super.equals(event2)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public String toString() {
        return String.format("%.3f", eventTime) + " "  + customer + " serves by " + server;
    }
}
