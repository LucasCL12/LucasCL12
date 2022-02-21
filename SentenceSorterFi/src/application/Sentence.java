package application;

public class Sentence {
    String str;
    int sc;
    public Sentence(String string,int score){
        str=string;
        sc=score;
    }

    public int getScore(){
        return sc;
    }
    public String getString(){
        return str;
    }
}
