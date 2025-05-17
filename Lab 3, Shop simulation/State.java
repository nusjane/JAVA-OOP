class State {
    private final PQ<Event> oldPQ;
    private final Shop shop;
    private final String log;
    private final boolean isEmpty;

    State(PQ<Event> oldPQ, Shop shop) {
        this.oldPQ = oldPQ;
        this.shop = shop;
        this.log = "";
        this.isEmpty = false;
    }

    State(PQ<Event> oldPQ, Shop shop, String log, boolean isEmpty) {
        this.oldPQ = oldPQ;
        this.shop = shop;
        this.log = log;
        this.isEmpty = isEmpty;
    }

    State next() { 
        return oldPQ.poll().t()
            .map(e -> {
                if (e.next(shop).t() != e) { 
                    return new State(oldPQ.poll().u().add(e.next(shop).t()),
                    e.next(shop).u(),
                    log + e.toString() + "\n",
                    false);
                } else {
                    return new State(oldPQ.poll().u(),
                    shop,
                    log + e.toString() + "\n",
                    false);
                }
            })
            .orElse(new State(new PQ<Event>(), shop, log, true));
    }



    boolean isEmpty() {
        return this.isEmpty;
    }

    public String toString() {
        if (log.length() > 0) {
            return log.substring(0, log.length() - 1);
        } else {
            return log;
        }
    }
}