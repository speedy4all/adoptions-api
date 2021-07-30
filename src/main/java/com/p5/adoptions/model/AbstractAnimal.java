package com.p5.adoptions.model;

/**
 * An abstract class, cannot be instantiated anymore
 * <p>
 * Pluses over interface:
 * - we can fields private static, non-static,protected
 * - we can body to functions
 * - we can abstract methods == interface methods
 * - constructors
 *
 * Minuses:
 * - IMPORTANT!!!  Limits inheritance, because we need to extend this in child, AND WE CAN ONLY EXTENDS ON
 */
public abstract class AbstractAnimal
{
    /**
     * An abstract method allows a method without body
     *
     * @return
     */

    protected String customField;

    public abstract String whatDoesItEats();
}
