import java.net.*;

public class MorpionClient {
    private DatagramSocket ds;
    private String server_address;
    private int server_port;

    public MorpionClient(String server_address, int server_port){
        this.server_address = server_address;
        this.server_port = server_port;
        try {
            ds = new DatagramSocket(); // Utilisez un port aléatoire pour le client
        } catch (SocketException e) {
            System.out.println(e);
        }
    }

    public void start() {
        try {
            // Envoi d'un message au serveur
            System.out.println("Envoi d'un message au serveur " + server_address + ":" + server_port);
            String message = "Bonjour!";
            byte[] bufferEnvoi = message.getBytes();
            InetAddress adresseServeur = InetAddress.getByName(server_address);
            DatagramPacket paquetEnvoi = new DatagramPacket(bufferEnvoi, bufferEnvoi.length, adresseServeur, server_port);
            ds.send(paquetEnvoi);
            System.out.println("Message envoyé.");

            // Réception de la réponse du serveur
            byte[] bufferReception = new byte[1000];
            DatagramPacket paquetReception = new DatagramPacket(bufferReception, bufferReception.length);
            ds.receive(paquetReception);
            String reponse = new String(paquetReception.getData(), 0, paquetReception.getLength());
            System.out.println("Réponse du serveur : " + reponse);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            // Fermeture de la socket
            if (ds != null) {
                ds.close();
            }
        }
    }

    public static void main(String args[]) {
        if (args.length < 2) {
            System.out.println("Nombre d'arguments insuffisant");
            System.out.println("Syntaxe : java MorpionClient server_address server_port");
            System.exit(-1);
        }
        String server_address = args[0];
        int server_port = Integer.parseInt(args[1]);
        MorpionClient client = new MorpionClient(server_address, server_port);
        client.start();
    }
}
