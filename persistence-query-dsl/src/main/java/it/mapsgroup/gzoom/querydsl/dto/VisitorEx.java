package it.mapsgroup.gzoom.querydsl.dto;

public class VisitorEx extends PersonEx {
    private Visitor visitor;
    private  UserLogin userLogin;
    private PartyParentRole partyParentRole;

    public Visitor getVisitor() {
        return visitor;
    }

    public void setVisitor(Visitor visitor) {
        this.visitor = visitor;
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(UserLogin userLogin) {
        this.userLogin = userLogin;
    }
}
