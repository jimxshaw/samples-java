import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.*;
import java.io.*;

/*
 * a simple static http server
 */

public class WebServer {

	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
		server.createContext("/test", new MyHandler());
		server.createContext("/ex", new GetHandler());
		server.createContext("/code", new GetCodeHandler());
		server.createContext("/htmlsrc", new GetCodeHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class MyHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange t) throws IOException {
			String response = "This is the response";
			t.sendResponseHeaders(200, response.getBytes().length);
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}

	static class GetHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// add the required response header for a PDF file
			Headers h = t.getResponseHeaders();

			h.add("Content-Type", "text/html");

			// a PDF (you provide your own!)
			File file = new File("./bin/Sample.html");
			byte[] bytearray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(bytearray, 0, bytearray.length);

			// ok, we are ready to send the response.
			t.sendResponseHeaders(200, file.length());
			OutputStream os = t.getResponseBody();
			os.write(bytearray, 0, bytearray.length);
			os.close();
		}
	}

	static class GetCodeHandler implements HttpHandler {

		public void handle(HttpExchange t) throws IOException {

			// add the required response header for a PDF file
			Headers h = t.getResponseHeaders();

			// h.add("Content-Type", "text/html");
			// a PDF (you provide your own!)
			File file = new File("./src/WebServer1.java");
			byte[] bytearray = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(bytearray, 0, bytearray.length);

			// ok, we are ready to send the response.
			t.sendResponseHeaders(200, file.length());
			OutputStream os = t.getResponseBody();
			os.write(bytearray, 0, bytearray.length);
			os.close();
		}
	}
}
