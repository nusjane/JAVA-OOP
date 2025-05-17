import java.util.Optional;

abstract class Event implements Comparable<Event> {
    protected static final double THRESHOLD = 1E-15;
    protected final Customer customer;
    protected final double eventTime;

    Event(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
    }

    @Override
    public int compareTo(Event event2) {
        if (this.eventTime < event2.eventTime) {
            return -1;
        }
        if (Math.abs(this.eventTime - event2.eventTime) < THRESHOLD) {
            if (this.isTerminal() == this.isTerminal()) {
                return this.customer.compareCustomerIndex(event2.customer);
            }
            if (this.isTerminal()) {
                return -1;
            }
        }
        return 1;
    }

    // boolean equals(Event event2) {
    //     return this.customer.equals(event2.customer) && this.eventTime == event2.eventTime;
    // }

    abstract Pair<Pair<Integer, Double>, Integer> getRecord();

    abstract boolean isTerminal();

    abstract boolean addToLog();
    
    abstract Pair<Event, Shop> next(Shop shop);
}
