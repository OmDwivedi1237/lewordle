package lewordle;

public class Word {
    private char[] word;

    public Word(String word) {
        this.word = word.toCharArray();
    }

    public char[] getWord() {
        return word;
    }

    @Override
    public String toString() {
        return new String(word);
    }

    public boolean checkLetter(char letter) {
        for (char c : word) {
            if (c == letter) {
                return true;
            }
        }
        return false;
    }
}
