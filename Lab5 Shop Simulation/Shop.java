import java.util.List;
import java.util.stream.IntStream;
import java.util.Optional;
import java.util.function.Supplier;

class Shop {                                                                  
    private final int numOfServers; 
    private final List<Server> servers;
    private final Supplier<Double> serviceTime;
    private final int maxQueue;

    Shop(int n, 
        Supplier<Double> serviceTime, 
        int maxQueue, 
        List<Server> servers) {                                                             
        this.numOfServers = n;                                                    
        this.servers = servers;
        this.serviceTime = serviceTime;
        this.maxQueue = maxQueue;       
    }

    Shop(int n, 
        Supplier<Double> serviceTime, 
        int maxQueue) {
        this(n, serviceTime, maxQueue, 
            IntStream.rangeClosed(1,n)
            .mapToObj(i -> new Server(i, maxQueue))
            .toList());                  
    }

    //returns an available server for immediate serving
    Optional<Server> findServer(double time) {
        return servers.stream()
            .filter(server -> server.canServe(time))
            .findFirst();
    }   

    Optional<Server> findQueue() {
        return servers.stream()
            .filter(server -> server.canWait())
            .findFirst();
    }

    Optional<Server> findParticularServer(Server s) {
        return servers.stream()
             .filter(server -> server.isServerEqual(s))
             .findFirst();
    }

    //returns updated shop after findServer gives a server
    Shop update(Server server) {
        List<Server> sv = servers.stream()
            .map(s -> {
                if (s.isServerEqual(server)) {
                    return server;
                } else {
                    return s;
                }
            })
            .toList();
        return new Shop(this.numOfServers, 
            this.serviceTime, 
            this.maxQueue, 
            sv);
    } 

    double getServiceTime() {
        return this.serviceTime.get();
    }
                                                                     
    public String toString() {                                                
        return servers.toString();                                                   
    }                                                
}                                                                
                                                                               