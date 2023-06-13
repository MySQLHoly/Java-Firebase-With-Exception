package com.sandro.postmanagement.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sandro.postmanagement.Exception.EmptyCollectionExpcetion;
import com.sandro.postmanagement.Exception.IdNotFoundException;
import com.sandro.postmanagement.Exception.StringContainsNumberException;
import com.sandro.postmanagement.dto.UserDTO;
import com.sandro.postmanagement.firebase.FirebaseInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private FirebaseInitializer firebaseInitializer;

    public void add(UserDTO userDTO) {
        Map<String, Object> docData = getDocData(userDTO);

        for (char c : userDTO.getName().toCharArray()) {
            if (Character.isDigit(c)) {
                throw new StringContainsNumberException();
            }
        }

        ApiFuture<WriteResult> writeResultApiFuture = getCollection().document().create(docData);
    }

    public List<UserDTO> getAll() throws InterruptedException, ExecutionException{
        List<UserDTO> users = new ArrayList<>();
        UserDTO user;

        ApiFuture<QuerySnapshot> querySnapshotApiFuture = getCollection().get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();

        if (querySnapshot.isEmpty()) {
            throw new EmptyCollectionExpcetion();
        } else {
            for (DocumentSnapshot doc : querySnapshotApiFuture.get().getDocuments()) {
                user = doc.toObject(UserDTO.class);
                user.setId(doc.getId());
                users.add(user);
            }
            return users;
        }
    }

    public Boolean edit(String id, UserDTO userDTO) throws InterruptedException, ExecutionException{
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = docRef.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            Map<String, Object> docData = getDocData(userDTO);
            ApiFuture<WriteResult> writeResultApiFuture = docRef.update(docData);
            return Boolean.TRUE;
        } else {
            throw new IdNotFoundException();
        }
    }

    public void delete(String id) throws InterruptedException, ExecutionException{
        DocumentReference docRef = getCollection().document(id);
        ApiFuture<DocumentSnapshot> documentSnapshotApiFuture = docRef.get();
        DocumentSnapshot documentSnapshot = documentSnapshotApiFuture.get();

        if (documentSnapshot.exists()) {
            ApiFuture<WriteResult> writeResultApiFuture = docRef.delete();
        } else {
            throw new IdNotFoundException();
        }
    }

    private static Map<String, Object> getDocData(UserDTO userD) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("name", userD.getName());
        docData.put("lastname", userD.getLastname());
        docData.put("email", userD.getEmail());
        return docData;
    }

    private CollectionReference getCollection() {
        return firebaseInitializer.getFirestore().collection("cliente");
    }

}
