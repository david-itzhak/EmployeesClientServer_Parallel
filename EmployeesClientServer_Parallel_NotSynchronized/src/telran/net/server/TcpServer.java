package telran.net.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import telran.net.common.*;

public class TcpServer implements Runnable {
	int port;
	Protocol protocol;
	ServerSocket serverSocket;
	AtomicBoolean isStopped = new AtomicBoolean(false);
	ExecutorService executor;
	int nThreds = 8;

	public TcpServer(int port, Protocol protocol) {
		this.port = port;
		this.protocol = protocol;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(10000);
		} catch (IOException e) {
			throw new RuntimeException("Port in use " + port);
		}
		executor = Executors.newFixedThreadPool(nThreds);
	}
	
	public void setIsStopped (boolean stop) {
		isStopped.set(stop);
		System.out.println("Server's thred got the comand: Stop server");
	}

	@Override
	public void run() {
		System.out.println("Server start and is listened on port " + port);
		SocketAddress clientAddr = null;
		while (!isStopped.get()) {
			try {
				Socket socket = serverSocket.accept();
				clientAddr = socket.getRemoteSocketAddress();
				System.out.println("Connected client:" + clientAddr);
				ClientSessionHandler client = new ClientSessionHandler(socket, protocol, isStopped);
				executor.execute(client);
				//new Thread(client).start();
			} catch (SocketTimeoutException e) {
				if(isStopped.get()) {
					try {
						List<Runnable> pendingClients = executor.shutdownNow();
						pendingClients.forEach(client -> ((ClientSessionHandler)client).closeSocet());
						serverSocket.close();
						System.out.println("Server's thread message: Administrator closed the server");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Server's thread message: is stopped");
	};
}
