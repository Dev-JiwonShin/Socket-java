import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class ClientThread extends Thread
{
    Socket socket;
    int id;
    
    ClientThread (Socket socket, int id)
    {
        this.socket = socket;
        this.id = id;
    }
    
    @Override
    public void run ()
    {
        try
        {
            while (true)
            {
                InputStream IS = socket.getInputStream();
                byte[] bt = new byte[256];
                int size = IS.read(bt);
                
                String output = new String(bt, 0, size, "UTF-8");
                System.out.println("Thread " + id + " >  " + output);
            }
        } catch (IOException e)
        {
            System.out.println("    Thread " + id + " is closed. ");
        }
    }
}

class ConnectThread extends Thread
{
    ServerSocket serverSocket;
    int count = 1;
    
    ConnectThread (ServerSocket serverSocket)
    {
        System.out.println(Server.getTime() + " Server opened");
        this.serverSocket = serverSocket;
    }
    
    @Override
    public void run ()
    {
        try
        {
            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("    Thread " + count + " is started.");
                ClientThread clientThread = new ClientThread(socket, count);
                clientThread.start();
                count++;
            }
        } catch (IOException e)
        {
            System.out.println("    SERVER CLOSE    ");
        }
    }
}

public class Server
{
    public static void main (String[] args)
    {
        Scanner input = new Scanner(System.in);
        ServerSocket serverSocket = null;
        try
        {   // 서버소켓을 생성, 7777 포트와 binding
            serverSocket = new ServerSocket(7777); // 생성자 내부에 bind()가 있고, bind() 내부에 listen() 있음
            ConnectThread connectThread = new ConnectThread(serverSocket);
            connectThread.start();
            
            int temp = input.nextInt(); // 스레드 생성 전에 숫자를 입력하면 바로 SERVER CLOSE!
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            serverSocket.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    static String getTime ()
    {
        SimpleDateFormat f = new SimpleDateFormat("[hh시 mm분 ss초]");
        return f.format(new Date());
    }
}