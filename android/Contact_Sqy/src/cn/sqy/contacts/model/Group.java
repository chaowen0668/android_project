package cn.sqy.contacts.model;

public class Group {
	private int groupId;// ����ID
	private String groupName;// ��������

	/**
	 * �޲ι��캯��
	 */
	public Group() {

	}

	/**
	 * �вι��캯��
	 * 
	 * @param groupName
	 */
	public Group(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * �вι��캯��(+1)
	 * 
	 * @param groupId
	 *            ����ID��
	 * @param groupName
	 *            ��������
	 */
	public Group(int groupId, String groupName) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", groupName=" + groupName + "]";
	}

}
