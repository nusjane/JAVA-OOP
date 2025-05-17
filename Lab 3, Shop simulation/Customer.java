class Customer {
    private final int x;
    private final double arrival;
    private final double duration;
    private static final double THRESHOLD = 1E-15;
    
    Customer(int x, double arrival, double duration) {
        this.x = x;
        this.arrival = arrival;
        this.duration = duration;
    }

    boolean canBeServed(double openTime) {
        return this.arrival >= openTime;
    }

    double serveTill() {
        return this.arrival + duration;
    }

    int compareCustomerIndex(Customer customer2) {
        int indexComparison = this.x - customer2.x;
        if (indexComparison < 0) {
            return -1;
        }
        return 1;
    }

    public String toString() {
        return "customer " + x;
    }
}