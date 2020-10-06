package com.rmit.sept.assignment.initial.security;

public class SecurityConstant {
    public static final String SIGN_UP_URL = "/api/customer";
    public static final String USER_LOGIN = "/api/customer/auth/**";
    public static final String WORKER_LOGIN = "/api/worker/auth/**";
    public static final String KEY = "8x/A?D(G+KbPeShVmYp3s6v9y$B&E)H@McQfTjWnZr4t7w!z%C*F-JaNdRgUkXp2";
    public static final String HEADER_NAME = "Authorisation";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final Long EXPIRATION_TIME = 1000L*60*30;  // 30m expiration time
}
