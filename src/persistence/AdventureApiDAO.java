package persistence;

import business.adventure.Adventure;
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
 * Esta clase implementa lo necesario para poder acceder a los datos guardados en la API sobre nuestras Aventuras
 */
public class AdventureApiDAO implements AdventureDAO{

    /**
     * Enlace URL de la API que se nos ha asignado para las aventuras
     */
    static final String APIURL = "https://balandrau.salle.url.edu/dpoo/S1-Project_100/adventures";

    /**
     * Cliente HTTP que nos permite la conexion con la API
     */
    private final HttpClient client;

    /**
     * Booleano que no permite saber si la API es funcional
     */
    private boolean apiWorks = true;

    public AdventureApiDAO() {
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            apiWorks = false;
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestras aventuras
     * @return Listado de las aventuras guardado en la base de datos seleccionada
     * @throws FileNotFoundException chequea que el archivo exista antes de recoger la información de la base de datos
     */
    @Override
    public LinkedList<Adventure> getAdventureList() throws FileNotFoundException {
        String adventureString = readFromApi();

        Gson gson = new Gson();

        return gson.fromJson(adventureString, new TypeToken<LinkedList<Adventure>>(){}.getType());

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
            try {
                throw new IOException(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Este método va a permitirnos enviar una lista de aventuras para después poder actualizar la base de datos
     * y guardar nueva información.
     * @param adventures es el listado de aventuras que queremos guardar
     */
    @Override
    public void updateAdventureList(LinkedList<Adventure> adventures) throws IOException {
        try {
            for (int i = 0; i < adventures.size(); i++) {

                String output = toJson(adventures.get(i)).toString();
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
     * Este metodo es util para poder guardar la informacion de la aventura en un formato apto para escribir en un
     * JSON
     * @return la aventura en formato JSON
     */
    private JSONObject toJson(Adventure adventure) {
        JSONObject adventurerJSON = new JSONObject();
        adventurerJSON.put("name", adventure.getName());
        adventurerJSON.put("numCombat", adventure.getNumCombat());
        adventurerJSON.put("party", adventure.getParty());
        adventurerJSON.put("combats", adventure.getCombats());
        return adventurerJSON;
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
     Helper function that sets up a SSLContext designed to ignore certificates, accepting anything by default
     * NOT TO BE USED IN REAL PRODUCTION ENVIRONMENTS
     *
     * @return An instance of the SSLContext class, which manages SSL verifications, configured to accept even misconfigured certificates
     * @throws NoSuchAlgorithmException por si falla a la hora de leer la información
     * @throws KeyManagementException por si falla a la hora de leer la información
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
