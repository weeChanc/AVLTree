package stack;

abstract class AbsStack<E>{

    abstract void push(E element);

    abstract E pop();

    abstract E peek();

    abstract boolean empty();

    abstract int size();

    abstract void clear();

}
