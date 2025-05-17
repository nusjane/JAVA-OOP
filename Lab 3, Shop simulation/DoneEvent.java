import java.util.Optional;

class DoneEvent implements Event {
    private static final int PRIORITY_VALUE = 1;
    private final Customer customer;
    private final double eventTime;

    DoneEvent(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = customer.serveTill();
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
        return new Pair<Event, Shop>(this, shop);
    }

    public String toString() {
        return customer.serveTill() + " "  + customer + " done";
    }
}
