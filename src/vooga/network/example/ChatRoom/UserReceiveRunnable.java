package vooga.network.example.ChatRoom;

import java.io.IOException;
import java.net.SocketException;
import java.util.List;


import vooga.network.INetworkEngine;

public class UserReceiveRunnable implements Runnable
{
	private MainGUI gui;
	private INetworkEngine engine;
	public UserReceiveRunnable(MainGUI gui, INetworkEngine e){
		this.gui = gui;
		this.engine = e;
	}

	@Override
	public void run()
	{
		List<Object> received;
		while(engine.isConnected()){
			received = engine.update();
			for(Object object : received){
				try{
					gui.messageShow.append((String)object+"\n");
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		gui.messageShow.append("the user receive runnable ends\n");
	}
}
