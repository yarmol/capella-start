package me.jarad.capella.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import me.jarad.capella.persistance.MongoService;

@Service
public class BackendService {


    @Autowired
    private MongoService mongoService;


    @Secured("ROLE_ADMIN")
    public String adminMethod() {
        return "Hello from an admin method";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public String userMethod() {

        mongoService.save("User object");

        return "Hello from a user method";
    }
}
