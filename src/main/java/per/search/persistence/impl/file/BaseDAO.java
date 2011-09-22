package per.search.persistence.impl.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class BaseDAO {

	protected boolean put(String key, Object value) {
		boolean returnValue = false;
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			String dirName = prepareDir();
			fout = new FileOutputStream(dirName + File.separator + key);
			oos = new ObjectOutputStream(fout);
			oos.writeObject(value);
			returnValue = true;
		} catch (Exception e) {
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (Exception dontCare) {
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception dontCare) {
				}
			}
		}
		return returnValue;
	}

	protected Object get(String key) {
		Object returnValue = null;
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try {
			String dirName = prepareDir();
			fin = new FileInputStream(dirName + File.separator + key);
			ois = new ObjectInputStream(fin);
			returnValue = ois.readObject();
		} catch (Exception e) {
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (Exception dontCare) {
				}
			}
			if (fin != null) {
				try {
					fin.close();
				} catch (Exception dontCare) {
				}
			}
		}
		return returnValue;
	}

	private String prepareDir() {
		String parent = "file_db";
		if (!new File(parent).exists()) {
			new File(parent).mkdir();
		}
		String dirName = parent + File.separator + this.getClass().getSimpleName();
		if (!new File(dirName).exists()) {
			new File(dirName).mkdir();
		}
		return dirName;
	}
}