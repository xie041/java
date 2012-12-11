package org.jboss.resteasy.examples.hello;

public class GreeterImpl implements IGreeter
{
   public String greet(final String name)
   {
      return "Hello " + name;
   }
}
