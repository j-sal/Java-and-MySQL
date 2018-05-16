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
    private AdminService adminService = new AdminService();
    private UserService userService = new UserService();
    private OrderService orderService = new OrderService();
    private CuisineService cuisineService = new CuisineService();


    public void p(String str) {
        System.out.println(str);
    }

    public void viewCuisine(){
        List list = cuisineService.viewCuisine();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Cuisine cuisine = (Cuisine) it.next();
            p("......................");
            p("Name:"+cuisine.getDishName());
            p("Price:"+cuisine.getPrice());
            p("Introduction:"+cuisine.getIntroduction());
            p("Evaluation:"+cuisine.getEvaluation());
            p("......................");
        }
    }

    public void adminService() {
        p("----------Administrator Login------------");
        p("-----------------by Joey-----------------");
        p("ID:");
        int serNum = input.nextInt();
        p("Password:");
        String pwd = input.next();
        p("Loging in....");

        boolean login = adminService.loginAdmin(serNum, pwd);
        if(login){
            outerloop:
            while(true) {
                p("------------Administrator-----------");
                p("Please Choose A Service!");
                p("1. Add an Administrator");
                p("2. Delete an Administrator");
                p("3. View an Administrator");
                p("4. View Cuisine");
                p("5. Add a Dish");
                p("6. Delete a Dish");
                p("7. Exit");
                p("The Selected option:");
                int num = input.nextInt();
                switch (num) {
                    case 1:
                        p("name:");
                        String name = input.next();
                        p("Password:");
                        String pwd2 = input.next();
                        p("Adding....");
                        if(adminService.addAdministrator(name, pwd) > 0)
                            p("Successful!");
                        break;
                    case 2:
                        p("ID:");
                        int id = input.nextInt();
                        adminService.deleteAdministrator(id);
                        break;
                    case 3:
                        p("ID:");
                        int id2 = input.nextInt();
                        Administrator admin = adminService.selectAdministrator("SerialNumber",id2+"");
                        p("SerialNum:"+admin.getSerialNum());
                        p("Name:"+admin.getName());
                        p("LoginIP:"+admin.getLoginIP());
                        p("LastLoginTime:"+admin.getLastLoginTime());
                        break;
                    case 4:
                        viewCuisine();
                        break;
                    case 5:
                        p("Dishname:");
                        String dishname = input.next();
                        p("Price:");
                        int price = input.nextInt();
                        p("Introduction:");
                        String introduction = input.next();
                        p("Evaluation:");
                        String evaluation = input.next();
                        p("Adding....");
                        if(cuisineService.addCuisine(dishname, price, introduction, evaluation) > 0)
                            p("Successful!");
                        break;
                    case 6:
                        p("Dishname:");
                        String dishname2 = input.next();
                        cuisineService.deleteCuisine(dishname2);
                        break;
                    case 7:
                        break outerloop;
                    default:
                        p("The Number is Wrong!");
                }
            }
        } else {
            p("Login Failed!!!");
        }
    }

    public void userService_LR(){
        outerloop2:
        while(true) {
            p("----------User Login or  Registration------------");
            p("1. User Login");
            p("2. User Registration");
            p("3. Exit");
            p("The Serial Number of Service:");
            int c = input.nextInt();
            switch (c) {
                case 1:
                    p("Number:");
                    String number = input.next();
                    p("Password:");
                    String pwd = input.next();
                    p("Logining....");
                    if (userService.loginUser(number, pwd)){
                        p("Login Successful!");
                        User user = userService.viewUser(number);
                        userService(user);
                    } else {
                        p("Login Failed!!!");
                    }
                    break;
                case 2:
                    p("Number:");
                    String number2 = input.next();
                    p("Name:");
                    String name = input.next();
                    p("Password:");
                    String pwd2 = input.next();
                    p("Address:");
                    String address = input.next();
                    userService.registUser(number2, name, pwd2, address);
                    break;
                case 3:
                    break outerloop2;
                default:
                    p("The Number is Wrong!");
            }
        }
    }

    public void userService(User user){
        outerloop3:
        while(true) {
            p("----------User Service------------");
            p("1. View My Information");
            p("2. View My Orders");
            p("3. View Order Information");
            p("4. View Cuisine");
            p("5. Determine the Order");
            p("6. Confirm receipt of goods");
            p("7. Exit");
            p("The Serial Number of Service:");
            int c = input.nextInt();
            switch (c) {
                case 1:
                    p("----------My Information------------");
                    p("Number:"+user.getNumber());
                    p("Name:"+user.getName());
                    p("Address:"+user.getAddress());
                    p("RegistrationTime:"+user.getRegistrationTime());
                    break;
                case 2:
                    p("......................");
                    List orderlist = orderService.selectOrders(user.getNumber());
                    Iterator it = orderlist.iterator();
                    while (it.hasNext()) {
                        int ordernum = (int)it.next();
                        p(ordernum+"");
                    }
                    p("......................");
                    break;
                case 3:
                    p("The Serial Number of the Order:");
                    int o = input.nextInt();
                    Order order = orderService.viewOrder(o);
                    List cuisines = orderService.viewCuisines(o);

                    p("......................");
                    p("SerialNumber: "+order.getSerialNum());
                    p("Number: "+order.getNumber());
                    p("Status: "+order.getStatus());
                    p("Address: "+order.getAddress());
                    p("OrderTime: "+order.getOrdertime());
                    p("Remarks: "+order.getRemarks());
                    System.out.print("The Cuisines: " );
                    Iterator it2 = cuisines.iterator();
                    while (it2.hasNext()) {
                        System.out.print(it2.next() + "  ");
                    }
                    p("");
                    p("......................");
                    break;
                case 4:
                    viewCuisine();
                    break;
                case 5:
                    List cuisinelist = new LinkedList();
                    while(true){
                        p("Add Cuisine(N for exit):");
                        String dish = input.next();
                        if(dish.equals("N") || dish.equals("n"))
                            break;
                        else
                            cuisinelist.add(dish);
                    }
                    p("The Address: 1. You Address  2. New ddress");
                    p("Your choose:");
                    int ad = input.nextInt();
                    String address = null;
                    if(ad == 1) {
                        address = user.getAddress();
                    } else if(ad == 2){
                        p("Your Address:");
                        address = input.next();
                    }
                    p("Remarks:");
                    String remarks = input.next();

                    int i = orderService.determineOrder(user.getNumber(), address, remarks, cuisinelist);
                    if(i != 0){
                        p("Do you ensure to pay?(Y/N)");
                        String pay = input.next();
                        if(pay.equals("Y") || pay.equals("y"))
                            orderService.changeOrderStatus(i, "Paid");
                        else
                            orderService.changeOrderStatus(i, "Canceled");
                    }
                    break;
                case 6:
                    p("The Serial Number of the Order:");
                    int num = input.nextInt();
                    String status = orderService.viewOrderStatus(num);
                    if(status.equals("Paid")){
                        orderService.changeOrderStatus(num, "Completed");
                    } else {
                        p("The Status of Order is Invalid！");
                    }
                    break;
                case 7:
                    break outerloop3;
                default:
                    p("The Number is Wrong!");
            }
        }
    }

    public Main() {
        while (true) {
            p("---------------------------------------");
            p("Welcome to the Online Ordering System!!!!");
            p("Please Choose A Service!");
            p("1. Administrator Login");
            p("2. User Login or Registration");
            p("3. View Dishes");
            p("4. Exit");
            p("The Serial Number of Service:");
            int serNum = input.nextInt();
            switch (serNum) {
                case 1:
                    adminService();
                    break;
                case 2:
                    userService_LR();
                    break;
                case 3:
                    viewCuisine();
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
