package client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

//client is another thread
public class Client implements Runnable
{
    private final String hostname;
    private final int port;
    SocketChannel channel;
    private boolean running;

    public Client(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
        this.running = true;
    }

    @Override
    public void run()
    {
        while (running)
        {
            try
            {
                channel = SocketChannel.open(); 
                channel.connect(new InetSocketAddress(hostname, port)); //connect with host

                StringBuilder m = new StringBuilder();

                while (true)
                {
                    ByteBuffer bufferA = ByteBuffer.allocate(65536); //size 2^16

                    while (channel.read(bufferA) > 0)
                    {
                        bufferA.flip();
                        m.append(Charset.defaultCharset().decode(bufferA));
                    }

                    while (m.length() > 0 && m.indexOf("\n") > 0)
                    {
                        String packet = m.substring(0, m.indexOf("\n"));
                        m.delete(0, m.indexOf("\n") + 1);
                        System.out.println(packet);

                    }
                }
            }
            catch (ConnectException e)
            {
                if (!running) return; //if not running, which means no connection
                //wait for another second and try again, if that doesen't work either close connection
                System.out.println("Can't connect: " + e.getLocalizedMessage() + " another try in 1 second");
                try
                {
                    Thread.sleep(1000);	//wait 1 second
                }
                catch (InterruptedException interruptedException)
                {
                    interruptedException.printStackTrace();
                }
            }
            catch (IOException e)
            {
                if (!running) return; //if not running, which means no connection
                System.out.println("Disconnected. " + e.getLocalizedMessage() + " another try in 1 second");
                try
                {
                    Thread.sleep(1000); //wait 1 second
                }
                catch (InterruptedException interruptedException)
                {
                    interruptedException.printStackTrace();
                }
            }
        }
    }

    //send a packet to server and return true if it reached the server and false otherwise
    public boolean send(String packet)
    {
        if (!channel.isConnected()) return false; //no connection
        try
        {
            channel.write(ByteBuffer.wrap(packet.getBytes()));
            return true;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    //end connection cleanly
    public void disconnect()
    {
        try
        {
            this.running = false;
            channel.close();
        }
        catch (IOException e)
        {
           e.printStackTrace();
        }
    }
}

