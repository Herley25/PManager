package com.java360.pmanager.domain.exception;

import com.java360.pmanager.infrastructure.exception.RequestException;

import java.util.UUID;

public class TaskNotFoundException extends RequestException {

    public TaskNotFoundException(UUID taskId) {

        super("TaskNotFound", "Task not found: " + taskId);
    }
}
