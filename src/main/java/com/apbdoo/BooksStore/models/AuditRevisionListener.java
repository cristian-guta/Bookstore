package com.apbdoo.BooksStore.models;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {

//        String currentUser = Optional.ofNullable(SecurityContextHolder.getContext())
//                .map(SecurityContext::getAuthentication)
//                .filter(Authentication::isAuthenticated)
//                .map(Authentication::getPrincipal)
////                .map(User.class::cast)
////                .map(User::getUsername)
////                .orElse("Unknown-User");
//;
//        RevisionInfo audit = (RevisionInfo) revisionEntity;
//        audit.setUser(currentUser);

    }
}
