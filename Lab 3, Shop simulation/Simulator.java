import java.util.List;
import java.util.stream.Stream;

class Simulator {
    private final int numOfServers;
    private final int numOfCustomers;
    private final List<Pair<Integer, Pair<Double, Double>>> arrivals;

	Simulator(int numOfServers, int numOfCustomers, List<Pair<Integer, Pair<Double,Double>>> arrivals) {
		this.numOfServers = numOfServers;
        this.numOfCustomers = numOfCustomers;
        this.arrivals = arrivals;
	}

    State run() {
    	PQ<Event> pq = new PQ<Event>();
    	for (Pair<Integer, Pair<Double, Double>> x : arrivals) {
    		pq = pq.add(new ArriveEvent(new Customer(x.t(), x.u().t(), x.u().u()), x.u().t()));
    	}

        return Stream.iterate(new State(pq, new Shop(numOfServers)),
                state -> !state.isEmpty(),
                state -> state.next())
            .reduce(new State(new PQ<Event>(), new Shop(0)), (x, y) -> y) ;
    }
}