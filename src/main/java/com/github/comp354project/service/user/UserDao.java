package com.github.comp354project.service.user;

import com.github.comp354project.service.sqlite.IConnectionService;

import javax.inject.Inject;

public class UserDao {

    private IConnectionService connectionService;

    @Inject
    public UserDao(IConnectionService connectionService){
        this.connectionService = connectionService;
    }
}