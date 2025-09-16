package gestione;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"gestione"})
public class GestioneApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestioneApplication.class, args);
    }
}
