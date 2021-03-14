package com.soapdemo.photohunter.util;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

public class ItemTemplate {
    private int variableId;
    @LayoutRes
    private int templateId;

    @NonNull
    public static ItemTemplate of(int variableId, @LayoutRes int templateId) {
        return new ItemTemplate(variableId, templateId);
    }

    public ItemTemplate( int variableId , @LayoutRes int templateId){
        this.variableId = variableId;
        this.templateId = templateId;
    }

    public int getTemplateId() {
        return templateId;
    }

    public int getVariableId() {
        return variableId;
    }
}
