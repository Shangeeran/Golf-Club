package shanga;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class GolfClub {
    public static HashMap<String, Integer> STORE;
    public static HashMap<String, Integer> KEEP;
    public static void main(String[] args) {
        STORE = new HashMap<String, Integer>();
        KEEP = new HashMap<String, Integer>();
        System.out.println("Welcome to Springfield Golf Club.");
        // Scanner for select the option
        Scanner sc = new Scanner(System.in);
        int option; // Get option Integer from user
        do{
            System.out.println("");
            System.out.println("Please Select Option");
            System.out.println("1 : Enter Scores");
            System.out.println("2 : Find Golfer");
            System.out.println("3 : Display Scoreboard");
            System.out.println("4 : Delete Player");
            System.out.println("5 : Restore Player");
            System.out.println("6 : Exit Program");

            System.out.print("\t"+"Your Option is : ");

            // Stop strings when we enter "Please Select Option"
            while (!sc.hasNextInt()){
                System.out.print("Invalid Entry!!, Please Select the Option : ");
                sc.next();
            }
            // Get the option from user assign to option variable
            option = sc.nextInt();

            switch(option){
                case 1:
                    enterScore();
                    break;
                case 2:
                    findGolfer();
                    break;
                case 3:
                    displayScoreboard();
                    break;
                case 4:
                    deletePlayer();
                    break;
                case 5:
                    restorePlayer();
                    break;
                case 6:
                    exitProgram();
                    break;
                default:
                    System.out.println("Invalid Option!!! Reenter...");
            }

        } while(option!=6); // The option until the not equal 6 programme is run

    }


    //When we enter 1 option the method is directly work
    private static void enterScore() {
        Scanner se = new Scanner(System.in);
        System.out.print("How many Golfer Do you want to add : ");
        //Stop strings when we enter "How many Golfer Do you want to add"
        while (!se.hasNextInt()){
            System.out.print("Invalid Entry!!, Please try again : ");
            se.next();
        }
        int golfers = se.nextInt(); // Assign number of Player, we want to add
        if (golfers>0){
            // For loop is running until the end of number
            for (int i=1; i<=golfers; i++){
                System.out.print("Please Enter Golfer Name : ");
                String name = se.next();
                name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();

                // If enter the name then check in Database and name is already exits visible to them
                while (STORE.containsKey(name)){
                    System.out.println("Sorry!!, Name Already Taken.");
                    System.out.print("Do you want to Replace the "+name+"'s Score (y/n) : ");
                    String selection = se.next().toLowerCase();
                    // If select yes, go to get new Score and update
                    if (selection.equals("y")){
                        break;
                    } // If select no, ask new Player name
                    else if(selection.equals("n")){
                        System.out.print("Please Enter new Golfer Name : ");
                        name = se.next();
                        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
                    } else {
                        // otherwise again ask the yes or no option
                        System.out.println("Invalid Option!!! Reenter...");
                    }
                }
                System.out.print("Please Enter the "+name+"'s Score : ");
                //Stop string when we enter "Please Enter the Golfer Score"
                while (!se.hasNextInt()){
                    System.out.print("Invalid Entry!!, Please Enter "+name+"'s Score : ");
                    se.next();
                }
                int score = se.nextInt();
                //when we enter the score lessthan 18 or greatthan 108
                while (18>=score|| 108<=score){
                    System.out.print("Please Enter "+name+"'s Valid Score (18-108) : ");
                    //After enter the interger, again enter string block when we enter "Please Enter the Golfer Score"
                    while (!se.hasNextInt()){
                        System.out.print("Invalid Entry!!, Please Enter "+name+"'s Score : ");
                        se.next();
                    }
                    score = se.nextInt();
                }
                // Save in Database(STORE)
                STORE.put(name,score);
                System.out.println("\t"+" Successfully  added "+"("+name+" : "+score+")");

            } // End for loop
        }
        else {
            System.out.println("Invalid Entry!!..");
        }
    } // End enterScore method


    // When we enter 2 option the method is directly work
    private static void findGolfer() {
        // used for obtaining the input of the primitive data type
        Scanner sf = new Scanner(System.in);
        System.out.print("Please Enter the Golfer Name : ");
        String find = sf.next();
        find = find.substring(0,1).toUpperCase() + find.substring(1).toLowerCase();
        // Get the name from user and find the name from my DataBase
        if (STORE.containsKey(find)) // If the name available
        {
            System.out.println("\t"+"Name is  : "+find);
            System.out.println("\t"+"Score is : "+STORE.get(find));
        } else{
            System.out.println("Sorry!!, Name not found.");
        }
    } // End findGolfer method



    // When we enter 3 option the method is directly work
    private static void displayScoreboard() {
        HashMap<String, Integer> FINAL=STORE;
        // HashMap(STORE) assign to Final
        FINAL=STORE
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        // displayScoreboard visible
        System.out.println("Players Names and Scores are here,");
        // one by one visible result
        for (String i : FINAL.keySet()){
            System.out.println("\t"+i+"'s score is "+FINAL.get(i)+".");
        }

    } // End displayScoreboard method


    // When we enter 4 option the method is directly work
    private static void deletePlayer() {
        Scanner del = new Scanner(System.in);
        System.out.print("Please enter the player name : ");
        String name = del.next();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        if(STORE.containsKey(name)){
            int z =0;
            while (z ==0){
                System.out.print("Are you confirm to delete "+name+" (y/n) : ");
                String confirm = del.next().toLowerCase();
                if (confirm.equals("y")){
                    //if delete player from STORE backup in KEEP
                    KEEP.put(name,STORE.get(name));
                    STORE.remove(name); //removed
                    System.out.println("\t"+name+" removed from this club.");
                    z=1;
                }
                else if (confirm.equals(("n"))){
                    System.out.println("\t"+name+" not remove from this club.");
                    z=1;
                }
                else{
                    System.out.println("Invalid Option!!! Reenter...");
                }
            }
        }
        else {
            System.out.println("\t"+"Sorry!!, Name not found.");
        }
    } //End delete player method


    // When we enter 5 option the method is directly work
    private static void restorePlayer() {
        Scanner res = new Scanner(System.in);
        System.out.print("Please enter player name : ");
        String name = res.nextLine();
        name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        //if we restore player from 2nd DB
        if(KEEP.containsKey(name)){
            int x =0;
            while (x ==0){
                System.out.print("Are you sure to restore "+name+" (y/n) : ");
                String confirm = res.next().toLowerCase();

                if (confirm.equals("y")){
                    //if we restore from KEEP to STORE
                    STORE.put(name,KEEP.get(name));
                    KEEP.remove(name); //removed
                    System.out.println("\t"+name+" successfully added to this club.");
                    x=1;
                }
                else if (confirm.equals(("n"))){
                    System.out.println("\t"+name+" not restore to this club.");
                    x=1;
                }
                else{
                    System.out.println("Invalid Option!!! Reenter...");
                }
            }
        }
        else {
            System.out.println("\t"+"Sorry!!, Name not found.");
        }
    } //End restorePlayer method


    // When we enter 6 option the method is directly work
    private static void exitProgram() {
        System.out.println("Thank You for Your Support!!");
        System.exit(0);
    } // End exitProgram method


} // End of GolfClub Class
