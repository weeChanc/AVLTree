package queue;

public class LinkQueue<E> extends AbsQueue<E> {

    private LinkQueueNode<E> root;
    private int size = 0 ;


    @Override
    public void offer(E element) {
        if(element == null) return;
        LinkQueueNode<E> node = new LinkQueueNode<>();
        node.data = element;
        if(root == null){
            root = node;
        }else{
            LinkQueueNode<E> tmp = root;
            while(true){
                if(tmp.next != null) {
                    tmp = tmp.next;
                }else{
                    tmp.next = node;
                    break;
                }
            }
        }
        size++;
    }

    @Override
    public E peek() {
        return root.data;
    }

    @Override
    public E poll() {
        E element = root.data;
        root = root.next;
        return element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        return root == null;
    }

    private final class LinkQueueNode<E>{
        E data;
        LinkQueueNode<E> next;
    }

}
