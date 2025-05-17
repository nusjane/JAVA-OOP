import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer,Double>> arrivals;
    private final int qmax;
    private final Supplier<Double> serviceTime;
    private final State finalState;

    Simulator(int numOfServers, int qmax, Supplier<Double> serviceTime,
        int numOfCustomers, List<Pair<Integer,Double>> arrivals, State finalState) {
        this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
        this.qmax = qmax;
        this.serviceTime = serviceTime;
        this.finalState = finalState;
    }

    Simulator(int numOfServers, int qmax, Supplier<Double> serviceTime, 
        int numOfCustomers, List<Pair<Integer,Double>> arrivals) {
        this(numOfServers, qmax, serviceTime, numOfCustomers, arrivals, 
            new State(new PQ<Event>(), new Shop(0, () -> 0.0, qmax)));
    }

    Simulator run() {
        PQ<Event> pq = new PQ<>();
        for (Pair<Integer,Double> x : arrivals) {
            Event arriveEvent = new ArriveEvent(new Customer(x.t(), x.u()), x.u()); // necessary?
            pq = pq.add(arriveEvent);
        }

        State finalState = Stream.iterate(new State(pq, new Shop(numOfServers, serviceTime, qmax)),
                state -> !state.isEmpty(),
                state -> state.next())
            .reduce(new State(new PQ<Event>(), new Shop(0, serviceTime, qmax)), (x, y) -> y);
        return new Simulator(this.numOfServers, this.qmax, this.serviceTime, 
            this.numOfCustomers, this.arrivals, finalState);
    }

    public String toString() {
        Pair<Pair<Integer, Double>, Integer> records = finalState.getRecord();
        double averageWatitingTime = 
            records.t().t() == 0 ? 0.0 : (records.t().u() / records.t().t());
        String recordedString = String.format(
                "%.3f", averageWatitingTime)
                + " " + records.t().t() + " " + records.u();
        return finalState.toString() + "\n[" + recordedString + "]";
    }
}