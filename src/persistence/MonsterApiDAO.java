package persistence;

import business.entities.monster.Monster;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
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
 * Esta clase implementa lo necesario para poder acceder a los datos guardados en la API sobre nuestros Monstruos
 */
public class MonsterApiDAO implements MonsterDAO{

    /**
     * Enlace URL de la API que se nos ha asignado para los monstruos
     */
    static final String APIURL = "https://balandrau.salle.url.edu/dpoo/shared/monsters";

    /**
     * Cliente HTTP que nos permite la conexion con la API
     */
    private final HttpClient client;

    /**
     * Booleano que no permite saber si la API es funcional
     */
    private boolean apiWorks = true;


    public MonsterApiDAO() {
        try {
            client = HttpClient.newBuilder().sslContext(insecureContext()).build();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            apiWorks = false;
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método nos permitirá leer el archivo designado y conseguir un listado de nuestros Monstruos
     * @return Listado de los monstruos guardado en la base de datos seleccionada
     */
    @Override
    public LinkedList<Monster> getMonsterList(){
        String monstersString = readFromApi();

        Gson gson = new Gson();

        return gson.fromJson(monstersString, new TypeToken<LinkedList<Monster>>(){}.getType());
    }

    /**
     * Este metodo nos permite leer la inforacion recibida de la API
     * @return Un String con la informacion de la API
     */
    private String readFromApi(){
        try {
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
            e.printStackTrace();
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
