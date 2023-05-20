/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espol.util;

import java.util.Iterator;
import java.util.ListIterator;

/**
 *
 * @author Kevin C, Kenny J., Richard P.
 * @param <E>
 */
public class CircularDoubleLinkedList <E> implements List<E>{
    private Node<E> last;
    private Node<E> first;
    private int tam;
    
    public CircularDoubleLinkedList(){
        last=null;
        first=null;
        tam=0;
    }
   
    
     private class Node<E>{
        private E contenido;
        private Node<E> siguiente;
        private Node<E> anterior;
        
        public Node(E e){
            this.contenido=e;
            this.siguiente=null;
            this.anterior=null;
        }
    }
      public Node <E> getNode(int index){
        if(index==0){
            return first;
        }
        if (index==tam-1){
            return last;
        }
        Node <E> n;
        if(index<tam/2){
            n=first;
            for(int i=0;i<index;i++){
                n=n.siguiente;
            }
        }else{
            n=last;
            for(int i=tam-1;i>index;i--){
                n=n.anterior;
            }  
        }
        return n;
    }
      public ListIterator<E> listIterator(){
        ListIterator<E> lit = new ListIterator<E>(){
        Node<E> nodo = first.siguiente;
        @Override
        public boolean hasNext() {
            return nodo.siguiente!=null;
        }
        @Override
        public E next() {
            E tmp = nodo.contenido;
            nodo = nodo.siguiente;
            return tmp;
        }
        @Override
        public boolean hasPrevious() {
            return nodo.anterior!=null;
        }
        @Override
        public E previous() {
            nodo = nodo.anterior.anterior;
            E tmp = nodo.contenido;
            return tmp;
        }
        @Override
        public int nextIndex() {
            return 0;
        }
        @Override
        public int previousIndex() {
            return 0;
            }
        @Override
        public void remove() {
        }
        @Override
        public void set(E arg0) {
        }
        @Override
        public void add(E arg0) {
        }    
        };
        return lit;
    }
      
        
   
        

    @Override
    public boolean addFirst(E e) {
        if(e==null){ 
        return false;
        }
        Node<E> n = new Node(e);
        if(isEmpty()) {
            first = n; 
            last = n;
        }
        else{
            Node<E> primero = first;
            n.siguiente=primero;
            n.anterior=last;
            primero.anterior=n;
            last.siguiente=n;
            first=n;          
            
        }
        tam++;
        return true;
    }

    @Override
    public boolean addLast(E e) {
        if(e==null){ 
        return false;
        }
        Node<E> n = new Node(e);
        if(isEmpty()) {
            last = n; 
            first = n;
        }
        else{
            Node<E> ultimo=last;
            n.siguiente=first;
            n.anterior=ultimo;
            ultimo.siguiente=n;
            first.anterior=n;
            last=n;
        }
        tam++;
        return true;
    }

    @Override
    public E getFirst() {
        if (isEmpty()){
            return null;
        }
        return first.contenido;
    }

    @Override
    public E getLast() {
        if (isEmpty()){
            return null;
        }
        return last.contenido;
    }

    @Override
    public int indexOf(E e) {
        if (e==null){
            return -1;
        }
        if (isEmpty()){
            return -1;
        }
        Node <E> n=first;
        for(int i=0;i<tam;i++){
            if(n.contenido.equals(e)){
                return i;
            }
            n=n.siguiente;
        }
        return -1;
        
    }

    @Override
    public int size() {
        return tam;
    }

    @Override
    public boolean removeLast() {
         if (isEmpty()){
            return false;
        }
         last=null;
         if(tam==1){
            first=null;
        }else{
             Node <E> penultimo=last.anterior;
             Node <E> primero=first;
             penultimo.siguiente=primero;
             primero.anterior=penultimo;
             last.siguiente=last.anterior=null;
             last=penultimo;
             
         }
         return true;
    }

    @Override
    public boolean removeFirst() {
        if (isEmpty()){
            return false;
        }
        first=null;
                if(tam==1){
            last=null;
        }else{
            Node<E> sig=first.siguiente;
            Node<E> ultimo=last;
            sig.anterior=ultimo;
            ultimo.siguiente=sig;
            first.siguiente=first.anterior=null;
            first=sig;
                }
         return true;
    }

    @Override
    public boolean insert(int index, E e) {
        if(e==null){
            return false;
        }
        if(index>tam-1 || index<0){
            System.out.println("Indice no valido");
        }
        if (index==0){
            addFirst(e);
            return true;            
        }
        Node <E> n=new Node(e);
        Node <E> n1=getNode(index);
        Node <E> ante=n1.anterior;
        ante.siguiente=n1.anterior=n;
        n.anterior=ante;
        n.siguiente=n1;
        tam++;
        return true; 
    }

    @Override
    public boolean set(int index, E e) {
        if(e==null){
            return false;
        }
        if(index>tam-1 || index<0){
            System.out.println("Indice no valido");
        }
        if(index==0){
            first.contenido=e;
        }
        if(index==tam-1){
            last.contenido=e;
        }
        Node <E> n=getNode(index);
        n.contenido=e;
        return true;
    }

    @Override
    public boolean isEmpty() {
        return tam==0;
    }

    @Override
    public E get(int index) {
        if(index>tam-1 || index<0){
            System.out.println("Indice no valido");
        }
        if(isEmpty()){
            return null;
        }
        if(index==0){
            return first.contenido;
        }
        if(index==tam-1){
            return last.contenido;
        }
        Node <E> n=getNode(index);
        return n.contenido;
    }

    @Override
    public boolean contains(E e) {
        if(e==null){
            return false;
        }
        Node <E> n=first;
        for(int i=0;i<tam;i++){
            if (n.contenido.equals(e)){
                return true;
            }
            n=n.siguiente;
        }
        return false;
    }

    @Override
    public boolean remove(int index) {
       if(index>tam-1 || index<0){
            System.out.println("Indice no valido");
        }
        if(index==0){
            return removeFirst();
        }
        if(index==tam-1){
            return removeLast();
        }
        Node <E> n=getNode(index-1);
        Node <E> remove=n.siguiente;
        Node <E> n1=remove.siguiente;
        remove.contenido=null;
        remove.siguiente=remove.anterior=null;
        n.siguiente=n1;
        n1.anterior=n;
        tam--;
        return true; 
    }
}
