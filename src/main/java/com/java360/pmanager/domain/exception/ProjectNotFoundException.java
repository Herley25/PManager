package com.java360.pmanager.domain.exception;

import com.java360.pmanager.infrastructure.exception.RequestException;

import java.util.UUID;

public class ProjectNotFoundException extends RequestException {

    public ProjectNotFoundException(UUID projectId) {
        super("ProjectNotFound", "Project not found: " + projectId);
    }
}
