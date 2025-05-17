import java.util.Optional;

class DoneEvent extends Event {
    DoneEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public Pair<Event, Shop> next(Shop shop) {
        Event thisEvent = this;
        return new Pair<>(thisEvent, shop);
    }

    public Pair<Pair<Integer, Double>, Integer> getRecord() {
        return new Pair<Pair<Integer, Double>, Integer>(new Pair<>(0, 0.0), 0);
    }

    public boolean isTerminal() {
        return true;
    }

    public boolean addToLog() {
        return true;
    }

    // boolean equals(Event event2) {
    //     if (event2 instanceof DoneEvent) {
    //         if (super.equals(event2)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public String toString() {
        return String.format("%.3f", eventTime) + " "  + customer + " done";
    }
}
