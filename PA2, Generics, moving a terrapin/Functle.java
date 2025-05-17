import java.util.function.Function;
import java.util.stream.Stream;

class Functle<T extends Movable<T>> {
    private final Function<T, T> reversed;
    private final Function<T, T> consumer;

    private Functle(Function<T, T> consumer, Function<T, T> reversed) {
        this.consumer = consumer;
        this.reversed = reversed;
    }

    private Functle() {
        this(t -> t, t -> t);
    }

    static <T extends Movable<T>> Functle<T> of() {
        return new Functle<T>();
    }

    public Functle<T> forward(int steps) {
        Function<T, T> newR = t -> {
            t.moveForward(-steps);
            return t;
        };
        Function<T, T> newReversed = newR.andThen(this.reversed);
        Function<T, T> newConsumer = this.consumer.andThen(t -> {
            t.moveForward(steps);
            return t;
        });
        return new Functle<T>(newConsumer, newReversed);
    }

    public Functle<T> backward(int steps) {
        Function<T, T> newR = t -> {
            t.moveForward(steps);
            return t;
        };
        Function<T, T> newReversed = newR.andThen(this.reversed);
        Function<T, T> newConsumer = this.consumer.andThen(t -> {
            t.moveForward(-steps);
            return t;
        });
        return new Functle<T>(newConsumer, newReversed);
    }

    public Functle<T> left(int theta) {
        Function<T, T> newR = t -> {
            t.turnLeft(-theta);
            return t;
        };
        Function<T, T> newReversed = newR.andThen(this.reversed);
        Function<T, T> newConsumer = this.consumer.andThen(t -> {
            t.turnLeft(theta);
            return t;
        });
        return new Functle<T>(newConsumer, newReversed);
    }

    public Functle<T> right(int theta) {
        Function<T, T> newR = t -> {
            t.turnLeft(theta);
            return t;
        };
        Function<T, T> newReversed = newR.andThen(this.reversed);
        Function<T, T> newConsumer = this.consumer.andThen(t -> {
            t.turnLeft(-theta);
            return t;
        });
        return new Functle<T>(newConsumer, newReversed);
    }

    void run(T terp) {
        this.consumer.apply(terp);
    }

    public Functle<T> andThen(Functle<T> terp2) { // changed
        return new Functle<T>(this.consumer.andThen(terp2.consumer),
            terp2.reversed.andThen(this.reversed));
    }

    public Functle<T> loop(int count) { // changed
        Functle<T> newFunct = this;
        for (int i = 1; i < count; i++) {
            newFunct = newFunct.andThen(this);
        }
        if (count == 0) {
            return Functle.<T>of();
        }
        return newFunct;
    }

    public Functle<T> reverse() { //changed
        Functle<T> newFunct = 
            new Functle<T>(
                this.consumer.andThen(this.reversed),
                t -> t);
        return newFunct;
    }

    public Functle<T> comeHome() {
        Functle<T> start = this; 

        Function<T, T> homing = t -> {
            Stream.iterate(start,
                // check hasNext
                x -> !t.equals(() -> x.consumer.apply(t)),
                x -> x.andThen(this))
                .forEach(x -> x.reversed.apply(t));
            return t;
        }; 
        return new Functle<T>(homing, t -> t);
    }

    public String toString() {
        return "Functle";
    }
}