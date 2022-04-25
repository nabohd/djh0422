package com.djh.models;

/**
 *
 * This model holds data pulled from the CSV configuration files "tools.csv" and "toolType.csv".
 */

public class Tool {
    private final String code;
    private final String brand;
    private final ToolType toolType;

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
