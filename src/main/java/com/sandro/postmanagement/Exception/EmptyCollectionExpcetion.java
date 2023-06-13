package com.sandro.postmanagement.Exception;

public class EmptyCollectionExpcetion extends RuntimeException{
    public EmptyCollectionExpcetion() {

    }

    public EmptyCollectionExpcetion(String message) {
        super(message);
    }
}
