package micronaut.cache.example.controller

import com.hazelcast.client.HazelcastClient
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.config.Config
import com.hazelcast.core.Hazelcast
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.IMap
import groovy.transform.CompileStatic
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.http.MediaType

@CompileStatic
@Controller("/hello")
class HelloController {
    @Get("/")
    @Produces(MediaType.TEXT_PLAIN)
    String index() {
        "Hello World"
    }

    @Get("/hazelcastServer")
    @Produces(MediaType.TEXT_PLAIN)
    String hazelcastServer() {
        Config cfg = new Config()
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg)
        Map<Integer, String> mapCustomers = instance.getMap("customers")
        mapCustomers.put(1, "Joe")
        mapCustomers.put(2, "Ali")
        mapCustomers.put(3, "Avi")

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());

        Queue<String> queueCustomers = instance.getQueue("customers");
        queueCustomers.offer("Tom")
        queueCustomers.offer("Mary")
        queueCustomers.offer("Jane")
        System.out.println("First customer: " + queueCustomers.poll())
        System.out.println("Second customer: " + queueCustomers.peek())
        System.out.println("Queue Size: " + queueCustomers.size())
        "Hazel cast Server"
    }

    @Get("/hazelcastPort")
    @Produces(MediaType.TEXT_PLAIN)
    String hazelcastPort() {
        Config cfg = new Config()
        cfg.getNetworkConfig().setPort(5900)
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg)
        Map<Integer, String> mapCustomers = instance.getMap("customers")
        mapCustomers.put(1, "Nirav")
        mapCustomers.put(2, "Ali")

        System.out.println("Customer with key 1: "+ mapCustomers.get(1));
        System.out.println("Map Size:" + mapCustomers.size());
        "Hazel cast with new port"
    }

    @Get("/hazelcastClient")
    @Produces(MediaType.TEXT_PLAIN)
    String hazelcastClient() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.addAddress("127.0.0.1:5701");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap map = client.getMap("customers");
        System.out.println("Map Size:" + map.size());
        "Hazel cast sample"
    }
}
