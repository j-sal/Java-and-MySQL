import Dao.ConMySQL;
import Models.*;
import Service.*;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
	
	private Scanner input = new Scanner(System.in);
    private WeiboService weiboService = new WeiboService();
    
    private static int total=985373;
    int percentage=0;
    
    public void p(String str) {
        System.out.println(str);
    }
    
    public void viewSinaUsers(){
        List list = weiboService.viewSinaUsers();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Weibo weibo = (Weibo) it.next();
            p("..................................");
            p("Sina Users: "+weibo.getSinaUsers());
            percentage=weibo.getSinaUsers()*100/total ;
            p("Percentage from total users: "+percentage+"%");
            p("..................................");
        }
    }
    
    public void viewNonSina(){
        List list = weiboService.viewNonSina();
        weiboService.viewTotal();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Weibo weibo = (Weibo) it.next();
            p("..................................");
            p("Non Sina users: "+weibo.getNonSina());
            percentage=weibo.getNonSina()*100/total ;
            p("Percentage from total users: "+percentage+"%");
            p("..................................");
        }
    }
    
    public void viewTotal(){
        List list = weiboService.viewTotal();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Weibo weibo = (Weibo) it.next();
            p("..................................");
            p("Total Users: "+weibo.getTotal());
            p("..................................");
        }
    }
    
    public Main() {
        while (true) {
            p("-----------------------------------------");
            p("Welcome to the Weibo Java Analyst!!!!");
            p("          Jogebeth Gomez     ");
            p("Please Choose an Option");
            p("1. View amount of Sina Users");
            p("2. View amount of Users excluding Sina");
            p("3. View total amount of registered users");
            p("4. Exit");
            p("Your selected option:");
            int serNum = input.nextInt();
            switch (serNum) {
                case 1:
                    viewSinaUsers();
                    break;
                case 2:
                	viewNonSina();
                	break;
                case 3:
                	viewTotal();
                	break;
                case 4:
                    exit(0);
                    break;
                default:
                    p("The Number is Wrong!");
            }
        }
    }
    public static void main(String[] args) {
        new Main();
    }
}

