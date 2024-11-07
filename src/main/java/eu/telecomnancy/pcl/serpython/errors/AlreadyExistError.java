package eu.telecomnancy.pcl.serpython.errors;

public class AlreadyExistError extends Exception {
    public AlreadyExistError() {

    }

    public AlreadyExistError(String message) {
         super(message);
    }
}
