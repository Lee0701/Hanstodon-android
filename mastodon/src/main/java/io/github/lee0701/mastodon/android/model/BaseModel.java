package io.github.lee0701.mastodon.android.model;

import io.github.lee0701.mastodon.android.api.AllFieldsAreRequired;
import io.github.lee0701.mastodon.android.api.ObjectValidationException;
import io.github.lee0701.mastodon.android.api.RequiredField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import androidx.annotation.CallSuper;

public abstract class BaseModel{
	@CallSuper
	public void postprocess() throws ObjectValidationException{
		try{
			boolean allRequired=getClass().isAnnotationPresent(AllFieldsAreRequired.class);
			for(Field fld:getClass().getFields()){
				if(!fld.getType().isPrimitive() && !Modifier.isTransient(fld.getModifiers()) && (allRequired || fld.isAnnotationPresent(RequiredField.class))){
					if(fld.get(this)==null){
						throw new ObjectValidationException("Required field '"+fld.getName()+"' of type "+fld.getType().getSimpleName()+" was null in "+getClass().getSimpleName());
					}
				}
			}
		}catch(IllegalAccessException ignore){}
	}
}
