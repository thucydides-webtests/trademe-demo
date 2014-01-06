package net.thucydides.demo.trademe.model;

public enum User {
    JOE_BLOGS("wakaleo1","SmallLion","training@wakaleo.com","Joe","Blogs");

    public final String login;
    public final String password;
    public final String email;
    public final String firstName;
    public final String lastName;

    private User(String login, String password, String email, String firstName, String lastName) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User " + firstName + " " + lastName;
    }
}
