package com.oppenheimer;

import com.fasterxml.jackson.annotation.JsonAlias;

public class TaxRelief {
    String  natid;
    String   name;
    String   relief;

    public String getNatid() {
        return natid;
    }

    public void setNatid(String natid) {
        this.natid = natid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelief() {
        return relief;
    }

    public void setRelief(String relief) {
        this.relief = relief;
    }
}
