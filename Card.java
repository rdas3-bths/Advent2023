public class Card {
    private int cardNumber;
    private int totalWinning;
    private int copies;

    public Card(int cardNumber, int totalWinning) {
        this.cardNumber = cardNumber;
        this.totalWinning = totalWinning;
        copies = 1;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getTotalWinning() {
        return totalWinning;
    }

    public void setTotalWinning(int totalWinning) {
        this.totalWinning = totalWinning;
    }

    public int getCopies() {
        return copies;
    }

    public void addCopy() {
        copies++;
    }

    public String toString() {
        return "Card number: " + cardNumber + ", Total Winning: " + totalWinning + ", Copies: " + copies;
    }
}
