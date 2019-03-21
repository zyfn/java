import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    public static class serverTest implements Runnable  {
        @Override
         public void run(){
            try {
                ServerSocket serverSocket = null;
                serverSocket = new ServerSocket(8888);
                Socket socket;
                PrintStream ps;
                BufferedReader br;
                String s1;
                File f;
                FileInputStream fr;
                System.out.println("等待客户端连接");
                while (true) {
                    socket = serverSocket.accept();
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ps = new PrintStream(socket.getOutputStream());
                    String[] s = br.readLine().split(" ");
                    System.out.println("server收到：" + s[1]);
                    if(s[1].equals("/rest")) {
                        s1 = new rest().create();
                    }
                    else if(s[1].equals("/test")){
                        s1 = new test().create();
                    }
                    else
                        s1 = new any().create();
                    f = new File(s1);
                    fr = new FileInputStream(f);
                    byte[] body = new byte[(int) f.length()];
                    fr.read(body);
                    ps.println(new String(body));
                    ps.close();
                    br.close();
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args)  {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new serverTest());

    }
}