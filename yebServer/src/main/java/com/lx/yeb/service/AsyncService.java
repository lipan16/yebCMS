package com.lx.yeb.service;

import java.util.concurrent.Future;

public interface AsyncService{

    public Future<String> sendSMS();
}
