package es.codeurjc.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
	
	private static ConfigurableApplicationContext app;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    public static void start() {
    	start(new String[] {});
    }

	private static void start(String[] args) {
		if(app == null) {
    		app = SpringApplication.run(Application.class, args);
    	} 
	}    
	
	public static void stop() {
		if(app != null) {
			app.close();
			app = null;
		}
	}
}
