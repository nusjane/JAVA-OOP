import java.util.Optional;

class ArriveEvent extends Event {
    ArriveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public Pair<Event, Shop> next(Shop shop) {
        return shop.findServer(super.eventTime) // returns Optional<Server>
            .map(availableServer -> {
                Event serveEvent = new ServeEvent(
                    this.customer, this.eventTime, availableServer, 0.0);
                return new Pair<>(serveEvent, shop);
            }) //serve
            .orElse(shop.findQueue() // returns Optional<Server>
                .map(qServer -> {
                    Event waitEvent = new WaitEvent(
                        this.customer, this.eventTime, qServer, true, this.eventTime);
                    return new Pair<>(waitEvent, shop.update(qServer.queue()));
                }) // wait
                .orElseGet(() -> {
                    Event leaveEvent = new LeaveEvent(this.customer, this.eventTime);
                    return new Pair<>(leaveEvent, shop);
                })
            ); // leaves
    }

    public Pair<Pair<Integer, Double>, Integer> getRecord() {
        return new Pair<Pair<Integer, Double>, Integer>(new Pair<>(0, 0.0), 0);
    }

    public boolean isTerminal() {
        return false;
    }

    public boolean addToLog() {
        return true;
    }

    // boolean equals(Event event2) {
    //     if (event2 instanceof ArriveEvent) {
    //         if (super.equals(event2)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public String toString() {
        return String.format("%.3f", eventTime) + " "  + customer + " arrives";
    }
}
