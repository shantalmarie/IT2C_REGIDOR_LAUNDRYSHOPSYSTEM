package model;

public record User(int userId, String fullName, String username, String passwordHash, String role, String status, String dateCreated) {
}