package persistence;

import business.entities.characters.Char;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;

/**
 * Esta clase implementa lo necesario para poder acceder a los datos guardados en la API sobre nuestros Personajes
 */
public class CharacterApiDAO implements CharacterDAO{

    /**
     * Enlace URL de la API que se nos ha asignado para los personajes
     */
    static final String APIURL = "https://balandrau.salle.url.edu/dpoo/S1-Project_100/characters";

    /**
     * Cliente HTTP que nos permite la conexion con la API
     */
    private final HttpClient client;

    /**
     * Booleano que no permite saber si la API es funcional
     */
    private boolean apiWorks = true;

    public CharacterApiDAO() {
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            apiWorks = false;
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros personajes
     * @return Listado de los personajes guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Char> getCharList() throws FileNotFoundException {
        String adventureString = readFromApi();

        Gson gson = new Gson();

        return gson.fromJson(adventureString, new TypeToken<LinkedList<Char>>(){}.getType());
    }

    /**
     * Este metodo nos permite leer la inforacion recibida de la API
     * @return Un String con la informacion de la API
     */
    private String readFromApi(){
        try{
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(APIURL)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Exceptions are simplified for any classes that need to catch them
            try {
                throw new IOException(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Este método va a permitirnos enviar una lista de personajes para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param characters es el listado de personajes que queremos guardar
     * @throws IOException sirve para comprobar que los inputs sean correctos
     */
    @Override
    public void updateCharList(LinkedList<Char> characters) throws IOException {
        try {
            for (int i = 0; i < characters.size(); i++) {
                String output = toJson(characters.get(i)).toString();
                if(i == 0){
                    output = output.replaceFirst(", ", "");
                }
                HttpRequest request = HttpRequest.newBuilder().uri(new URI(APIURL)).headers("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(output)).build();
                client.send(request, HttpResponse.BodyHandlers.ofString());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            //Exceptions are simplified for any classes that need to catch them
        }
    }

    /**
     * Metodo que nos permite mostrar el personaje en formato apto para le JSON
     * @return el JSONObject de nuestro personaje
     */
    private JSONObject toJson(Char character) {
        JSONObject characterJSON = new JSONObject();
        characterJSON.put("name", character.getName());
        characterJSON.put("player", character.getPlayer());
        characterJSON.put("xp", character.getXp());
        characterJSON.put("body", character.getBody());
        characterJSON.put("mind", character.getMind());
        characterJSON.put("spirit", character.getSpirit());
        characterJSON.put("type", character.getType());
        return characterJSON;
    }

    /**
     * Este método comprobará que el archivo exista y que por ende sea accesible
     * @return un booleano que nos dara la respuesta a nuestra duda
     */
    @Override
    public boolean fileExists() {
        try{
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(APIURL)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiWorks = response != null;
        } catch (Exception e) {
            apiWorks = false;
            //e.printStackTrace();
        }

        return apiWorks;
    }

    /**
     * Helper function that sets up a SSLContext designed to ignore certificates, accepting anything by default
     * NOT TO BE USED IN REAL PRODUCTION ENVIRONMENTS
     *
     * @return An instance of the SSLContext class, which manages SSL verifications, configured to accept even misconfigured certificates
     * @throws NoSuchAlgorithmException aparece al no poder acceder a la base de datos
     * @throws KeyManagementException aparece al no poder acceder a la base de datos
     */
    private SSLContext insecureContext() throws NoSuchAlgorithmException, KeyManagementException {
        // We set up a TrustManager that accepts every certificate by default
        TrustManager[] insecureTrustManager = new TrustManager[]{new X509TrustManager() {
            // By not throwing any exceptions in these methods we're accepting everything
            public void checkClientTrusted(X509Certificate[] xcs, String string) {
            }

            public void checkServerTrusted(X509Certificate[] xcs, String string) {
            }

            // This doesn't affect our use case, so we just return an empty array
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        }};
        // We set up the SSLContext with the over-accepting TrustManager
        SSLContext sc = SSLContext.getInstance("ssl");
        sc.init(null, insecureTrustManager, null);
        return sc;
    }
}
