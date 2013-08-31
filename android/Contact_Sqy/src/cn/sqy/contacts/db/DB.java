package cn.sqy.contacts.db;

public interface DB {
	public static final String DATABASENAME = "contact.db";
	public static final int DATABASE_VERSION = 1;

	public interface TABLES {
		/**
		 * ����Contact��
		 * 
		 * @author dell
		 * 
		 */
		public interface CONTACT {
			public static final String TABLENAME = "tbl_contact";

			public interface FIELDS {
				public static final String ID = "id";
				public static final String NAME = "name";
				public static final String NAMEPINYIN = "namePinyin";
				public static final String NICKNAME = "nickName";
				public static final String ADDRESS = "address";
				public static final String COMPANY = "company";
				public static final String BIRTHDAY = "birthday";
				public static final String NOTE = "note";
				public static final String IMAGE = "image";
				public static final String GROUPID = "groupId";
			}

			public interface SQL {
				// ������ϵ�˱��SQL���
				public static final String CREATE = "create table if not exists "
						+ TABLENAME
						+ "("
						+ FIELDS.ID
						+ " integer primary key autoincrement,"
						+ FIELDS.NAME
						+ " varchar(20),"
						+ FIELDS.NAMEPINYIN
						+ " varchar(20),"
						+ FIELDS.NICKNAME
						+ " varchar(20),"
						+ FIELDS.ADDRESS
						+ " varchar(30),"
						+ FIELDS.COMPANY
						+ " varchar(20),"
						+ FIELDS.BIRTHDAY
						+ " varchar(20),"
						+ FIELDS.NOTE
						+ " varchar(40),"
						+ FIELDS.IMAGE
						+ " integer,"
						+ FIELDS.GROUPID + " integer)";
				// ɾ�����SQL���
				public static final String DROPTABLE = "drop table if exists "
						+ TABLENAME;
				// ����һ����¼��SQL���
				public static final String INSERT = "insert into " + TABLENAME
						+ "(" + FIELDS.NAME + "," + FIELDS.NAMEPINYIN + ","
						+ FIELDS.NICKNAME + "," + FIELDS.ADDRESS + ","
						+ FIELDS.COMPANY + "," + FIELDS.BIRTHDAY + ","
						+ FIELDS.NOTE + "," + FIELDS.IMAGE + ","
						+ FIELDS.GROUPID
						+ ") values('%s','%s','%s','%s','%s','%s')";
				// ��������id���޸�һ����¼��SQL���
				public static final String UPDATE = "update " + TABLENAME
						+ " set " + FIELDS.NAME + "='%s'," + FIELDS.NAMEPINYIN
						+ "='%s'," + FIELDS.NICKNAME + "='%s',"
						+ FIELDS.ADDRESS + "='%s'," + FIELDS.COMPANY + "='%s',"
						+ FIELDS.BIRTHDAY + "='%s'," + FIELDS.NOTE + "='%s',"
						+ FIELDS.IMAGE + "=%s," + FIELDS.GROUPID + "=%s where "
						+ FIELDS.ID + "=%s";
				// ɾ��һ����¼��SQL���
				public static final String DELETE = "delete from " + TABLENAME
						+ " where id=%s";
				// ��ѯ��SQL���
				public static final String SELECT = "select *from " + TABLENAME
						+ " where %s";
				// �ı����
				public static final String CHANGEGROUP = "update " + TABLENAME
						+ " set " + FIELDS.GROUPID + "=%s where %s";
			}
		}

		/**
		 * ����Email��
		 * 
		 * @author dell
		 * 
		 */
		public interface EMAIL {
			public static final String TABLENAME = "tbl_email";

			public interface FIELDS {
				public static final String EMAILID = "emailId";
				public static final String ID = "id";
				public static final String EMAILNAME = "emailName";
				public static final String EMAILACOUNT = "emailAcount";
			}

			public interface SQL {
				// �������SQL���
				public static final String CREATE = "create table if not exists "
						+ TABLENAME
						+ "("
						+ FIELDS.EMAILID
						+ " integer primary key autoincrement,"
						+ FIELDS.ID
						+ " integer,"
						+ FIELDS.EMAILNAME
						+ " varchar(20),"
						+ FIELDS.EMAILACOUNT + " varchar(30))";
				// ɾ�����SQL���
				public static final String DROPTABLE = "drop table if exists "
						+ TABLENAME;
				// ����һ����¼��SQL���
				public static final String INSERT = "insert into " + TABLENAME
						+ "(" + FIELDS.ID + "," + FIELDS.EMAILNAME + ","
						+ FIELDS.EMAILACOUNT + ") values('%s','%s','%s')";
				// ��������emailId���޸�һ����¼��SQL���
				public static final String UPDATE = "update " + TABLENAME
						+ " set " + FIELDS.ID + "=%s," + FIELDS.EMAILNAME
						+ "='%s'," + FIELDS.EMAILACOUNT + "='%s' where "
						+ FIELDS.EMAILID + "=%s";
				// ɾ��һ����¼��SQL���
				public static final String DELETE = "delete from " + TABLENAME
						+ " where %s";
				// ��ѯ��SQL���
				public static final String SELECT = "select *from " + TABLENAME
						+ " where %s";
			}
		}

		/**
		 * ����Group��
		 * 
		 * @author dell
		 * 
		 */
		public interface GROUP {
			public static final String TABLENAME = "tbl_group";

			public interface FIELDS {
				public static final String GROUPID = "groupId";
				public static final String GROUPNAME = "groupName";
			}

			public interface SQL {
				// �������SQL���
				public static final String CREATE = "create table if not exists "
						+ TABLENAME
						+ "("
						+ FIELDS.GROUPID
						+ " integer primary key autoincrement,"
						+ FIELDS.GROUPNAME + " varchar(20))";
				// ɾ�����SQL���
				public static final String DROPTABLE = "drop table if exists "
						+ TABLENAME;
				// ����һ����¼��SQL���
				public static final String INSERT = "insert into " + TABLENAME
						+ "(" + FIELDS.GROUPNAME + ") values('%s')";
				// ��������emailId���޸�һ����¼��SQL���
				public static final String UPDATE = "update " + TABLENAME
						+ " set " + FIELDS.GROUPNAME + " = '%s' where "
						+ FIELDS.GROUPID + " = '%s' ";
				// ɾ��һ����¼��SQL���
				public static final String DELETE = "delete from " + TABLENAME
						+ " where groupId=%s";
				// ��ѯ��SQL���
				public static final String SELECT = "select *from " + TABLENAME
						+ " where %s";
			}
		}

		/**
		 * ����IM��
		 * 
		 * @author dell
		 * 
		 */
		public interface IM {
			public static final String TABLENAME = "tbl_im";

			public interface FIELDS {
				public static final String IMID = "imId";
				public static final String ID = "id";
				public static final String IMNAME = "imName";
				public static final String IMACOUNT = "imAcount";
			}

			public interface SQL {
				// �������SQL���
				public static final String CREATE = "create table if not exists "
						+ TABLENAME
						+ "("
						+ FIELDS.IMID
						+ " integer primary key autoincrement,"
						+ FIELDS.ID
						+ " integer,"
						+ FIELDS.IMNAME
						+ " varchar(20),"
						+ FIELDS.IMACOUNT + " varchar(30))";
				// ɾ�����SQL���
				public static final String DROPTABLE = "drop table if exists "
						+ TABLENAME;
				// ����һ����¼��SQL���
				public static final String INSERT = "insert into " + TABLENAME
						+ "(" + FIELDS.ID + "," + FIELDS.IMNAME + ","
						+ FIELDS.IMACOUNT + ") values('%s','%s','%s')";
				// ��������emailId���޸�һ����¼��SQL���
				public static final String UPDATE = "update " + TABLENAME
						+ " set " + FIELDS.ID + "=%s," + FIELDS.IMNAME
						+ "='%s'," + FIELDS.IMACOUNT + "='%s' where "
						+ FIELDS.IMID + "=%s";
				// ɾ��һ����¼��SQL���
				public static final String DELETE = "delete from " + TABLENAME
						+ " where imId=%s";
				// ��ѯ��SQL���
				public static final String SELECT = "select *from " + TABLENAME
						+ " where %s";
			}
		}

		/**
		 * ����Tel��
		 * 
		 * @author dell
		 * 
		 */
		public interface TEL {
			public static final String TABLENAME = "tbl_tel";

			public interface FIELDS {
				public static final String TELID = "telId";
				public static final String ID = "id";
				public static final String TELNAME = "telName";
				public static final String TELNUMBER = "telNumber";
			}

			public interface SQL {
				// �������SQL���
				public static final String CREATE = "create table if not exists "
						+ TABLENAME
						+ "("
						+ FIELDS.TELID
						+ " integer primary key autoincrement,"
						+ FIELDS.ID
						+ " integer,"
						+ FIELDS.TELNAME
						+ " varchar(20),"
						+ FIELDS.TELNUMBER + " varchar(30))";
				// ɾ�����SQL���
				public static final String DROPTABLE = "drop table if exists "
						+ TABLENAME;
				// ����һ����¼��SQL���
				public static final String INSERT = "insert into " + TABLENAME
						+ "(" + FIELDS.ID + "," + FIELDS.TELNAME + ","
						+ FIELDS.TELNUMBER + ") values('%s','%s','%s')";
				// ��������emailId���޸�һ����¼��SQL���
				public static final String UPDATE = "update " + TABLENAME
						+ " set " + FIELDS.ID + "=%s," + FIELDS.TELNAME
						+ "='%s'," + FIELDS.TELNUMBER + "='%s' where "
						+ FIELDS.TELID + "=%s";
				// ɾ��һ����¼��SQL���
				public static final String DELETE = "delete from " + TABLENAME
						+ " where telId=%s";
				// ��ѯ��SQL���
				public static final String SELECT = "select *from " + TABLENAME
						+ " where %s";
			}
		}
	}
}
