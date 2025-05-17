import java.util.Optional;

interface Event extends Comparable<Event> {
    static final double THRESHOLD = 1E-15;

    Pair<Event, Shop> next(Shop shop);

    String toString();

    int compare(double eventTime, int priorityValue, Customer customer);
}
