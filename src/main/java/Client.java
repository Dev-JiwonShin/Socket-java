import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client
{
    public static void main (String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        String serverIp = "127.0.0.1";
        System.out.println("Connecting to server. Server IP :" + serverIp);
        
        try
        {
            // 소켓을 생성하여 연결을 요청한다.
            Socket socket = new Socket(serverIp, 7777);
            
            while (true)
            {
                System.out.println("> ");
                String msg = input.nextLine();
                byte[] arrayStream = msg.getBytes(StandardCharsets.UTF_8);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(arrayStream);
            }
        } catch (ConnectException ce)
        {
            ce.printStackTrace();
        } catch (IOException ie)
        {
            ie.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}