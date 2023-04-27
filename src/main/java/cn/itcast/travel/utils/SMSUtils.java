package cn.itcast.travel.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 *              LTAI5tDihV4heh2vCPWdvZku
	 * ZxG2hdirQHIR5px3MPPklnWaE5gAUq
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param) throws ClientException {
		// 阿里云短信配置
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5tD9ZbsuZ5kyiR7DcY2D", "0kZeAy1ouxxhzORa0E9fCoRATOLPSc");
		//短信发送工具
		IAcsClient client = new DefaultAcsClient(profile);
		// 封装请求参数 模板 签名 手机号 验证码
		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);//手机号
		request.setSignName(signName);//签名
		request.setTemplateCode(templateCode);//短信模板
		request.setTemplateParam("{\"code\":\""+param+"\"}");//验证码
		SendSmsResponse response = client.getAcsResponse(request); //发送短信
		System.out.println("短信发送成功");
		System.out.println(param);

	}

	public static void main(String[] args) {
		try {
			SMSUtils.sendMessage("商之翼","SMS_142946316","15893560741","520");
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}

}
