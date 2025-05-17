import java.util.List;
import java.util.stream.IntStream;
import java.util.Optional;

class Shop {                                                                  
    private final int numOfServers; 
    private final List<Server> servers;

    Shop(int n) {                                                             
        numOfServers = n;                                                    
        servers = IntStream.rangeClosed(1,n)
            .mapToObj(i -> new Server(i))
            .toList();                   
    }

    Shop(int n, List<Server> servers) {                                                             
        numOfServers = n;                                                    
        this.servers = servers;          
    }

    //returns an available server
    Optional<Server> findServer(Customer c) {
        return servers.stream()
            .filter(s -> s.canServe(c))
            .findFirst();
    } 
                                                                     
    public String toString() {                                                
        return servers.toString();                                                   
    }      

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
        return new Shop(this.numOfServers, sv);
    }                                                
}                                                                
                                                                               