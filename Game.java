import java.util.List;
import java.util.ArrayList;
import java.util.Random;

//classe abstraite pour la base d'un mode de jeu
public abstract class Game{
    protected List<String> dictionnary = List.of("gabby","writing","tub","grass","receipt","accidental","baby","road","quack","fantastic","crawl","songs"
    ,"dusty","parsimonious","groan","profuse","remember","volatile","unused","search","grieving","public","furry","snore","glossy","hand","unnatural"
    ,"even","mysterious","abortive","kind","educated","lethal","abaft","superficial","view","shelf","divergent","fanatical","liquid","macho","wealth"
    ,"improve","brown","money","lock","hurried","determined","ducks","stop","keen","enchanted","replace","run","mere","colorful","tranquil","clever"
    ,"brush","fortunate","cream","teaching","son","reduce","marked","connection","steer","day","incandescent","alive","calm","hobbies","wipe","station"
    ,"iron","leather","gratis","earn","grip","succinct","victorious","normal","eager","finger","veil","icicle","ludicrous","panicky","stream"
    ,"endurable","puny","shoes","adorable","engine","lucky","permit","shallow","talk","breathe","natural","kitty","picture","lonely","cabbage","eggs"
    ,"pen","wait","field","apologise","crowded","wail","four","influence","pass","chemical","buzz","hallowed","murder","toes","female","vengeful","prick"
    ,"spring","spot","knee","glorious","classy","land","careful","fence","oafish","plucky","befitting","alcoholic","thankful","wealthy","book","bee"
    ,"hapless","thrill","impress","education","nonstop","rat","omniscient","cross","release","second","mint","macabre","rapid","stuff","righteous","mom"
    ,"familiar","table","nine","rain","neck","gaping","shiny","dazzling","boundary","heal","increase","wholesale","spiritual","fabulous","acoustics","scary"
    ,"seashore","adjustment","scratch","room","gaudy","rake","depressed","passenger","grumpy","fearful","plants","mellow","eminent","offer","spotty","rest"
    ,"shivering","attract","add","challenge","broad","cherry","paltry","sore","different","person","little","repulsive","curved","notebook","yielding");
    protected List<String> words = new ArrayList<String>();
    protected int[] stats = new int[3];
    protected int charUtiles, keyPressed, timeLimit, lives, score, level, correctWords = 0;
    protected String userInput = "";

    public List<String> getWords(){
        return words;
    }
    
    public void setWords(List<String> words){
        this.words = words;
    }

    public int[] getStats(){
        return stats;
    }

    public void setCorrectWords(int x){
        correctWords = x;
    }

    public int getCorrectWords(){
        return correctWords;
    }

    public void setUserInput(String s){
        userInput = s;
    }

    public String getUserInput(){
        return userInput;
    }

    public void setCharUtiles(int x){
        charUtiles = x;
    }

    public void setKeyPressed(int x){
        keyPressed = x;
    }

    public abstract void setTimeLimit(int lim);

    public abstract int getTimeLimit();

    public abstract int getLives();

    public abstract void setLives(int l);

    public abstract boolean hasLives();

    public abstract int getLevel();

    public abstract void setLevel(int l);

    public void setScore(int s){
        score = s;
    }
    
    public int getScore(){
        return score;
    }

    public abstract void stats(int time);

    public abstract void initWords();

    public abstract void removeAndUpdate();
}