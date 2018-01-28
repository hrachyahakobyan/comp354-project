package com.github.comp354project.service.account.remote;

import com.github.comp354project.service.dao.IRemoteAccountDao;

import javax.inject.Inject;

public class RemoteAccountService implements IRemoteAccountService {
    private IRemoteAccountDao remoteAccountDao;

    @Inject
    public RemoteAccountService(IRemoteAccountDao remoteAccountDao){
        this.remoteAccountDao = remoteAccountDao;
    }

    @Override
    public GetRemoteAccountResponse getAccount(GetRemoteAccountRequest request) throws IllegalArgumentException {
        return null;
    }
}
