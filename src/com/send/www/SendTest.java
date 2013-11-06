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
		//推送配置文件 ServletPath--p12文件所在的路径
		String path = ServletPath;
		//证书密码
		String password = "dongzhong";
		//接收推送的机器Token
		String deviceToken = "ef876bd26c37a6c3c51d3077c4e1aa2111ff8ba9c49eba4baa61617e7fb576a4";
		
		//推送的message，参照apple文档
		String message = "{\"aps\":{\"alert\":\"您有一条新的信息。\",\"sound\":\"default\"},\"url\":\"http://*******\"}";
		//推送后图标上表示的数字
		int count = 1;
				
		PushNotificationPayload payLoad = PushNotificationPayload.fromJSON(message);

		payLoad.addBadge(count);    // 图标小红圈的数值
		payLoad.addSound("default");// 铃音 默认

		PushNotificationManager pushManager = new PushNotificationManager();

		// True 产品推送服务器  false 测试的服务器
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
