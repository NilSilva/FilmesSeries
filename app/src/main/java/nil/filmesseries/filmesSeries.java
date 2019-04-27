package nil.filmesseries;

import java.util.Date;

public class filmesSeries {

    private int ID;
    private String formato;
    private String nome;
    private int nEpisodios;
    private int nEpiVistos;
    private Date data;
    private String status;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getnEpisodios() {
        return nEpisodios;
    }

    public void setnEpisodios(int nEpisodios) {
        this.nEpisodios = nEpisodios;
    }

    public int getnEpiVistos() {
        return nEpiVistos;
    }

    public void setnEpiVistos(int nEpiVistos) {
        this.nEpiVistos = nEpiVistos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
