package com.tensquare.friend.service;

import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFriend(String userid, String friendid) {
        // 先判断userid到friendid是否有数据，有就是重复添加好友，返回0
        if (friendDao.findByUseridAndFriendid(userid, friendid) != null) {
            return 0;
        }
        // 添加好友，让好友表中userid到friendid方向的type为0
        Friend friend = new Friend(userid, friendid, "0");
        friendDao.save(friend);
        // 判断从friendid到userid是否有数据，如果有，把双方的状态都改为1
        if (friendDao.findByUseridAndFriendid(friendid, userid) != null) {
            friendDao.updateIslike("1", userid, friendid);
            friendDao.updateIslike("1", friendid, userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {
        // 先判断是否已经是非好友
        if (noFriendDao.findByUseridAndFriendid(userid, friendid) != null) {
            return 0;
        }
        NoFriend noFriend = new NoFriend(userid, friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        // 删除好友表中userid到friendid这条数据
        friendDao.deleteFriend(userid, friendid);
        // 更新好友表中friend到userid的islike为0
        friendDao.updateIslike("0", friendid, userid);
        // 非好友表中添加数据
        addNoFriend(userid, friendid);
    }
}
