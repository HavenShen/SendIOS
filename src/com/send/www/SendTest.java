package com.send.www;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import sun.rmi.runtime.Log;

import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class SendTest {
	/**
	 * @param args
	 * @throws JSONException 
	 * @throws KeystoreException 
	 * @throws CommunicationException 
	 */
	public static void main(String[] args) throws JSONException, CommunicationException, KeystoreException {
		
		// TODO Auto-generated method stub
		String ServletPath = "00.p12";
		//���������ļ� ServletPath--p12�ļ����ڵ�·��
		String path = ServletPath;
		//֤������
		String password = "dongzhong";
		//�������͵Ļ���Token
		String deviceToken = "ef876bd26c37a6c3c51d3077c4e1aa2111ff8ba9c49eba4baa61617e7fb576a4";
		
		//���͵�message������apple�ĵ�
		String message = "{\"aps\":{\"alert\":\"����һ���µ���Ϣ��\",\"sound\":\"default\"},\"url\":\"http://*******\"}";
		//���ͺ�ͼ���ϱ�ʾ������
		int count = 1;
				
		PushNotificationPayload payLoad = PushNotificationPayload.fromJSON(message);

		payLoad.addBadge(count);    // ͼ��С��Ȧ����ֵ
		payLoad.addSound("default");// ���� Ĭ��

		PushNotificationManager pushManager = new PushNotificationManager();

		// True ��Ʒ���ͷ�����  false ���Եķ�����
		pushManager.initializeConnection(new AppleNotificationServerBasicImpl(path, password, false));

		List<PushedNotification> notifications = new ArrayList<PushedNotification>();

		Device device = new BasicDevice();

		device.setToken(deviceToken);

		PushedNotification notification = pushManager.sendNotification(device, payLoad, true);

		notifications.add(notification);

		List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
		List<PushedNotification> successNotifications = PushedNotification.findSuccessfulNotifications(notifications);

		int failed = failedNotifications.size();
		int successful = successNotifications.size();
		System.out.println(successful);
	}

}
