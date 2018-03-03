package os;

/*
public String[] word
   An array of 2048 strings(maximum disk space allowed),
   each one representing a word from the program file (ex. '0xC050005C')

public int counter = 0
   A counter to keep track of which location in the array the next word is to be stored

public void newWord(String newword)
   This function takes a string and places it in the next available space in the word array.
   It checks to make sure there is enough room and outputs an error message if not.

public String getWord(int x)
   Basic getter function, returns the word at the x location in the array, word[x]

This disk class does not have the control cards from the program file (ex. '// JOB 1 17 2')
There will be a PCB class that stores that information.
*/

public class Disk{
    public String[] word = new String[2048];
    public int counter = 0;

    public void newWord(String newword){
        if(counter < 2048){
            word[counter] = newword;
            counter++;
        }
        else{
            System.out.println("No more space in disk memory.");
        }
    }
    public String getWord(int x){
        return word[x];
    }
}