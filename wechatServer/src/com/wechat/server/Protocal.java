package com.wechat.server;

public interface Protocal {
	
				//���������տ���
	int sendCode = 1;							//������֤��
	int userRegister = 2;						//��ͨ�û�ע��
	int eUserRegister = 3;						//��ҵ�û�ע��
	int userLogin = 4;							//��ͨ�û���½
	int eUserLogin = 5;						//��ҵ�û���½
	int sendtesxt = 6;							//�����ı���Ϣ***
	int sendFile = 7;							//�����ļ���Ϣ***
	int updaHeadImg = 8;						//����ͷ��
	int userUpdateMessage = 9;				//��ͨ�û�������Ϣ��������
	int sendUpdateCode = 10;					//���͸���������֤��
	int userUpdateMessageNotCode = 11;		//��ͨ�û���������֤����������Ϣ
	int sendVoice = 12;						//��������***
	int sendImg = 13;							//���ͱ����***
	int sendMessage = 14;						//������Ϣ*****
	int sendGroupMessage = 15;				//����Ⱥ��Ϣ
	int delLike = 16;							//ȡ����
	int addLike = 17;							//������
	int addComment = 18;						//��������
	int delComment = 19;						//ɾ������
	int addMoment = 20;						//��������Ȧ
	int delMoment = 21;						//ɾ������Ȧ
	int addeBullein = 22;						//��ҵ�û���������
	int addSession = 26;
	int addGroup = 27;
	
	
	
	int deleBulletin = 25;						//ɾ������
	int eCountSum = 23;						//ͳ�ƹ�ע����
	int eCountSex = 24;						//ͳ�ƹ�ע����
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
	
	
	
	
				//�ͻ��˽��տ���
	int sendCodeError = 51;						//������֤��ʧ��
	int sendCodeSuccess = 52;						//������֤��ɹ�
	int registerError = 53;						//ע��ʧ��
	int registerError_code = 54;					//ע��ʧ�ܣ���֤�벻��ȷ
	int registerError_id = 55;					//ע��ʧ�ܣ��˺��Ѿ�����
	int registerSuccess = 56;						//ע��ɹ�
	int loginError = 57;							//��½ʧ��
	int notNumber = 58;							//�˺Ų����ڵ�½ʧ��
	int notPassword = 59;							//���벻��ȷ��½ʧ��
	int loginSuccess = 60;							//��½�ɹ�
	int sendMessageError = 61;					//������Ϣʧ��
	int sendMessageSuccess = 62;					//������Ϣ�ɹ�
	
	int acceptText = 63;							//�����ı���Ϣ
	int acceptFile = 64;							//�����ļ���Ϣ
	int accpetImg = 73;							//���ձ����
	int accpetVoice = 74;							//��������
	int accpetGroupMessage = 75;					//����Ⱥ��Ϣ
	
	int sendFileSuccess = 65;						//�����ļ��ɹ�����
	int sendFileError = 66;						//�����ļ�ʧ�ܿ���
	int updaHeadImgSuccess = 67;					//����ͷ��ɹ�
	int updaHeadImgError = 68;					//����ͷ��ʧ��
	int userUpdateMessageSuccess = 69;			//��ͨ�û�������Ϣ�ɹ�
	int userUpdateMessageError = 70;				//��ͨ�û�������Ϣʧ��
	int updateSendCodeError = 71;					//��ͨ�û�������֤��ɹ�
	int updateSendCodeSuccess = 72;				//��ͨ�û�������֤��ʧ��
	
	int accpetLastMoment = 76;					//��������Ȧ
	int eLoginSuccess = 77;							//��ҵ�û���½�ɹ�
	
	int updateVoice = 78;							//���������ؼ��¼�
	//80
		int addeBulleinSuccess = 79;					//��������ɹ�
		int addeBulleinError = 80;					//��������ʧ��
		int deleBulletinSuccess = 81;					//ɾ������ɹ�
		int deleBulletinError = 82;					//ɾ������ʧ��
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
