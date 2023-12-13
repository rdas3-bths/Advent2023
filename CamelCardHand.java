import java.util.ArrayList;

public class CamelCardHand implements Comparable {
    private ArrayList<Integer> cards;
    private int bid;
    private String handType;

    public CamelCardHand(String cards, int bid) {
        this.cards = new ArrayList<Integer>();
        this.bid = bid;
        for (int i = 0; i < cards.length(); i++) {
            char c = cards.charAt(i);
            if (Character.isDigit(c)) {
                this.cards.add(Integer.parseInt(c + ""));
            }
            else {
                String cardString = c + "";
                if (cardString.equals("T")) this.cards.add(10);
                else if (cardString.equals("J")) this.cards.add(1);
                else if (cardString.equals("Q")) this.cards.add(12);
                else if (cardString.equals("K")) this.cards.add(13);
                else if (cardString.equals("A")) this.cards.add(14);
            }
        }
        setHandType();
    }

    public void setHandType() {
        if (checkFiveOfAKind())
            handType = "A";
        else if (checkFourOfAKind())
            handType = "B";
        else if (checkFullHouse())
            handType = "C";
        else if (checkThreeOfAKind())
            handType = "D";
        else if (checkTwoPairs())
            handType = "E";
        else if (checkOnePair())
            handType = "F";
        else
            handType = "G";
    }

    public boolean checkFiveOfAKind() {
        for (int i = 0; i < cards.size(); i++) {
            int value = cards.get(i);
            int count = countValue(cards, value);
            if (count == 5) return true;
        }
        return false;
    }

    public boolean checkFourOfAKind() {
        for (int i = 0; i < cards.size(); i++) {
            int value = cards.get(i);
            int count = countValue(cards, value);
            if (count == 4) return true;
        }
        return false;
    }

    public boolean checkFullHouse() {
        boolean foundThree = false;
        boolean foundTwo = false;

        ArrayList<Integer> copyOfCards = new ArrayList<Integer>();

        copyOfCards.addAll(cards);


        for (int i = 0; i < copyOfCards.size(); i++) {
            int value = copyOfCards.get(i);
            int count = countValue(copyOfCards, value);
            if (count == 3) {
                foundThree = true;
                removeValue(copyOfCards, value);
                break;
            }
        }

        for (int i = 0; i < copyOfCards.size(); i++) {
            int value = copyOfCards.get(i);
            int count = countValue(copyOfCards, value);
            if (count == 2) {
                foundTwo = true;
                break;
            }
        }
        return foundThree && foundTwo;
    }

    public boolean checkThreeOfAKind() {
        boolean foundThree = false;
        boolean foundTwo = false;
        for (int i = 0; i < cards.size(); i++) {
            int value = cards.get(i);
            int count = countValue(cards, value);
            if (count == 3) return true;
        }
        return false;
    }

    public boolean checkTwoPairs() {
        ArrayList<Integer> copyOfCards = new ArrayList<Integer>();
        copyOfCards.addAll(cards);

        for (int i = 0; i < copyOfCards.size(); i++) {
            int value = copyOfCards.get(i);
            int count = countValue(copyOfCards, value);
            if (count == 2) {
                removeValue(copyOfCards, value);
                break;
            }
        }

        for (int i = 0; i < copyOfCards.size(); i++) {
            int value = copyOfCards.get(i);
            int count = countValue(copyOfCards, value);
            if (count == 2) {
                return true;
            }
        }

        return false;

    }

    public boolean checkOnePair() {

        for (int i = 0; i < cards.size(); i++) {
            int value = cards.get(i);
            int count = countValue(cards, value);
            if (count == 2) {
                if (!checkTwoPairs() && !checkFullHouse())
                    return true;
            }
        }
        return false;
    }



    public void removeValue(ArrayList<Integer> cards, int value) {
        for (int i = 0; i < cards.size(); i++) {
            int currentValue = cards.get(i);
            if (currentValue == value || currentValue == 1) {
                cards.remove(i);
                i--;
            }
        }
    }

    private int countValue(ArrayList<Integer> cards, int value) {
        int count = 0;
        for (int i = 0; i < cards.size(); i++) {
            int currentValue = cards.get(i);
            if (value == currentValue || currentValue == 1) count++;
        }
        return count;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Integer> cards) {
        this.cards = cards;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getHandType() {
        if (handType.equals("A")) return "Five of a Kind";
        else if (handType.equals("B")) return "Four of a Kind";
        else if (handType.equals("C")) return "Full House";
        else if (handType.equals("D")) return "Three of a Kind";
        else if (handType.equals("E")) return "Two Pairs";
        else if (handType.equals("F")) return "One Pair";
        else if (handType.equals("G")) return "High Card";
        else {
            return "";
        }

    }

    public int compareTo(Object o) {
        CamelCardHand other = (CamelCardHand)o;

        if (this.handType.equals(other.handType)) {
            for (int i = 0; i < this.cards.size(); i++) {
                int valueOne = cards.get(i);
                int valueTwo = other.cards.get(i);
                if (valueOne != valueTwo) {
                    return (valueOne-valueTwo);
                }
            }
        }

        else {
            return other.handType.compareTo(this.handType);
        }

        return 0;
    }
}
