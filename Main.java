import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        char[] senators = input.toCharArray();
        char winner = predictWinner(senators);
        printWinner(winner);
    }

    public static char predictWinner(char[] senators) {

        //Initialize senators
        Senator head = null, tail = null;
        boolean radiantWin = false, direWin = false;

        //Create list of senators
        for (char s : senators) {
            Senator senator = new Senator(s);

            if (head == null) {
                head = senator;
                tail = senator;
            } else {
                tail.next = senator;
                tail = senator;
            }
        }
        tail.next = head; //For recursive


        Senator current = head;
        Senator currentCheck;
        int radiantCount, direCount;
        while (true) {
            //Reset counter
            radiantCount = 0;
            direCount = 0;

            //If there's only one senator
            if (current.next == current) {
                if (current.party == 'R') {
                    radiantWin = true;
                    break;
                } else {
                    direWin = true;
                    break;
                }
            }

            //Next senator can't vote
            Senator nextVoteTaken = current.next;
            while (nextVoteTaken.party == current.party || !nextVoteTaken.canVote) {
                nextVoteTaken = nextVoteTaken.next;
            }
            nextVoteTaken.canVote = false;

            //Check win probability
            currentCheck = head;
            do {
                if (currentCheck.canVote) {
                    if (currentCheck.party == 'R') {
                        radiantCount++;
                    } else if (currentCheck.party == 'D') {
                        direCount++;
                    }
                }
                currentCheck = currentCheck.next;
            } while (currentCheck != head);

            if (direCount <= 0) {
                radiantWin = true;
                break;
            } else if (radiantCount <= 0) {
                direWin = true;
                break;
            }

            //Next senator who can vote
            current = current.next;
            while (!current.canVote) {
                current = current.next;
            }
        }


        //Win condition
        char winner = 'n';
        if (radiantWin && !direWin) {
            winner = 'R';
        } else if (direWin && !radiantWin) {
            winner = 'D';
        }
        return winner;
    }

    public static void printWinner(char winner) {
        if (winner == 'R') {
            System.out.println("Radiant");
        } else if (winner == 'D') {
            System.out.println("Dire");
        }
    }
}

class Senator {
    char party;
    Senator next;
    boolean canVote;

    public Senator(char party) {
        this.party = party;
        this.next = null;
        this.canVote = true;
    }
}