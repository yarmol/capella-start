package me.jarad.capella;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;

import me.jarad.capella.persistance.ModelMongoRepository;
import me.jarad.capella.security.VaadinSessionSecurityContextHolderStrategy;

@SpringBootApplication(exclude =
        {
        SecurityAutoConfiguration.class
})
@EnableMongoRepositories(basePackageClasses = ModelMongoRepository.class)
public class CapellaStartApplication {


    @Configuration
    @EnableGlobalMethodSecurity(securedEnabled = true)
    public static class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            //@formatter:off
            auth
                .inMemoryAuthentication()
                    .withUser("admin").password("p").roles("ADMIN", "USER")
                    .and()
                    .withUser("user").password("p").roles("USER");
            //@formatter:on
        }

        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return authenticationManager();
        }

        static {
            // Use a custom SecurityContextHolderStrategy
            SecurityContextHolder.setStrategyName(VaadinSessionSecurityContextHolderStrategy.class.getName());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CapellaStartApplication.class, args);
    }
}
