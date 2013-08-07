package cn.sqy.contacts.biz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.os.Environment;

public class FileManager {
	public static final String COMMAND_BACKUP = "backup";
	public static final String COMMAND_RESTORE = "restore";
	public static final String COMMAND_ISCONTACT = "contact";
	public static final String COMMAND_ISMESSAGE = "message";
	private Context mContext;
	private MsgManager msgMgr;

	public FileManager(Context context) {
		this.mContext = context;
		msgMgr = new MsgManager(context);
	}

	public int dbFileOperation(String... params) {
		// Ĭ��·���� /data/data/(����)/databases/*.db
		File dbFile = null;
		File backupDir = null;
		String command1 = params[1];
		if (command1.equals(COMMAND_ISCONTACT)) {
			dbFile = mContext
					.getDatabasePath("/data/data/cn.sqy.contacts/databases/contact.db");
			backupDir = new File(Environment.getExternalStorageDirectory(),
					"contactsBackup");
		} else if (command1.equals(COMMAND_ISMESSAGE)) {
			dbFile = mContext
					.getDatabasePath("/data/data/cn.sqy.contacts/databases/msg.db");
			backupDir = new File(Environment.getExternalStorageDirectory(),
					"msgBackup");
		} else {
			return -1;
		}

		if (!backupDir.exists()) {
			backupDir.mkdirs();
		}
		File backup = new File(backupDir, dbFile.getName());
		String command2 = params[0];
		if (command2.equals(COMMAND_BACKUP)) {
			try {
				if (command1.equals(COMMAND_ISCONTACT)) {
					backup.createNewFile();
					fileCopy(dbFile, backup);
					return 0;
				} else {
					msgMgr.insertMsgsFromSysAllMsgs();// �ٽ�ϵͳ���ŵ��뵽���ݿ�
					backup.createNewFile();// ����ٰ����ݿⱸ�ݵ�SD����
					fileCopy(dbFile, backup);
					return 3;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else if (command2.equals(COMMAND_RESTORE)) {
			try {
				if (command1.equals(COMMAND_ISCONTACT)) {
					fileCopy(backup, dbFile);
					return 1;
				} else {
					fileCopy(backup, dbFile);// �Ȱ����ݿ��SD���ϻָ����ҵ����ݿ�
					msgMgr.insertIntoSysMsgFromMyMsg();// �ٴ��ҵ����ݿ⵼�뵽ϵͳ���ݿ���
					return 4;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else {
			return -1;
		}
	}

	private void fileCopy(File dbFile, File backup) throws IOException {
		FileChannel inChannel = new FileInputStream(dbFile).getChannel();
		FileChannel outChannel = new FileOutputStream(backup).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inChannel != null) {
				inChannel.close();
			}
			if (outChannel != null) {
				outChannel.close();
			}
		}
	}
}
