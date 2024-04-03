import java.util.Scanner;

/*توجه: در حل این سوال، باید ساختار داده لیست پیوندی را پیاده سازی کنید و مجاز به استفاده از کتابخانه های آماده نیستید.

مایک که جدیدا در دانشگاه گیلان قبول شده است ، در یک مسابقه برنامه نویسی شرکت کرده است . اما از آنجایی که مایک انسان بدردنخوری می باشد ، قصد دارد که با تقلب یکی از مسئله های این مسابقه را حل کند . با اینکه ما دوست نداریم هیچوقت در تقلب به کسی کمک کنیم ، اما در این سوال می خواهیم مسئله را برای مایک حل کنیم. مسئله در رابطه با بازی Dota2 میباشد . در این بازی دو گروه Radiant و Dire وجود دارند. سنای Dota2 شامل سناتورهایی از دو گروه یاد شده است. اکنون سنا میخواهد در رابطه با تغییر در بازی تصمیم گیری کند. رای گیری برای این تغییر یک روال مبتنی بر دور است. در هر دور ، هر سناتور می تواند یکی از دو حقوق زیر را اعمال کند:

    حق یک سناتور را ممنوع کند : یک سناتور می تواند سناتور دیگری را در این دور و دورهای بعدی ا از دست بدهد.
    اعلام پیروزی : اگر یک سناتور متوجه شد سناتورهایی که هنوز حق رای دارند همه از یک گروه هستند ، میتواند پیروزی را اعلام کرده و در مورد تغییر بازی تصمیم گیری کند.

ما یک رشته شامل D و R از سنا داریم (D = Dire و R = Radiant) سپس اگر n تا سناتور وجود داشته باشد ، اندازه رشته داده شده n خواهد بود. روش دورگردان از اولین سناتور تا اخرین سناتور به ترتیب مشخص شروع می شود . این رویه تا پایان رای گیری ادامه خواهد داشت. تمام سناتورهایی که حقوق خود را از دست داده اند ، در طی این روش حذف خواهند شد. فرض کنید که هر سناتور به اندازه کافی باهوش است و بهترین استراتژی را برای حزب خود بازی می کند. پیش بینی کنید که در نهایت کدام طرف پیروزی را اعلام کرده و بازی Dota2 را تغییر میدهد.*/



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
