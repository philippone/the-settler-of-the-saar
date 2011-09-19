package de.unisaarland.cs.sopra.ai;

import java.io.IOException;
import java.net.UnknownHostException;

import de.unisaarland.cs.sopra.common.Client;


public class AiMain {

	public static void main(String[] args) {
		try {
			Client.main(args);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
