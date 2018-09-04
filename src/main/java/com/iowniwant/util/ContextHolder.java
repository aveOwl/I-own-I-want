package com.iowniwant.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

public class ContextHolder {
    private static final String USER_ID = "user_id";

    private static final Logger LOG = LoggerFactory.getLogger(ContextHolder.class);

    private ContextHolder() {
    }

    public static Long getUserIdFromServletContext(final HttpServletRequest request) {
        Long id = (Long) request.getServletContext().getAttribute(USER_ID);

        if (id == null) {
            throw new EntityNotFoundException(
                    "Did not find any objects in ServletContext by attribute name: " + USER_ID);
        }

        LOG.debug("Fetching user id from ServletContext: {}", id);

        return id;
    }
}
