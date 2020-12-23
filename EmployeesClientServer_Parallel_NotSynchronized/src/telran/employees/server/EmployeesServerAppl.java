package telran.employees.server;

import static telran.employees.common.EmployeesApiConstants.*;

import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import telran.net.server.TcpServer;

public class EmployeesServerAppl {
	static boolean mainAppRun = true;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		TcpServer server = new TcpServer(PORT, new EmployeesProtocolController(new EmployeeServiceImpl()));
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(server);
//		Thread serverThread = new Thread(server);
//		serverThread.start();
		System.out.println("Main app started.");
		while(mainAppRun) { // hw
			String adminComand = scanner.nextLine();
			if (adminComand.equalsIgnoreCase("q")) {
				scanner.close();
				server.setIsStopped(true);
				break;
			}
		}
//		try {
//			serverThread.join();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		executor.shutdown();
		System.out.println("Main app message: server's thread stopped.");
		System.out.println("Main app stopped.");
	}
}