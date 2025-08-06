package com.java360.pmanager.domain.exception;

import com.java360.pmanager.infrastructure.exception.RequestException;

import java.util.UUID;

public class MemberNotFoundException extends RequestException {

    public MemberNotFoundException(UUID memberId) {

        super("MemberNotFound", "Member not found: " + memberId);
    }
}
