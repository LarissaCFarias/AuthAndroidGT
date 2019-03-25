package br.com.udemycouse.gtauth;

import com.google.firebase.database.Exclude;

public class Login {

    private String id;
    private String nome, apelido, dtNasc, eMail, senha;



    public Login (String nome, String apelido, String dtNasc, String eMail, String senha){

        this.nome = nome;
        this.apelido = apelido;
        this.dtNasc = dtNasc;
        this.eMail = eMail;
        this.senha = senha;


    }

    public Login (){

    }

    public Login (String id, String nome, String apelido, String dtNasc, String eMail, String senha){
        this (nome, apelido, dtNasc, eMail, senha);
        setId(id);
    }

    @Exclude

    public String getId(){ return id;}

    public void setId(String id) {this.id = id;}

    public String getNome() { return nome;}

    public void setNome(String nome) {this.nome = nome;}

    public String getApelido() {return apelido;}

    public void setApelido(String apelido) {this.apelido = apelido;}

    public String getDtNasc() {return dtNasc;}

    public void setDtNasc(String dtNasc) {this.dtNasc = dtNasc;}

    public String geteMail() {return eMail;}

    public void seteMail(String eMail) {this.eMail = eMail;}

    public String getSenha() {return senha;}

    public void setSenha(String senha) {this.senha = senha;}

    @Override
    public String toString() {
        return "Login{" +  ", nome='" + nome +
                '\'' + "user_id='" + id + '\'' + ", apelido='" + apelido + '\'' +
                ", dtNasc='" + dtNasc + '\'' + ", email='" + eMail +
                '\'' + ", senha='" + senha + '\'' + '}';
    }
}
