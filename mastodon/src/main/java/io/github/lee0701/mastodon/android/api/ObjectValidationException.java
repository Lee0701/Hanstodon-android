package io.github.lee0701.mastodon.android.api;

import java.io.IOException;

public class ObjectValidationException extends IOException{
	public ObjectValidationException(){
	}

	public ObjectValidationException(String message){
		super(message);
	}

	public ObjectValidationException(String message, Throwable cause){
		super(message, cause);
	}

	public ObjectValidationException(Throwable cause){
		super(cause);
	}
}
