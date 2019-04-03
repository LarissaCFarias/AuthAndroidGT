package br.com.udemycouse.gtauth.Models;

public class User {
    private String id;
    private String username;
    private String nome;
    private  String imageUrl;
    private String dtNasc;

    public User(String id, String username, String nome, String imageUrl, String dtNasc) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.imageUrl = imageUrl;
        this.dtNasc = dtNasc;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }
}
