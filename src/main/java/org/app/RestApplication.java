package org.app;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application; // new import

@ApplicationPath("/api")
public class RestApplication extends Application {
}
