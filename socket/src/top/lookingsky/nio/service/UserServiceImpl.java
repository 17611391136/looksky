package top.lookingsky.nio.service;

import top.lookingsky.nio.entity.User;

/**
 * Created by 李明 on 2018/2/1.
 */
public class UserServiceImpl implements UserService {
    @Override
    public void add(User user) {
        System.out.println("user/add");
    }
}
