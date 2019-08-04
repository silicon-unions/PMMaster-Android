package com.codesignet.pmp.practices.data_access_layer.pojos;

import java.io.Serializable;

public class PracticesOption implements Serializable {

    private String knowladgerAreaa;
    private String processGroup;

    public String getKnowladgerAreaa() {
        return knowladgerAreaa;
    }

    public void setKnowladgerAreaa(String knowladgerAreaa) {
        this.knowladgerAreaa = knowladgerAreaa;
    }

    public String getProcessGroup() {
        return processGroup;
    }

    public void setProcessGroup(String processGroup) {
        this.processGroup = processGroup;
    }
}
