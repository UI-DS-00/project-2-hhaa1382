package Elements;

public class MyLinkList<E extends Comparable<E>>{
    private static class Node<E>{
        Node<E> next;
        E element;

        Node(E element,Node<E> next){
            this.next=next;
            this.element=element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public E getElement() {
            return element;
        }

        public void setElement(E element) {
            this.element = element;
        }
    }

    private int size=0;
    private Node<E> head;
    private Node<E> tail;

    public int size(){
        return this.size;
    }

    public boolean isEmpty(){
        return size==0;
    }

    public E first(){
        if(isEmpty())   return null;
        return head.getElement();
    }

    public E last(){
        if(isEmpty())   return null;
        return head.getElement();
    }

    public void addFirst(E element){
        head= new Node<E>(element,head);
        if(isEmpty()){
            tail=head;
        }

        size++;
    }

    public void addLast(E element){
        Node<E> hold=new Node<>(element,null);
        if(isEmpty()){
            head=hold;
        }
        else{
            tail.setNext(hold);
        }
        tail=hold;
        size++;
    }

    public E removeFirst(E element){
        if(isEmpty())   return null;
        E hold=head.getElement();
        head=head.getNext();
        size--;
        if(size==0){
            tail=null;
        }
        return hold;
    }

    public E get(int index){
        Node<E> temp=head;
        for(int i=0;i<index;i++){
            temp=temp.getNext();
        }
        return temp.getElement();
    }

    public void remove(int index){
        if(index<size) {
            Node<E> current = head;
            Node<E> pre = null;

            for (int i = 0; i < index; i++) {
                pre = current;
                current = current.getNext();
            }

            if (pre != null) {
                pre.next = current.getNext();
            }
            else {
                head = head.getNext();
            }
            size--;
        }
    }

    public boolean contain(E element){
        Node<E> temp=head;
        while (temp!=null){
            if(temp.getElement()==element){
                return true;
            }
            temp=temp.getNext();
        }
        return false;
    }

    public void sort(){
        Node<E> current=head;
        Node<E> next;

        while (current!=null){
            next=head.getNext();
            while (next!=null){
                if(current.getElement().compareTo(next.getElement())<0){
                    E temp=current.getElement();
                    current.element=next.getElement();
                    next.element=temp;
                }
                next=next.getNext();
            }
            current=current.getNext();
        }
    }
}
