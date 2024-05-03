import java.net.*;

public class MorpionServeur {
    private Morpion jeu;
    private DatagramSocket socketServeur;
    private static final int PORT = 5000;

    public MorpionServeur() {
        jeu = new Morpion(); // Initialise le jeu de morpion
    }

    public void demarrer() {
        try {
            socketServeur = new DatagramSocket(PORT); // Crée la socket du serveur sur le port 5000
            System.out.println("Serveur du jeu de morpion démarré...");

            while (true) {
                byte[] bufferReception = new byte[1000];
                DatagramPacket paquetReception = new DatagramPacket(bufferReception, bufferReception.length);
                socketServeur.receive(paquetReception); // Attend la réception d'un paquet

                String message = new String(paquetReception.getData(), 0, paquetReception.getLength());
                System.out.println("Message reçu du client : " + message);

                // Traiter le message reçu
                // Dans cet exemple, nous n'effectuons aucune validation, nous supposons que le message est un coup valide
                String[] coordonnees = message.split(",");
                int ligne = Integer.parseInt(coordonnees[0]);
                int colonne = Integer.parseInt(coordonnees[1]);
                jeu.jouerCoup(ligne, colonne); // Jouer le coup sur la grille de morpion

                // Envoyer l'état actuel de la grille au client
                String etatGrille = arrayToString(jeu.getGrille());
                byte[] etatGrilleBytes = etatGrille.getBytes();
                InetAddress adresseClient = paquetReception.getAddress();
                int portClient = paquetReception.getPort();
                DatagramPacket paquetEnvoye = new DatagramPacket(etatGrilleBytes, etatGrilleBytes.length, adresseClient, portClient);
                socketServeur.send(paquetEnvoye);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socketServeur != null) {
                socketServeur.close(); // Ferme la socket du serveur lorsque le serveur s'arrête
            }
        }
    }

    // Méthode utilitaire pour convertir un tableau 2D en chaîne de caractères
    private String arrayToString(char[][] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                sb.append(array[i][j]);
            }
            sb.append("\n"); // Nouvelle ligne pour chaque ligne de la grille
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        MorpionServeur serveur = new MorpionServeur();
        serveur.demarrer();
    }
}
