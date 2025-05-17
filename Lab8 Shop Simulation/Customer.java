class Customer {
    private final int x;
    private final double arrival;
    
    Customer(int x, double arrival) {
        this.x = x;
        this.arrival = arrival;
    }

    int compareCustomerIndex(Customer customer2) {
        int indexComparison = this.x - customer2.x;
        if (indexComparison < 0) {
            return -1;
        }
        return 1;
    }

    public boolean equals(Customer customer2) {
        return this.x == customer2.x;
    }

    public String toString() {
        return "customer " + x;
    }
}