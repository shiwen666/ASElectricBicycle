package com.wxxiaomi.ming.electricbicycle.db;

import com.wxxiaomi.ming.electricbicycle.db.bean.InviteMessage;

import java.util.List;

import rx.Observable;


public interface InviteMessgeDao {
	String TABLE_NAME = "new_friends_msgs";
	String COLUMN_NAME_FROM = "username";
	String COLUMN_NAME_TIME = "time";
	String COLUMN_NAME_REASON = "reason";
	String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";
	String COLUMN_NAME_ID = "id";
	String COLUMN_NAME_NICK = "nickname";
	String COLUMN_NAME_AVATAR = "avatar";

	/**
	 * 获取邀请消息数量
	 * @return
	 */
	int getUnreadNotifyCount();

	/**
	 * 存储一条消息
	 * @param message
	 * @return
	 */
	Integer saveMessage(InviteMessage message);


	/**
	 * 获取未读消息列表
	 * @return
	 */
	List<InviteMessage> getMessagesList();

	Observable<List<InviteMessage>> getMessagesListRx();

	/**
	 * 设置未读的邀请消息的数目
	 * @param count
	 */
//	void saveUnreadMessageCount(int count);
}
