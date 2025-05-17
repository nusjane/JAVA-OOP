import java.util.Optional;
import java.util.function.Function;

class Log<T> {
    private final T t;
    private final String log;

    private Log(T t) {
        this.t = t;
        this.log = "";
    }

    private Log(T t, String log) {
        this.t = t;
        this.log = log;
    }

    static <T> Log<T> of(T t) {
        return Log.<T>of(t, "");
    }

    static <T> Log<T> of(T t, String log) {
        return Optional.ofNullable(t)
            .filter(y -> !(t instanceof Log<?>))
            .flatMap(x -> Optional.ofNullable(log))
            .map(z -> new Log<T>(t,log))     
            .orElseThrow(() -> new IllegalArgumentException("Invalid arguments"));
    }

    <K> Log<K> map(Function<? super T, K> f) {
        return Log.<K>of(f.apply(t), this.log);
    }

    <K> Log<K> flatMap(Function<? super T, Log<K>> f) {
        Log<K> newLog = f.apply(this.t);
        K tValue = newLog.t;
        String addLog = "";
        if (!newLog.log.isEmpty() && !this.log.isEmpty()) {
            addLog = "\n" + newLog.log;
        } else if (!newLog.log.isEmpty() && this.log.isEmpty()) {
            addLog = newLog.log;
        }
        return Log.<K>of(tValue, this.log + addLog);
    }

    boolean equals(Log<? super T> l) {
        return this.t.equals(l.t) && this.log.equals(l.log);
    }

    public String toString() {
        String string = "Log[" + t + "]";
        if (!log.isEmpty()) {
            string += "\n" + log;
        }
        return  string;
    }
}
