import java.util.Optional;

class State {
    private final PQ<Event> eventQ;
    private final Shop shop;
    private final String log;
    private final boolean isEmpty;
    private final Pair<Pair<Integer, Double>, Integer> records; 
    //served, totalWaitTime, left

    State(PQ<Event> eventQ, Shop shop, String log, boolean isEmpty,
        Pair<Pair<Integer, Double>, Integer> records) {
        this.eventQ = eventQ;
        this.shop = shop;
        this.log = log;
        this.isEmpty = isEmpty;
        this.records = records;
    }

    State(PQ<Event> eventQ, Shop shop) {
        this(eventQ, shop, "", false, new Pair<>(new Pair<>(0, 0.0), 0));
    }

    State(PQ<Event> eventQ, Shop shop, boolean isEmpty) {
        this(eventQ, shop, "", isEmpty, new Pair<>(new Pair<>(0, 0.0), 0));
    }

    State next() { 
        Optional<Event> optEvent = eventQ.poll().t();
        PQ<Event> updatedQ = eventQ.poll().u();
        return optEvent //returns Optional<Event>
            .map(event -> {
                String updatedLog;
                if (this.log.isEmpty()) {
                    updatedLog = (event.addToLog() ? event.toString() : "");
                } else {
                    updatedLog = log + (event.addToLog() ? "\n" + event.toString() : "");
                }

                Pair<Pair<Integer, Double>, Integer> additionalRecord = event.getRecord();
                if (event.isTerminal()) {
                    return new State(updatedQ, this.shop, updatedLog, false, 
                        this.updateRecords(additionalRecord));
                } else {
                    Pair<Event, Shop> nextEventShopPair = event.next(shop);
                    PQ<Event> newQ = updatedQ.add(nextEventShopPair.t());
                    Shop newShop = nextEventShopPair.u();
                    return new State(newQ, nextEventShopPair.u(), updatedLog, false, 
                        this.updateRecords(additionalRecord));
                }
            })
            .orElse(new State(new PQ<Event>(), shop, true));
    }

    Pair<Pair<Integer, Double>, Integer> updateRecords(
        Pair<Pair<Integer, Double>, Integer> additionalRecord) {
        return new Pair<>(new Pair<>(
            this.records.t().t() + additionalRecord.t().t(),
            this.records.t().u() + additionalRecord.t().u()),
            this.records.u() + additionalRecord.u());
    }

    Pair<Pair<Integer, Double>, Integer> getRecord() {
        return records;
    }

    boolean isEmpty() {
        return this.isEmpty;
    }

    public String toString() {
        return log;
    }
}