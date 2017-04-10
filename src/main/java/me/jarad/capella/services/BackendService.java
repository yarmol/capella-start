package me.jarad.capella.services;

import com.vaadin.ui.UI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import me.jarad.capella.application.MainApplicationContext;
import me.jarad.capella.model.security.User;
import me.jarad.capella.persistance.services.MongoService;
import me.jarad.capella.persistance.services.UserService;
import me.jarad.capella.security.SecurityManager;

@Service
public class BackendService{
    private Logger LOGGER = LoggerFactory.getLogger(BackendService.class);


    @Autowired
    private ApplicationContext context;

    @Autowired
    private MongoService mongoService;

    @Secured("ROLE_ADMIN")
    public String adminMethod() {
        return "Hello from an admin method";
    }

    @Secured({ "ROLE_ADMIN", "ROLE_USER" })
    public String userMethod() {
        SecurityManager securityManager = new SecurityManager();

        Object data = securityManager.getUi().getData();

        Object data1 = UI.getCurrent().getData();

         if (data != null) {
             MainApplicationContext appContext = (MainApplicationContext) data;
             Map<Class, Class> entityService = appContext.getEntityService();
             LOGGER.info("UserService 2 = " + context.getBean(entityService.get(User.class)));

         }

        Collection<? extends GrantedAuthority> authorities = securityManager.getAuthorities();

        LOGGER.info(securityManager.getSession().toString());
//        LOGGER.info("uiData= " + UI.getCurrent().getData().toString());

        LOGGER.info("UserService 1 = " + context.getBean(UserService.class));

        LOGGER.info("authorities = " + authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return "Hello from a user method";
    }

}
