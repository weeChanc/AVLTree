package queue;

abstract class AbsQueue<E> {

    abstract void offer(E element);

    abstract E peek();

    abstract E poll();

    abstract int size();

    abstract boolean empty();
}
