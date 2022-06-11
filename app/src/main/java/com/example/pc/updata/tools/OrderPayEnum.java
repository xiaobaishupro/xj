package com.example.pc.updata.tools;


public enum OrderPayEnum {

    ORDER_TYPE_SHOPPING(0,"购物"),
    ORDER_TYPE_EAT(1,"吃饭"),
    ORDER_TYPE_SLEEP(2,"睡觉"),
    ORDER_TYPE_GO(3,"出行"),
    ORDER_TYPE_OTHER(4,"其他");


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private Integer type;
    private String message;
    OrderPayEnum(Integer type,String message){
        this.type = type;
        this.message = message;
    }
}
