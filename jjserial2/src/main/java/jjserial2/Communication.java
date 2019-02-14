package jjserial2;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import com.fazecast.jSerialComm.SerialPort;

import jjserial2.PortListener;

public class Communication {

	public static SerialPort port;
	
	
	public String outPutS;
	

	static int bb = 10;
	static String temp = "";
	
	public static void main (String[]args) throws IOException, SQLException, ClassNotFoundException
	{
		
		
		SerialPort[]allAvailableComPorts = SerialPort.getCommPorts();
		
		port = allAvailableComPorts[0];
		port.openPort();
		
		InputStream input= port.getInputStream();
		OutputStream output = port.getOutputStream();
		input.skip(input.available());
		
		int read;
		
		do {
			
			read = input.read();
			System.out.println((char)read);
		} while (read != 10);
		
		
		
		PortListener listener = new PortListener();
		
		port.addDataListener(listener);
		
		getConnection();
	}

		
		 
	
	
	
	public static void parseByteArray (byte []readBuffer) throws Exception
	{
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String time = dateFormat.format(date);
		
		
		String readttBuffer = new String(readBuffer);
		temp = temp.concat(readttBuffer);
		
		if ((temp.indexOf(bb)+1)>0) {
			
			String outPuts = temp.substring(0, temp.indexOf(bb)+1);
			temp = temp.substring(temp.indexOf(bb)+1);
			
			System.out.println(outPuts);
			
			Connection connection = getConnection();

			String sql = "INSERT INTO arduino (Time, Value) VALUES ('"+time+"','"+outPuts+"')";
			PreparedStatement statement = connection.prepareStatement(sql);
			
			statement.executeUpdate();
			
		
			
					
		
		}
		
		
		
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException 
	{
		
		 final String url = "jdbc:mysql://localhost:3306/coto?useLegacyDatetimeCode=false&serverTimezone=UTC";
		 final String user = "root";
		 final String password = "root";
		 		
		 
		 
		 Connection connection = DriverManager.getConnection(url, user, password);
		 
		 return connection;
		
	}

}
	

