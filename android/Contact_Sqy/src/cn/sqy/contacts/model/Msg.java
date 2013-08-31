package cn.sqy.contacts.model;

public class Msg {
	private int id;// �洢��_id��
	private int threadId;// �Ի�ID��
	private int msgCount;// ��������
	private int unreadCount;// δ����������
	private String address;// ��Ϣ����
	private String person;// ��ϵ������
	private int msgMark;// ��Ϣ�ı��(�ж��Ƿ��͵Ļ��ǽ��յ�)1:����;2:����
	private int read;// �����Ϣ�Ƿ����Ķ�,0δ����1�Ѷ�;
	private String msgDate;// ��Ϣ������
	private String content;// ��Ϣ������

	/**
	 * �޲ι��캯��
	 */
	public Msg() {
	}

	/**
	 * �вι��캯��(+1)
	 * 
	 * @param contactId
	 *            ��ϵ��ID��
	 * @param msgMark
	 *            ��Ϣ�ı��(�ж��Ƿ��͵Ļ��ǽ��յ�)
	 * @param msgDate
	 *            ��Ϣ������
	 * @param content
	 *            ��Ϣ������
	 */
	public Msg(int id, int threadId, int msgCount, int unReadCount,
			String address, String person, int msgMark, int read,
			String msgDate, String content) {
		this.id = id;
		this.threadId = threadId;
		this.msgCount = msgCount;
		this.unreadCount = unReadCount;
		this.address = address;
		this.person = person;
		this.msgMark = msgMark;
		this.read = read;
		this.msgDate = msgDate;
		this.content = content;
	}

	/**
	 * �вι��캯��(+2)
	 * 
	 * @param threadId
	 *            �Ի�ID��
	 * @param address
	 *            ��Ϣ����
	 * @param person
	 *            ��Ϣ��ϵ��
	 * @param msgMark
	 *            ��Ϣ�ı��(�ж��Ƿ��͵Ļ��ǽ��յ�)
	 * @param msgDate
	 *            ��Ϣ������
	 * @param content
	 *            ��Ϣ������
	 */
	public Msg(int threadId, int msgCount, int unReadCount, String address,
			String person, int msgMark, int read, String msgDate, String content) {
		super();
		this.threadId = threadId;
		this.msgCount = msgCount;
		this.unreadCount = unReadCount;
		this.address = address;
		this.person = person;
		this.msgMark = msgMark;
		this.read = read;
		this.msgDate = msgDate;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public int getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(int msgCount) {
		this.msgCount = msgCount;
	}

	public int getUnreadCount() {
		return unreadCount;
	}

	public void setUnreadCount(int unreadCount) {
		this.unreadCount = unreadCount;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public int getMsgMark() {
		return msgMark;
	}

	public void setMsgMark(int msgMark) {
		this.msgMark = msgMark;
	}

	public int getRead() {
		return read;
	}

	public void setRead(int read) {
		this.read = read;
	}

	public String getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(String msgDate) {
		this.msgDate = msgDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Msg [id=" + id + ", threadId=" + threadId + ", msgCount="
				+ msgCount + ", unreadCount=" + unreadCount + ", address="
				+ address + ", person=" + person + ", msgMark=" + msgMark
				+ ", read=" + read + ", msgDate=" + msgDate + ", content="
				+ content + "]";
	}

}
