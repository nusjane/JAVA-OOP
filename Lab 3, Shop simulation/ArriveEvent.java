import java.util.Optional;

class ArriveEvent implements Event {
    private static final int PRIORITY_VALUE = 2;
    private final Customer customer;
    private final  double eventTime;

    ArriveEvent(Customer customer, double eventTime) {
        this.customer = customer;
        this.eventTime = eventTime;
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

    // create serve or leave event and put into pq
    public Pair<Event, Shop> next(Shop shop) {
        return shop.findServer(this.customer) // returns Optional<Server>
            .map(availableServer -> 
                new Pair<Event, Shop>(new ServeEvent(
                    this.customer, this.eventTime, availableServer), 
                shop.update(availableServer.serve(this.customer)))) //serve
            .orElse(new Pair<Event, Shop>(
                new LeaveEvent(this.customer, this.eventTime), shop)); //leaves
    }

    public String toString() {
        return eventTime + " "  + customer + " arrives";
    }
}
