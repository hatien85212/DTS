package com.dts.adminportal.autotools;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.event.InputEvent;

import com.dts.adminportal.util.Keyboard;

public class GuiAutoToolLinux implements IGuiAutoTool{

	private static GuiAutoToolLinux guiAutoToolLinux;
	private Robot robot;
	private GuiAutoToolLinux(){
	
		
	}
	
	public static GuiAutoToolLinux getGuiAutoToolInstance(){
		if(guiAutoToolLinux == null){
			guiAutoToolLinux = new GuiAutoToolLinux();
		}
		return guiAutoToolLinux;
	}
	
	public void winWait(String title, String text, int timeout) {
		// TODO Auto-generated method stub
		
	}

	public void winActivate(String title) {
		// TODO Auto-generated method stub
		
	}

	public void ControlSetText(String title, String text, String control,
			String filePath) {
		// TODO Auto-generated method stub
		try {
		Keyboard kb = new Keyboard();
			kb.ctrShiftEnd();
			kb.type(filePath);
			kb.type("\n");
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void controlClick(String title, String text, String controlID) {
		// TODO Auto-generated method stub
		
	}

	public boolean winExists(String title) {
		// TODO Auto-generated method stub
		return false;
	}

	public String winGetHandle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	public void send(String keys, boolean isRaw) {
		// TODO Auto-generated method stub
		Keyboard kb;
		try {
			kb = new Keyboard();
			kb.type(keys);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void mouseWheel( String direction, int clicks) {
		// TODO Auto-generated method stub
		robot.mouseWheel(clicks);
	}

	public void winWait(String title) {
		// TODO Auto-generated method stub
		
	}

	public void winMove(String title, String text, int x, int y, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	public void winClose(String title) {
		// TODO Auto-generated method stub
		
	}

	public String winGetTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	
	// Release and Renew Network
	public void releaseRenewNetwork(String status){
		   
		String command="";
		if (status=="release"){
			command = "sudo ifconfig eth0 down";
			String returnstatus=commandExecute(command);
			if (returnstatus==null){
				try {
					System.out.println("Network is down");
					Thread.sleep(20000);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (status=="renew"){
			command = "sudo ifconfig eth0 up";
			commandExecute(command);
			try {
				Thread.sleep(15000);
				String pingStatus=commandExecute("ping google.com -c 2");
				System.out.println(pingStatus);
				
				int loop = 5;
				for (int a =0; a<loop;a++)
				{
					if (pingStatus == null || pingStatus.toLowerCase().contains("unknown host"))
					{
						commandExecute(command);
						Thread.sleep(5000);
						pingStatus=commandExecute("ping google.com -c 2");
						System.out.println(pingStatus);
					} else break;
					
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
       
	}
	
	//Used to execute shell command on linux
	public String commandExecute(String command){
		String s = null; 	 
        try {
   
            Process p = Runtime.getRuntime().exec(command);
             
            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
   
            // read any errors from the attempted command
            s = stdInput.readLine();
            return s;
                        
        }
        catch (IOException e) {
            System.out.println("Error while executing");
            e.printStackTrace();
            return s;
        }
	}
	

	
	
//	public void getIPAddress(){
//    	String interfaceName = "eth0";
//        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
//        Enumeration<InetAddress> inetAddress = networkInterface.getInetAddresses();
//        InetAddress currentAddress;
//        currentAddress = inetAddress.nextElement();
//        while(inetAddress.hasMoreElements())
//        {
//            currentAddress = inetAddress.nextElement();
//            if(currentAddress instanceof Inet4Address && !currentAddress.isLoopbackAddress())
//            {
//                String ip = currentAddress.toString();
//                System.out.println(ip);
//                break;
//            }
//        }
// }
	


}
