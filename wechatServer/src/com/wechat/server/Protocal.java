package com.wechat.server;

public interface Protocal {
	
				//服务器接收口令
	int sendCode = 1;							//发送验证码
	int userRegister = 2;						//普通用户注册
	int eUserRegister = 3;						//企业用户注册
	int userLogin = 4;							//普通用户登陆
	int eUserLogin = 5;						//企业用户登陆
	int sendtesxt = 6;							//发送文本信息***
	int sendFile = 7;							//发送文件信息***
	int updaHeadImg = 8;						//更新头像
	int userUpdateMessage = 9;				//普通用户更新信息更换邮箱
	int sendUpdateCode = 10;					//发送更换邮箱验证码
	int userUpdateMessageNotCode = 11;		//普通用户不更新验证码来更新信息
	int sendVoice = 12;						//发送语音***
	int sendImg = 13;							//发送表情包***
	int sendMessage = 14;						//发送消息*****
	int sendGroupMessage = 15;				//发送群消息
	int delLike = 16;							//取消赞
	int addLike = 17;							//增加赞
	int addComment = 18;						//增加评论
	int delComment = 19;						//删除评论
	int addMoment = 20;						//增加朋友圈
	int delMoment = 21;						//删除朋友圈
	int addeBullein = 22;						//企业用户发布公告
	int addSession = 26;
	int addGroup = 27;
	
	
	
	int deleBulletin = 25;						//删除公告
	int eCountSum = 23;						//统计关注人数
	int eCountSex = 24;						//统计关注人数
	//27
	int searchUser = 28;
	int searcheUser = 29;
	int addUser = 30;
	int addeUser = 31;
	int delUser = 32;
	int deleUser = 33;
	int sendeCode = 34;
	int eUpdateNo = 35;
	int eUpdate = 36;
	int delGroupM = 37;
	int delGroup = 38;
	int flushf = 39;
	int addGroupM = 40;
	int sendGMessage = 41;
	int eflush = 42;
	
	
	
	
				//客户端接收口令
	int sendCodeError = 51;						//发送验证码失败
	int sendCodeSuccess = 52;						//发送验证码成功
	int registerError = 53;						//注册失败
	int registerError_code = 54;					//注册失败，验证码不正确
	int registerError_id = 55;					//注册失败，账号已经存在
	int registerSuccess = 56;						//注册成功
	int loginError = 57;							//登陆失败
	int notNumber = 58;							//账号不存在登陆失败
	int notPassword = 59;							//密码不正确登陆失败
	int loginSuccess = 60;							//登陆成功
	int sendMessageError = 61;					//发送信息失败
	int sendMessageSuccess = 62;					//发送信息成功
	
	int acceptText = 63;							//接收文本信息
	int acceptFile = 64;							//接收文件信息
	int accpetImg = 73;							//接收表情包
	int accpetVoice = 74;							//接收语音
	int accpetGroupMessage = 75;					//接收群消息
	
	int sendFileSuccess = 65;						//发送文件成功口令
	int sendFileError = 66;						//发送文件失败口令
	int updaHeadImgSuccess = 67;					//更新头像成功
	int updaHeadImgError = 68;					//更新头像失败
	int userUpdateMessageSuccess = 69;			//普通用户更新信息成功
	int userUpdateMessageError = 70;				//普通用户更新信息失败
	int updateSendCodeError = 71;					//普通用户发送验证码成功
	int updateSendCodeSuccess = 72;				//普通用户发送验证码失败
	
	int accpetLastMoment = 76;					//接收朋友圈
	int eLoginSuccess = 77;							//企业用户登陆成功
	
	int updateVoice = 78;							//更新语音控件事件
	//80
		int addeBulleinSuccess = 79;					//发布公告成功
		int addeBulleinError = 80;					//发布公告失败
		int deleBulletinSuccess = 81;					//删除公告成功
		int deleBulletinError = 82;					//删除公告失败
		int addSessionSuccess = 83;					
		int addSessionError = 84;
		int addGroupSuccess = 85;
		int addGroupError = 86;
		int searchUserSuccess = 87;
		int searchUserError = 88;
		int searcheUserSuccess = 89;
		int searcheUserError = 90;
		int addUserSuccess = 91;
		int addUserError = 92;
		int addeUserSuccess = 93;
		int addeUserError = 94;
		int delUserSuccess = 95;
		int delUserError = 96;
		int deleUserSuccess = 97;
		int deleUserError = 98;
		int sendeCodeSuccess = 99;
		int sendeCodeError = 100;
		int eUpdateSuccess = 101;
		int eUpdateError = 102;
		int delGroupMSuccess = 103;
		int delGroupMError = 104;
		int delGroupSuccess = 105;
		int delGroupError = 106;
		int flushfSuccess = 107;
		int addGroupMSuccess = 108;
		int addGroupMError = 109;
		int sendGMessageSuccess = 110;
		int sendGMessageError = 111;
		int acceptGText = 112;
		int acceptGFile = 113;
		int acceptGVoice = 114;
		int acceptGImg = 115;
		int eflushSuccess = 42;
}
