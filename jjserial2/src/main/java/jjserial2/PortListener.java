package jjserial2;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;



public class PortListener implements SerialPortDataListener {

	public int getListeningEvents() {
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}

	public void serialEvent(SerialPortEvent event) {
		
		byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
		event.getSerialPort().readBytes(buffer, buffer.length);
		
		try {
			Communication.parseByteArray(buffer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
}}
