package me.jarad.capella.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import me.jarad.capella.security.SecurityUtils;
import me.jarad.capella.services.BackendService;
import me.jarad.capella.ui.forms.LoginForm;
import me.jarad.capella.ui.forms.MainScreenForm;
import me.jarad.capella.ui.view.ErrorView;

@SpringUI
// No @Push annotation, we are going to enable it programmatically when the user logs on
// runo, reindeer, chameleon, base, valo
@Theme("runo") // Looks nicer
public class SecuredUI extends UI {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    BackendService backendService;

    @Autowired
    SpringViewProvider viewProvider;

    @Autowired
    ErrorView errorView;

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Vaadin and Spring Security Demo - Hybrid Security");
        if (SecurityUtils.isLoggedIn()) {
            showMain();
        } else {
            showLogin();
        }
    }

    private void showLogin() {
        setContent(new LoginForm(this::login));
    }

    private void showMain() {
        setContent(new MainScreenForm(this));
    }

    @Override
    public void detach() {
        //timer.cancel();
        super.detach();
    }

    private boolean login(String username, String password) {
        try {
            Authentication token = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));
            // Reinitialize the session to protect against session fixation attacks. This does not work
            // with websocket communication.
            VaadinService.reinitializeSession(VaadinService.getCurrentRequest());
            SecurityContextHolder.getContext().setAuthentication(token);
            // Now when the session is reinitialized, we can enable websocket communication. Or we could have just
            // used WEBSOCKET_XHR and skipped this step completely.
            getPushConfiguration().setTransport(Transport.WEBSOCKET);
            getPushConfiguration().setPushMode(PushMode.AUTOMATIC);
            // Show the main UI
            showMain();
            return true;
        } catch (AuthenticationException ex) {
            return false;
        }
    }



    private void handleError(com.vaadin.server.ErrorEvent event) {
        Throwable t = DefaultErrorHandler.findRelevantThrowable(event.getThrowable());
        if (t instanceof AccessDeniedException) {
            Notification.show("You do not have permission to perform this operation",
                Notification.Type.WARNING_MESSAGE);
        } else {
            DefaultErrorHandler.doDefault(event);
        }
    }

    public BackendService getBackendService() {
        return backendService;
    }

    public SpringViewProvider getViewProvider() {
        return viewProvider;
    }

    public ErrorView getErrorView() {
        return errorView;
    }

}
