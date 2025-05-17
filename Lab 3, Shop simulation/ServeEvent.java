import java.util.Optional;

class ServeEvent implements Event {
    private static final int PRIORITY_VALUE = 3;
    private final Customer customer;
    private final double eventTime;
    private final Server server;

    ServeEvent(Customer customer, double eventTime, Server server) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.server = server;
    }

    @Override
    public int compareTo(Event e) {
        return e.compare(this.eventTime, this.PRIORITY_VALUE, this.customer);
    }

    public int compare(double eventTime1, int priorityValue1, Customer customer1) {
        if (eventTime1 < this.eventTime) {
            return -1;
        }
        if (Math.abs(this.eventTime - eventTime1) < THRESHOLD) {
            if (priorityValue1 < this.PRIORITY_VALUE) {
                return -1; 
            }
            if (priorityValue1 == this.PRIORITY_VALUE) {
                return customer1.compareCustomerIndex(this.customer);
            }
        }
        return 1;
    }

    public Pair<Event, Shop> next(Shop shop) {
        return new Pair<Event, Shop>(new DoneEvent(customer, eventTime), shop);
    }

    public String toString() {
        return eventTime + " "  + customer + " serve by " + server;
    }
}
