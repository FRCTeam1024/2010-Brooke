/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mckenzierobotics.y2010.Brooke;

import com.sun.squawk.microedition.io.FileConnection;
import javax.microedition.io.Connector;
import java.io.DataOutputStream;
//import java.io.DataInputStream;

/**
 *
 * @author luke
 */
public class DataLogger {
	DataOutputStream file;
	//file:///output.txt
	public DataLogger(String filename) {
		FileConnection fc;

		try {
			fc = (FileConnection)Connector.open(filename, Connector.WRITE);
			if (Debug.dataLog) {
				fc.create();
				file = fc.openDataOutputStream();
			}
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

	public void println(String str) {
		if (Debug.dataLog) {
			try {
				file.writeUTF(str+"\n");
				file.flush();
			} catch (Exception e) {
				//TODO: handle exception
			}
		}
	}

	public void free() {
		try {
			file.close();
		} catch (Exception e) {
			//TODO: handle exception
		}
	}

}
