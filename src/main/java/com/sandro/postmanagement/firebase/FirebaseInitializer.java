package com.sandro.postmanagement.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    @PostConstruct
    private void iniFirestore() throws IOException {
        //Dentro de los parentesis ira el nombre de la clave que genero firebase, puedes cambiar el nombre
        //Se usa el getClass para poder colocar la ruta relativa de la ubicacion de la clave
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("private-key-firestore.json");

        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://post-management-53713.firebaseio.com/").build();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options);
        }
    }

    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }
}
