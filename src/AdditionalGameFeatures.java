import java.util.Scanner;
public class AdditionalGameFeatures {
    private String userName;
    public AdditionalGameFeatures(){
    }
    public String userName(){
        Scanner scan = new Scanner(System.in);
        System.out.println("What is your name? ");
        String userName = scan.nextLine();
        return userName;
    }
}
