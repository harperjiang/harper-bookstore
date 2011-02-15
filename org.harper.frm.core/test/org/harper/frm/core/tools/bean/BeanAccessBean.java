package org.harper.frm.core.tools.bean;

import java.math.BigDecimal;

public class BeanAccessBean {

    private String strVal;

    private int intVal;

    private boolean booleanVal;

    private long longVal;

    private BigDecimal bigDecimal;

    public String getStrVal() {
        return strVal;
    }

    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public boolean isBooleanVal() {
        return booleanVal;
    }

    public void setBooleanVal(boolean booleanVal) {
        this.booleanVal = booleanVal;
    }

    public long getLongVal() {
        return longVal;
    }

    public void setLongVal(long longVal) {
        this.longVal = longVal;
    }

    public BigDecimal getBigDecimal() {
        return bigDecimal;
    }

    public void setBigDecimal(BigDecimal bigDecimal) {
        this.bigDecimal = bigDecimal;
    }

}
