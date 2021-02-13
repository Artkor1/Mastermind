package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;

//server is another thread
public class Server implements Runnable
{
    //stores sockets and client connected to the server in hashmap
    private HashMap<SocketChannel, ClientServer> activeConnections = new HashMap<>();
    public String hostname;
    public int port;
    public final static long TIMEOUT = 10000;

    private ServerSocketChannel serverChannel;
    Selector selector;

    
    public Server(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
        initialize();
    }

    //start the server
    private void initialize()
    {
        System.out.println("Initializing server");
        if (selector != null) return;
        if (serverChannel != null) return;

        try
        {
            serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().bind(new InetSocketAddress(hostname, port));

            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        System.out.println("Now accepting connections...");
        try
        {
            while (!Thread.currentThread().isInterrupted())
            {
                selector.select(TIMEOUT);
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext())
                {
                    SelectionKey k = keys.next();
                    keys.remove();

                    if (!k.isValid())
                    {
                        continue;
                    }

                    if (k.isAcceptable())
                    {
                        System.out.println("Accepting connection");
                        accept(k);
                    }

                    if (k.isWritable())
                    {
                        System.out.println("Writing...");
                        write(k);
                    }

                    if (k.isReadable())
                    {
                        System.out.println("Reading connection");
                        read(k);
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeConnection();
        }
    }

    private void write(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        System.out.println("Sending to: " + activeConnections.get(channel).name);
        activeConnections.get(channel).packetQueue.forEach(d ->
        {
            try
            {
                channel.write(ByteBuffer.wrap(d.getBytes()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });

        activeConnections.get(channel).packetQueue.clear();
        key.interestOps(SelectionKey.OP_READ);
    }

    //end connection cleanly
    private void closeConnection()
    {
        System.out.println("Closing server down");
        if (selector != null)
        {
            try
            {
                selector.close();
                serverChannel.socket().close();
                serverChannel.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void accept(SelectionKey key) throws IOException
    {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);

        ClientServer client = new ClientServer(socketChannel, "Client");
        activeConnections.put(socketChannel, client);

        System.out.println("New client: " + socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException
    {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        buffer.clear();

        ClientServer c = activeConnections.get(channel);

        int read;

        try
        {
            while ((read = channel.read(buffer)) > 0)
            {
                buffer.flip();
                c.receiveBuffer.append(Charset.defaultCharset().decode(buffer));
            }
        }
        catch (IOException e)
        {
            System.out.println("Reading problem, closing connection");
            key.cancel();
            channel.close();
            return;
        }

        if (read == -1)
        {
            System.out.println("Nothing to read, closing connection");
            channel.close();
            key.cancel();
            return;
        }

        while (c.receiveBuffer.length() > 0 && c.receiveBuffer.indexOf("\n") > 0)
        {
            String packet = c.receiveBuffer.substring(0, c.receiveBuffer.indexOf("\n"));
            c.receiveBuffer.delete(0, c.receiveBuffer.indexOf("\n") + 1);

            c.parsePacket(packet);
        }

        key.interestOps(SelectionKey.OP_WRITE);
    }
}

