package telran.net.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import telran.net.common.*;

public class ClientSessionHandler implements Runnable {
	final Protocol protocol;
	final Socket socket;
	AtomicBoolean isStopped;

	public ClientSessionHandler(Socket socket, Protocol protocol, AtomicBoolean isStopped) {
		this.socket = socket;
		try {
			this.socket.setSoTimeout(10000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		this.protocol = protocol;
		this.isStopped = isStopped;
	}

	@Override
	public void run() {
		try (socket;
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
			while (!isStopped.get()) {
				try {
					ProtocolRequest request = (ProtocolRequest) input.readObject();
					ProtocolResponse response = protocol.getResponse(request);
					output.writeObject(response);
					output.reset();
				} catch (SocketTimeoutException e) {
					if (isStopped.get()) {
						closeSocet();
						System.out.println("ClientSessionHandler's thread message: Administrator closed the server");
						break;
					}
				}
			}
		} catch (EOFException e) {
			System.out.println("Client closed connection");
		} catch (IOException e) {
			System.out.println("Client closed connection abnormally:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unexpected exception: " + e.getMessage());
			// e.printStackTrace();
		}
		System.out.println("Disconnected client:" + socket.getRemoteSocketAddress());
	}

	public void closeSocet() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
