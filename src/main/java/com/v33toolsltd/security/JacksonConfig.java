package com.v33toolsltd.security;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Hibernate5JakartaModule hibernate5JakartaModule() {
        Hibernate5JakartaModule module = new Hibernate5JakartaModule();
        
        /* 
           FORCE_LAZY_LOADING is set to false by default. 
           This means if a field is Lazy (like Product in CartItems), 
           Jackson will just ignore it instead of trying to load the 
           entire database into your JSON response.
        */
        module.configure(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING, false);
        
        return module;
    }
}
