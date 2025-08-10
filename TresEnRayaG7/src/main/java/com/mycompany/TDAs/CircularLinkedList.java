package com.mycompany.TDAs;

import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Usuario
 */
public class CircularLinkedList<E> implements List<E>{
    private CircularNode<E> last;
    
    public CircularLinkedList() {
        this.last = null;
    }
    
    public CircularLinkedList(E[] elements){
        this.last = null;
        for(E e: elements){
            this.addLast(e);
        }
    }
    
    
    
    @Override
    public boolean addFirst(E element) {
        CircularNode<E> node = new CircularNode<>(element);
        if(element == null){
            return false;
        }   
        else if(isEmpty()){
            node.setNext(node);
            node.setPrevious(node);
            this.last = node;
            return true;
        }
        else{
            node.setPrevious(this.last);
            node.setNext(this.last.getNext());
            this.last.getNext().setPrevious(node);
            this.last.setNext(node);
            return true;
        }
    }

    @Override
    public boolean addLast(E element) {
        CircularNode<E> node = new CircularNode<>(element);
        if(element == null){
            return false;
        }   
        else if(isEmpty()){
            node.setNext(node);
            node.setPrevious(node);
            this.last = node;
            return true;
        }
        else{
            node.setPrevious(this.last);
            node.setNext(this.last.getNext());
            this.last.getNext().setPrevious(node);
            this.last.setNext(node);
            this.last = node;
            return true;
        }
    }

    @Override
    public boolean removeFirst() {
        if(isEmpty()){
            return false;
        }
        else if(this.last == this.last.getNext() && this.last == this.last.getPrevious()){
            this.last = null;
        }
        else{
            CircularNode<E> tmp = this.last.getNext();
            CircularNode<E> node = this.last.getNext().getNext();
            node.setPrevious(this.last);
            this.last.setNext(node);
            tmp.setNext(null);
        }
        return true;
    }

    @Override
    public boolean removeLast() {
        if(isEmpty()){
            return false;
        }
        else if(this.last == this.last.getNext() && this.last == this.last.getPrevious()){
            this.last = null;
        }
        else{
            CircularNode<E> tmp = this.last;
            CircularNode<E> node = this.last.getPrevious();
            node.setNext(this.last.getNext());
            this.last = node;
            tmp.setNext(null);
            tmp.setPrevious(null);
        }
        return true;
    }
    
    @Override
    public E getFirst() {
       return this.last.getNext().getData();
    }
    
    @Override
    public E getLast() {
       return this.last.getData();
    }

    @Override
    public boolean insert(int index, E element) {
        if(element == null || index<0 ){
            return false;
        }else if(index == 0){
            addFirst(element);
            return true;
        }else if(index == this.size() - 1 || index > this.size()){
            addLast(element);
            return true;
        }
        else{
            CircularNode<E> node = new CircularNode<>(element);
            int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cont == index -1){
                    node.setNext(current.getNext());
                    node.setPrevious(current);
                    current.getNext().setPrevious(node);
                    current.setNext(node);
                    return true;
                }
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return false;
        }
    }

    @Override
    public boolean contains(E element, Comparator<E> cmp) {
        if(isEmpty() || element == null){
            return false;
        }else{
            CircularNode<E> current = this.last.getNext();
           do {
                if(cmp.compare(current.getData(), element) == 0){
                    return true;
                }
                
                current = current.getNext();
            }  while (current != this.last.getNext());
            return false; 
        }
    }

    @Override
    public E get(int index) {
        if(isEmpty() || index<0 || index > this.size()){
            return null;
        }else{
            int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cont == index ){
                    return current.getData();
                }
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return null;
        }
    }

    @Override
    public int indexOf(E element, Comparator<E> cmp) {
        if(isEmpty() || element == null){
            return -1;
        }else{
            int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cmp.compare(current.getData(), element) == 0){
                    return cont;
                }
                
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return -1;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.last == null;
    }

    @Override
    public E remove(int index) {
        if(isEmpty() || index<0 || index > this.size()){
            return null;
        }else{
           int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cont == index){
                    E dataTmp = current.getData();
                    CircularNode<E> tmp = current;
                    current.getNext().setPrevious(current.getPrevious());
                    current.getPrevious().setNext(current.getNext());
                    tmp.setNext(null);
                    tmp.setPrevious(null);
                    return dataTmp;
                }
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return null; 
        }
    }

    @Override
    public boolean remove(E element, Comparator<E> cmp) {
        if(isEmpty() || element == null){
            return false;
        }
        else{
            int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cmp.compare(current.getData(), element) == 0){
                    CircularNode<E> tmp = current;
                    current.getNext().setPrevious(current.getPrevious());
                    current.getPrevious().setNext(current.getNext());
                    tmp.setNext(null);
                    tmp.setPrevious(null);
                    return true;
                }
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return false; 
        }
    }

    @Override
    public E set(int index, E element) {
        //este metodo asigna un nuevo contenido a un nodo existente, el contenido anterior lo devuelve 
        if(element == null || index<0 || index > this.size()){
            return null;
        }else{
            int cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                if(cont == index){
                    E dataTmp = current.getData();
                    current.setData(element);
                    return dataTmp;
                }
                cont++;
                current = current.getNext();
            } while (current != this.last.getNext());
            return null;
        }
    }
    @Override
    public int size() {
        int cont = 0;
        if(isEmpty()){
            return cont;
        }else{
            cont = 0;
            CircularNode<E> current = this.last.getNext();
            do {
                cont++;
                current = current.getNext();
            } while (current != null && current != this.last.getNext());
            return cont;
        }
    }
    
    @Override
    public Iterator<E> iterator() {
        return iteratorDo();
    }
    
    public Iterator<E> iteratorDo() {
        //iterador con el bucle infinito manejado internamente
        int size = this.size();
        System.out.println(size);
        Iterator<E> it = new Iterator<E>() {
            private CircularNode<E> cursor = last.getNext();
            int c = 0;
            @Override
            public boolean hasNext() {
                return c < size - 1;
            }

            @Override
            public E next() {
                E tmp = cursor.getData();
                cursor = cursor.getNext();
                c++;
                return tmp;
            }
        };

        return it;
    }
    
    public ListIterator<E> listIterator(){
        int size = this.size();
        //System.out.println(size);
        ListIterator<E> listIt = new ListIterator<>(){
            private CircularNode<E> cursor = last.getNext();
            int c = 0;
            @Override
            public boolean hasNext() {
                return cursor != null;
            }

            @Override
            public E next() {
                E tmp = cursor.getData();
                cursor = cursor.getNext();
                c++;
                return cursor.getData();
            }

            @Override
            public boolean hasPrevious() {
                return cursor != null;
            }
            

            @Override
            public E previous() {
                E tmp = cursor.getData();
                cursor = cursor.getPrevious();
                c--;
                return cursor.getData();
            }

            @Override
            public int nextIndex() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public int previousIndex() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void add(E e) {
                
                if(e == null ){
                throw new NullPointerException("El elemento no puede ser nulo");
                }
                else{
                    CircularNode<E> node = new CircularNode<>(e);
                    node.setNext(cursor.getNext());
                    node.setPrevious(cursor);
                    cursor.getNext().setPrevious(node);
                    cursor.setNext(node);
                }
            }
            
        };
        return listIt;
    }
    
  
    
    public void addAll(){
        
    }
}

