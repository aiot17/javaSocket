package abc.com;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.GregorianCalendar;

class SocketServer extends Thread {
	private Socket socket;

	public SocketServer(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		String output = "";
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			String line = null;
			line = bfr.readLine();
			System.out.println(line);
			output = "server send====================";
			pw.println(output);
			pw.flush();
			bfr.close();
			pw.close();
			socket.close();
		} catch (IOException ioex) {
			ioex.printStackTrace();
		}
	}
}

/**
 * Servlet implementation class MySocketServlet
 */
public class MySocketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MySocketServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("=================hello world=================");
		new Thread() {
			public void run() {
				try {
					ServerSocket server = new ServerSocket(30000);
					Socket socket = null;
					while (true) {
						socket = server.accept();
						SocketServer ss = new SocketServer(socket);
						ss.start();
					}
				} catch (IOException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}.start();
		System.out.println("_______________________hello world___________________________");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();

		response.setIntHeader("Refresh", 1);
//		response.setContentType("text/html");
		response.setHeader("content-type", "text/html;charset=utf-8");

		Calendar calendar = new GregorianCalendar();
		String am_pm;
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		if (calendar.get(Calendar.AM_PM) == 0)
			am_pm = "AM";
		else
			am_pm = "PM";

		String CT = hour + ":" + minute + ":" + second + " " + am_pm;

//		PrintWriter out = response.getWriter();
		String title = "使用 Servlet 自動重新整理頁面";
		String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
		out.println(docType + "<html>\n"
				+ "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/> <title>" + title
				+ "</title></head>\n" + "<body bgcolor=\"#f0f0f0\">\n" + "<h1 align=\"center\">" + title + "</h1>\n"
				+ "<p>當前時間是：" + CT + "</p>\n");

//		out.print("hello");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
