package cn.sqy.contacts.tool;
/*_id��������ţ���100
thread_id���Ի�����ţ���100����ͬһ���ֻ��Ż����Ķ��ţ����������ͬ��
address�������˵�ַ�����ֻ��ţ���+8613811810000
person�������ˣ������������ͨѶ¼����Ϊ����������İ����Ϊnull
date�����ڣ�long�ͣ���1256539465022�����Զ�������ʾ��ʽ��������
protocol��Э��0SMS_RPOTO���ţ�1MMS_PROTO����
read���Ƿ��Ķ�0δ����1�Ѷ�
status������״̬-1���գ�0complete,64pending,128failed
type����������1�ǽ��յ��ģ�2���ѷ���
body�����ž�������
service_center�����ŷ������ĺ����ţ���+8613800755500*/
public class ContantsUtil {
	
	public static final String ACTION_SMS_SEND = "sms.send";
	public static final String ACTION_SMS_DELIVERY = "sms.delivery";
	public static final String SMS_URI_ALL   = "content://sms/";     
	public static final String SMS_URI_INBOX = "content://sms/inbox";   
	public static final String SMS_URI_SEND  = "content://sms/sent";   
	public static final String SMS_URI_DRAFT = "content://sms/draft";  
	public static final String SMS_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	public static final String SMS_ID="_id";
	public static final String SMS_THREADID="thread_id";
	public static final String SMS_ADDRESS="address";
	public static final String SMS_PERSON="person";
	public static final String SMS_DATE="date";
	public static final String SMS_PROTOCOL="protocol";
	public static final String SMS_READ="read";
	public static final String SMS_STATUS="status";
	public static final String SMS_TYPE="type";
	public static final String SMS_BODY="body";
	public static final String SMS_SERVICE_CENTER="service_center";
	
	
}
