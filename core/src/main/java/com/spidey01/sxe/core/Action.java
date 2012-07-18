package com.spidey01.sxe.core;

/** Class representing an "Action" to be executed upon an event.
 *
 * This can be used for example, to execute code when a key is pressed.
 */
public interface Action {

    void execute();

    /** Get the name of this action. */
    String getName();

}


