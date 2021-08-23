package br.com.zup.edu.stephanie.propostas.request;

public class SolicitarAnaliseRequest {
    private String idProposta;
    private String documento;
    private String nome;


    public SolicitarAnaliseRequest() {}

    public SolicitarAnaliseRequest(String idProposta, String documento, String nome) {
        this.idProposta = idProposta;
        this.documento = documento;
        this.nome = nome;

    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
