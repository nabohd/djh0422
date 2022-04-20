package com.djh.models;

/**
 * Tools would be pulled from the database
 */

public class Tool {
    private String code;
    private String brand;
    private ToolType toolType;

    public Tool(String code, String brand, ToolType toolType){
        this.code = code;
        this.toolType = toolType;
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public ToolType getToolType() {
        return toolType;
    }

    public String getBrand() {
        return brand;
    }

}
