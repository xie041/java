package org.jboss.resteasy.examples;

import org.jboss.resteasy.examples.hello.GreeterImpl;
import org.jboss.resteasy.examples.hello.HelloResource;
import org.jboss.resteasy.examples.hello.IGreeter;
import org.jboss.resteasy.examples.library.Library;

import com.google.inject.Binder;
import com.google.inject.Module;


public class MainModule implements Module {

	public void configure(final Binder binder) {
		//加载模块
		binder.bind(HelloResource.class);
		binder.bind(Library.class);
		binder.bind(IGreeter.class).to(GreeterImpl.class);
	}

}