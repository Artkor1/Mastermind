package server;

import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;

//class for clients connected to the server
public class ClientServer
{
    private final SocketChannel socketChannel;
    public final String name;
    public StringBuilder receiveBuffer = new StringBuilder();
    ArrayDeque<String> packetQueue = new ArrayDeque<>();

    
    public ClientServer(SocketChannel socketChannel, String name)
    {
        this.socketChannel = socketChannel;
        this.name = name;
    }

    public void send(String packet)
    {
        packetQueue.add(packet + "\n");
    }
    
    //check if the packet reached
    public void parsePacket(String packet)
    {
        System.out.println(packet);
        send("Testing packet reached server");
    }
}

