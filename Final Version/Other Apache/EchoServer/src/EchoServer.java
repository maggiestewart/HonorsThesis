
import java.io.*;
import java.net.*;

public class EchoServer {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8008);
			while (true){
				Socket s = server.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream()));
				s.close();
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
