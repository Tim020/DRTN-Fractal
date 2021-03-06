/**
 * SEPR project inherited from DRTN.
 * Any changes are marked by preceding comments.
 * 
 * Executables availabe at: https://seprated.github.io/Assessment4/Executables.zip
**/
package io.github.teamfractal.util;

/**
 * Created by Tim on 20/03/2017.
 */
public class Tuple<T> {

    private T head, tail;

    public Tuple(T head, T tail) {
        this.head = head;
        this.tail = tail;
    }

    public T getHead() {
        return head;
    }

    public T getTail() {
        return tail;
    }

}
