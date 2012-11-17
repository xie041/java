package com.javapatterns.decorator;

public class Decorator implements Component
{
	public Decorator(Component component)
    {
//        super();
        this.component = component;
    }

    public Decorator() {
    }

    public void sampleOperation()
    {
    	 System.out.println();
    	 ....
        component.sampleOperation();
        System.out.println();
        .....
    }

    /**
     * @link aggregation 
     */
    private Component component;
}
