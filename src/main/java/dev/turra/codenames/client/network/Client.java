package dev.turra.codenames.client.network;

import dev.turra.codenames.client.Main;
import dev.turra.codenames.common.network.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Represents the client that is connected to the server.
 */
public class Client implements Runnable {

	private String host;
	private int port;

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public boolean running = false;
	private IPacketListener listener;

	/**
	 * Initialize the client
	 * @param host The IP address of the server
	 * @param port The port of the server
	 */
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	/**
	 * Create a connection between the client and the server
	 * Assign the packet listener to GameManager to handle incoming packets
	 * @throws IOException Thrown if the connection fails
	 */
	public final void connect() throws IOException{
		socket = new Socket(host, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		listener = Main.manager;
		new Thread(this).start();
		System.out.println("Connected to " + host + ":" + port);
	}

	/**
	 * Close the connection between the client and the server
	 */
	public final void close() {
		try {
			running = false;
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send a packet to the server
	 * @param packet The packet to send
	 */
	public void sendPacket(Packet packet) {
		try {
			out.writeObject(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handle incoming packets
	 */
	@Override
	public final void run() {
		try {
			running = true;

			while (running) {
				try {
					Packet data = (Packet) in.readObject();
					listener.received(data);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
