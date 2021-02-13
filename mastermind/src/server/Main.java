package server;

public class Main
{
    public static void main(String[] args)
    {
    	//create new server and start it up
        Server server = new Server("0.0.0.0", 4999);
        server.run();
    }
}
