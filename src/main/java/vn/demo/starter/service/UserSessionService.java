package vn.demo.starter.service;

public interface UserSessionService {

    boolean checkUserSession(String tokenId);
    void removeExpiredSession(String tokenId);
}
