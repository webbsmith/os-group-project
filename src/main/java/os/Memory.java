package os;

/* Simple memory class of the required size 1024 words.
   A counter keeps track of the next available memory location.

   public void newWord(String newword)
   The String newword gets added to the next available position in memory.
   If memory is full (counter >= 1024), counter is reset to 0, and memory at the start
   begins to be overwritten.

   public String getWord(int x)
   Returns the string located at position x in the memory's array.

   public int getCounter()
   Returns the current value of the counter representing the next memory position to be written to.
*/

public class Memory{
    public String[] word = new String[1024];
    public int counter = 0;

    public void newWord(String newword){
        if (counter >= 1024){
            counter = 0;
            word[counter] = newword;
            counter++;
        }
        else{
            word[counter] = newword;
            counter++;
        }
    }

    public String getWord(int x){
        return word[x];
    }

    public int getCounter(){
        return counter;
    }
}