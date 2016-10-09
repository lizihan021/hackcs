package com.li.zihan;

import java.io.BufferedReader;  
import java.io.BufferedWriter;  
import java.io.InputStreamReader;  
import java.io.OutputStreamWriter;  
import java.io.PrintWriter;  
import java.net.ServerSocket;  
import java.net.Socket;

public class Serverr implements Runnable {
	
	public void run()  
    {
		try  
        {  
            //创建ServerSocket  
            ServerSocket serverSocket = new ServerSocket(54321);  
            while (true)  
            {  
                //接受客户端请求  
                Socket clientA = serverSocket.accept();
                Socket clientB = serverSocket.accept();
                System.out.println("accept");
                try  
                {  
                    //接收客户端消息  
                    BufferedReader inA = new BufferedReader(new InputStreamReader(clientA.getInputStream()));
                    BufferedReader inB = new BufferedReader(new InputStreamReader(clientB.getInputStream()));
                    String longtitudeA = inA.readLine();
                    String latitudeA = inA.readLine();
                    String hasShootedA = inA.readLine(); 
                    longtitudeA = "123";
                    latitudeA = "34";
                    hasShootedA = "Shooted";
                    PrintWriter outB = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientA.getOutputStream())),true);        
                    outB.write(longtitudeA);
                    outB.write(latitudeA);
                    outB.write(hasShootedA); 
                    outB.close();  
                    inA.close();
                    
                    String longtitudeB = inB.readLine();
                    String latitudeB = inB.readLine();
                    String hasShootedB = inB.readLine();
                    longtitudeB = "145";
                    latitudeB = "86";
                    hasShootedB = "UnShooted";
                    PrintWriter outA = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientB.getOutputStream())), true);
                    outA.write(longtitudeB);
                    outA.write(latitudeB);
                    outA.write(hasShootedB);
                    outA.close();
                    inB.close();
                }  
                catch (Exception e)
                {  
                    System.out.println(e.getMessage());  
                    e.printStackTrace();  
                }  
                finally
                {  
                    //关闭  
                    clientA.close();
                    clientB.close();
                    System.out.println("close");  
                }
            }  
        }  
        catch (Exception e)  
        {  
            System.out.println(e.getMessage());  
        }
    }

    //main函数，开启服务器  
    public static void main(String a[])  
    {  
        Thread desktopServerThread = new Thread(new Serverr());  
        desktopServerThread.start();
    }
}
