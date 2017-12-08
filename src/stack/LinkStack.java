package stack;


public class LinkStack<E> extends AbsStack<E> {

    private StackNode<E> head ;

    private int size = 0 ;


    @Override
    public void push(E element) {
        StackNode<E> e = new StackNode<>(element);
        if(head == null){
            head = e;
        }else{
            e.next = head;
            head = e;
        }
        size++;
    }

    @Override
    public E pop() {
        E e = head.element;
        head = head.next;
        return e;
    }

    @Override
    public E peek() {
        return head.element;
    }

    @Override
    public boolean empty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    class StackNode<E>{

        public StackNode(E element) {
            this.element = element;
        }

        E element;

        StackNode<E> next;
    }

}



