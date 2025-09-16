package gestione.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Applica a tutti i percorsi dell'API
                .allowedOrigins("http://localhost:4200") // Consenti richieste dalla tua applicazione Angular
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Consenti questi metodi HTTP
                .allowedHeaders("*") // Consenti tutti gli header
                .allowCredentials(true) // Consenti l'invio di cookie e credenziali
                .maxAge(3600); // Durata della cache della preflight request (in secondi)
    }
}
