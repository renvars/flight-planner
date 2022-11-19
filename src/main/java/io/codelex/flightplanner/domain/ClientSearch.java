package io.codelex.flightplanner.domain;

public class ClientSearch {

    private Params params;

    public ClientSearch(Params params) {
        this.params = params;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }
}
