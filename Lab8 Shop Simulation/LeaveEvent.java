import java.util.Optional;

class LeaveEvent extends Event {
    LeaveEvent(Customer customer, double eventTime) {
        super(customer, eventTime);
    }

    public Pair<Event, Shop> next(Shop shop) {
        Event thisEvent = this;
        return new Pair<>(thisEvent, shop);
    }

    public Pair<Pair<Integer, Double>, Integer> getRecord() {
        return new Pair<Pair<Integer, Double>, Integer>(new Pair<>(0, 0.0), 1);
    }

    public boolean isTerminal() {
        return true;
    }

    public boolean addToLog() {
        return true;
    }

    // boolean equals(Event event2) {
    //     if (event2 instanceof LeaveEvent) {
    //         if (super.equals(event2)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public String toString() {
        return String.format("%.3f", eventTime) + " "  + customer + " leaves";
    }
}