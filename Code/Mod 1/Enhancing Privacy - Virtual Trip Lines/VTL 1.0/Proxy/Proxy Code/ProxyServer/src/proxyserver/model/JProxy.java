package proxyserver.model;

import java.io.*;
import java.net.*;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.namespace.QName;


public class JProxy extends Thread{
	public static final int DEFAULT_PORT = 8080;
        URLObserver uo;
        private String server_ip="";
        private int server_port;
	private ServerSocket server = null;
	private int thisPort = DEFAULT_PORT;
	private String fwdServer = "";
	private int fwdPort = 0;
	private int ptTimeout = ProxyThread.DEFAULT_TIMEOUT;
	private int debugLevel = 0;
	private PrintStream debugOut = System.out;

	public static void main (String args[])	{
		int port = 0;
		String fwdProxyServer = "";
		int fwdProxyPort = 0;

		if (args.length == 0){
			System.err.println("USAGE: java jProxy <port number> [<fwd proxy> <fwd port>]");
			System.err.println("  <port number>   the port this service listens on");
			System.err.println("  <fwd proxy>     optional proxy server to forward requests to");
			System.err.println("  <fwd port>      the port that the optional proxy server is on");
			System.err.println("\nHINT: if you don't want to see all the debug information flying by,");
			System.err.println("you can pipe the output to a file or to 'nul' using \">\". For example:");
			System.err.println("  to send output to the file prox.txt: java jProxy 8080 > prox.txt");
			System.err.println("  to make the output go away: java jProxy 8080 > nul");
			return;
		}

		port = Integer.parseInt(args[0]);
		if (args.length > 2){
			fwdProxyServer = args[1];
			fwdProxyPort = Integer.parseInt(args[2]);
		}

		System.err.println("  **  Starting jProxy on port " + port + ". Press CTRL-C to end.  **\n");
		JProxy jp = new JProxy(port, fwdProxyServer, fwdProxyPort, 20);
		jp.setDebug(1, System.out);
		jp.start();

		while (true){
			try { Thread.sleep(3000); } catch (Exception e) {}
		}

	}

	public JProxy (int port){
		thisPort = port;
	}

	public JProxy (int port, String proxyServer, int proxyPort){
		thisPort = port;
		fwdServer = proxyServer;
		fwdPort = proxyPort;
	}

        public JProxy (int port, String proxyServer, int proxyPort, int timeout){
		thisPort = port;
		fwdServer = proxyServer;
		fwdPort = proxyPort;
		ptTimeout = timeout;
	}

	public JProxy (int port, String proxyServer, int proxyPort, int timeout, URLObserver uo, String server_ip, int server_port){
		thisPort = port;
		fwdServer = proxyServer;
		fwdPort = proxyPort;
		ptTimeout = timeout;
                this.uo=uo;
                this.server_ip=server_ip;
                this.server_port=server_port;
	}

	public void setDebug (int level, PrintStream out){
		debugLevel = level;
		debugOut = out;
	}

	public int getPort (){
		return thisPort;
	}

        public void starter(){
            start();
        }

	public boolean isRunning (){
		if (server == null)
			return false;
		else
			return true;
	}

	public void closeSocket (){
		try {
			server.close();
		}  catch(Exception e)  {
			if (debugLevel > 0)
				debugOut.println(e);
		}
		server = null;
	}


        @Override
	public void run(){
		try {
			server = new ServerSocket(thisPort);
			if (debugLevel > 0)
				debugOut.println("Started jProxy on port " + thisPort);

			while (true){
				Socket client = server.accept();
				ProxyThread t = new ProxyThread(client, fwdServer, fwdPort,uo,server_ip,server_port);
				t.setDebug(debugLevel, debugOut);
				t.setTimeout(ptTimeout);
				t.start();
			}
		}  catch (Exception e)  {
			if (debugLevel > 0)
				debugOut.println("jProxy Thread error: " + e);
		}

		closeSocket();
	}

}

class ProxyThread extends Thread{
	private Socket pSocket;
	private String fwdServer = "";
	private int fwdPort = 0;
	private int debugLevel = 0;
	private PrintStream debugOut = System.out;
	URLObserver uo;
	public static final int DEFAULT_TIMEOUT = 20 * 1000;
	private int socketTimeout = DEFAULT_TIMEOUT;
        private String server_ip="";
        private int server_port;
        String u1,p1,g1,m1,e1;
        String pp1 = null,pp2 = null,gg1 = null,gg2 = null,mm1 = null,mm2 = null,ee1 = null,ee2 = null,eee1 = null,eee2 = null;

	public ProxyThread(Socket s){
		pSocket = s;
	}

	public ProxyThread(Socket s, String proxy, int port){
		pSocket = s;
		fwdServer = proxy;
		fwdPort = port;
	}

        public ProxyThread(Socket s, String proxy, int port, URLObserver uo, String server_ip, int server_port){
		pSocket = s;
		fwdServer = proxy;
		fwdPort = port;
                this.uo=uo;
                this.server_ip=server_ip;
                this.server_port=server_port;
	}

	public void setTimeout (int timeout){
		socketTimeout = timeout * 1000;
	}


	public void setDebug (int level, PrintStream out){
		debugLevel = level;
		debugOut = out;
	}


        @Override
	public void run(){
		try
		{
                        File ff=new File("");
			FileInputStream fin=new FileInputStream(ff.getAbsolutePath()+"/system.properties");
			Properties prop=new Properties();
			prop.load(fin);
			String system=prop.getProperty("system").toString();
                        System.out.println("system:..."+system);                        
			System.out.println("*********** PROXY: RECEIVING RESPONSE ***********");
			long startTime = System.currentTimeMillis();

			BufferedInputStream clientIn = new BufferedInputStream(pSocket.getInputStream());
			BufferedOutputStream clientOut = new BufferedOutputStream(pSocket.getOutputStream());

			Socket server = null;
			byte[] request = null;
			byte[] response = null;
			int requestLength = 0;
			int responseLength = 0;
			int pos = -1;
			StringBuffer host = new StringBuffer("");
			String hostName = "";
			int hostPort = 80;

			request = getHTTPData(clientIn, host, false);
			requestLength = Array.getLength(request);
			hostName = host.toString();

			pos = hostName.indexOf(":");
			if (pos > 0){
				try { hostPort = Integer.parseInt(hostName.substring(pos + 1));
					}  catch (Exception e)  { }
				hostName = hostName.substring(0, pos);
			}

			try{
				if ((fwdServer.length() > 0) && (fwdPort > 0)){
					server = new Socket(fwdServer, fwdPort);
				}  else  {
                                        server = new Socket(hostName, hostPort);
				}
			}  catch (Exception e)  {
				String errMsg = "HTTP/1.0 500\nContent Type: text/plain\n\n" +
								"Error connecting to the server:\n" + e + "\n";
				clientOut.write(errMsg.getBytes(), 0, errMsg.length());
			}

			if (server != null){
				BufferedInputStream serverIn = new BufferedInputStream(server.getInputStream());
				BufferedOutputStream serverOut = new BufferedOutputStream(server.getOutputStream());
				serverOut.write(request, 0, requestLength);
				serverOut.flush();
                                response = getHTTPData(serverIn, true);
                                responseLength = Array.getLength(response);
				serverIn.close();
				serverOut.close();
			}

                        String value = new String(response);
                        System.out.println("VALUE"+value);
                        byte[] samplereq=new byte[requestLength];
                        System.arraycopy(request,0,samplereq, 0,requestLength);
                        String sampreq=new String(samplereq);
                        StringTokenizer st=new StringTokenizer(sampreq,"\n");
                        String firstLine=st.nextToken();

                        if(firstLine.contains("userregistration.do")){
                        System.out.println("Accessing URL: "+firstLine);
                        int b=0,b1=0;
                                byte[] sampleres=new byte[responseLength];
                                System.arraycopy(response,0,sampleres, 0,responseLength);
                                String sampres=new String(sampleres);
                                StringTokenizer tokener=new StringTokenizer(sampres,"\n");
                                String indexer=tokener.nextToken();
                                HashMap<String,String> header=new HashMap<String,String>();
                                while(tokener.hasMoreTokens()){
                                        String temp=tokener.nextToken();
                                        if(temp.equals("\r")){
                                                indexer+=temp+"\n";
                                                break;
                                        }else{
                                                StringTokenizer headerInfo=new StringTokenizer(temp.trim(),":");
                                                header.put(headerInfo.nextToken(),headerInfo.nextToken());
                                                indexer+="\n"+temp;
                                        }
                                }
                                String content1="", content2="";
                                StringTokenizer t123=new StringTokenizer(value,"***");
                                 while(t123.hasMoreTokens()){
                                     content1=t123.nextToken();
                                     content2=t123.nextToken();
                                 }
                                 if(value.contains("ALREADY EXISTING")){
                                    String Msg ="HTTP/1.0 500\nContent Type: text/plain\n\n"+content2+"\n";
                                    clientOut.write(Msg.toString().trim().getBytes(), 0, Msg.toString().trim().length());
                                 }
                                 else if(value.contains("REGISTRATION SUCCESSFULL")){
                                    String Msg ="HTTP/1.0 500\nContent Type: text/plain\n\n"+content2+"\n";
                                    clientOut.write(Msg.toString().trim().getBytes(), 0, Msg.toString().trim().length());
                                 }
                        }

			if (debugLevel > 0){
				long endTime = System.currentTimeMillis();
				debugOut.println("Request from " + pSocket.getInetAddress().getHostAddress() +
                                                " on Port " + pSocket.getLocalPort() +
                                                " to host " + hostName + ":" + hostPort +
                                                "\n  (" + requestLength + " bytes sent, " +
                                                responseLength + " bytes returned, " +
                                                Long.toString(endTime - startTime) + " ms elapsed)");
                                Vector v=new Vector();
                                v.add(pSocket.getInetAddress().getHostAddress());
                                v.add(pSocket.getLocalPort());
                                v.add(hostName + ":" + hostPort);
                                uo.setTagNeighbours(v);
				debugOut.flush();
			}
			if (debugLevel > 1){
				debugOut.println("REQUEST:\n" + (new String(request)));
				debugOut.println("RESPONSE:\n" + (new String(response)));
				debugOut.flush();
			}                                
			clientOut.close();
			clientIn.close();
                        pSocket.close();
		}  catch (Exception e)  {
		}

	}


	private byte[] getHTTPData (InputStream in, boolean waitForDisconnect){
		StringBuffer foo = new StringBuffer("");
		return getHTTPData(in, foo, waitForDisconnect);
	}


	private byte[] getHTTPData (InputStream in, StringBuffer host, boolean waitForDisconnect){
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		streamHTTPData(in, bs, host, waitForDisconnect);
		return bs.toByteArray();
	}

	private int streamHTTPData (InputStream in, OutputStream out,StringBuffer host, boolean waitForDisconnect){
		StringBuffer header = new StringBuffer("");
		String data = "";
		int responseCode = 200;
		int contentLength = 0;
		int pos = -1;
		int byteCount = 0;

		try{
			data = readLine(in);
                       
			if (data != null){
				header.append(data + "\r\n");
				pos = data.indexOf(" ");
				if ((data.toLowerCase().startsWith("http")) && 	(pos >= 0) && (data.indexOf(" ", pos+1)>= 0)){
					String rcString = data.substring(pos+1, data.indexOf(" ", pos+1));
					try{
						responseCode = Integer.parseInt(rcString);
					}  catch (Exception e)  {
						if (debugLevel > 0)
							debugOut.println("Error parsing response code " + rcString);
					}
				}
			}

			while ((data = readLine(in)) != null){
				// the header ends at the first blank line
				if (data.length() == 0)
					break;
				header.append(data + "\r\n");

				// check for the Host header
				pos = data.toLowerCase().indexOf("host:");
				if (pos >= 0){
					host.setLength(0);
					host.append(data.substring(pos + 5).trim());
				}

				// check for the Content-Length header
				pos = data.toLowerCase().indexOf("content-length:");
				if (pos >= 0)
					contentLength = Integer.parseInt(data.substring(pos + 15).trim());
			}

			header.append("\r\n");

			out.write(header.toString().getBytes(), 0, header.length());

			if ((responseCode != 200) && (contentLength == 0)){
				out.flush();
				return header.length();
			}

			if (contentLength > 0)
				waitForDisconnect = false;

			if ((contentLength > 0) || (waitForDisconnect))	{
				try {
					byte[] buf = new byte[4096];
					int bytesIn = 0;
					while ( ((byteCount < contentLength) || (waitForDisconnect))
							&& ((bytesIn = in.read(buf)) >= 0) )
					{
						out.write(buf, 0, bytesIn);
						byteCount += bytesIn;
					}
				}  catch (Exception e)  {
					String errMsg = "Error getting HTTP body: " + e;
					if (debugLevel > 0)
                                            debugOut.println(errMsg);
				}
			}
		}  catch (Exception e)  {
			if (debugLevel > 0)
				debugOut.println("Error getting HTTP data: " + e);
		}

		try  {  out.flush();  }  catch (Exception e)  {}
		return (header.length() + byteCount);
	}


	private String readLine (InputStream in){
		StringBuffer data = new StringBuffer("");
		int c;

		try{
			in.mark(1);
			if (in.read() == -1)
				return null;
			else
				in.reset();

			while ((c = in.read()) >= 0){
				if ((c == 0) || (c == 10) || (c == 13))
					break;
				else
					data.append((char)c);
			}

			if (c == 13)
			{
				in.mark(1);
				if (in.read() != 10)
					in.reset();
			}
		}  catch (Exception e)  {
			if (debugLevel > 0)
				debugOut.println("Error getting header: " + e);
		}

		return data.toString();
	}
}
