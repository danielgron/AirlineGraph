
package airportassignment;

/**
 *
 * @author Cherry Rose Semeña
 */
public class ArrayStack<T> {
    private T[] data ;
    private int top=0;
    
    public ArrayStack(int num){
        data = (T[]) new Object[num];
    }
    
    public void push(T element){
        for (int i = 0; i < data.length; i++) {
            if(data[i] == null){
                data[i] = element;
                return;
            }
        }
    }
    
    public T pop(){
        T num = null;
        
        for (int i = data.length-1; i >= 0; i--) {
            if(data[i] != null){
                num = data[i];
                data[i] = null;
                
                return num;
            }
        }
        
        return num;
    }
    
    public boolean isEmpty(){
        boolean a=true;
        
        for (T t : data) {
            if(t != null){
                a = false;
            }
        }
        
        return a;
    } 
}
