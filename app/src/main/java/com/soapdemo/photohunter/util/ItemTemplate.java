package com.soapdemo.photohunter.util;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.library.baseAdapters.BR;

import com.orhanobut.logger.Logger;


public class ItemTemplate {
    private int variableId;
    @LayoutRes
    private int templateId;

    @NonNull
    public static ItemTemplate of( @LayoutRes int templateId , String variableName){
        int variableId = BR.datacontext;
        if( variableName != null ) {
            try {
                variableId = BR.class.getField(variableName).getInt(BR.class);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                Logger.e(e, "Error variableName parse");
            }
        }
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
