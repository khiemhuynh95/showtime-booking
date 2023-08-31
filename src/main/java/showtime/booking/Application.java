package showtime.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import showtime.booking.datagen.DataGenerator;
import showtime.booking.enums.MovieStatus;
@SpringBootApplication
public class Application implements WebMvcConfigurer {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "true");
        System.setProperty("spring.devtools.restart.additional-paths", "src/*");
        SpringApplication.run(Application.class, args);
        
        //populate db
        DataGenerator.start();
        //DataGenerator.populateTheaters();
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8080") // Add your frontend URL here
                        .allowedMethods("GET")
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
