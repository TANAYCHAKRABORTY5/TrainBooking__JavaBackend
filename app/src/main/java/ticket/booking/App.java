package ticket.booking;

import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.service.UserBookingService;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) {


        System.out.println("Running Train Booking System: ");
        Scanner sc = new Scanner(System.in);
        int option =0;
        UserBookingService userBookingService;
        try{
            userBookingService = new UserBookingService();
        }catch(IOException ex){
            System.out.println("There is  Something Wrong");
            return;
        }
        while(option!=7){
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a Seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");
            option = sc.nextInt();
            Train trainSelectedForBooking = new Train();
            switch (option){
                case 1:
                    System.out.println("Enter the username to signup :");
                    String nameToSignUp = sc.next();
                    System.out.println("Enter Password : ");
                    String passwordToSignUp = sc.next();
                    User userToSignUp = new User(nameToSignUp,passwordToSignUp, UserServiceUtil.hashPassword(passwordToSignUp),new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(userToSignUp);
                    break;
                case 2:
                    System.out.println("Enter the username to Login");
                    String nameToLogin = sc.next();
                    System.out.println("Enter the password to Login");
                    String passwordToLogin = sc.next();
                    User userToLogin = new User(nameToLogin,passwordToLogin,UserServiceUtil.hashPassword(passwordToLogin),new ArrayList<>(),UUID.randomUUID().toString());
                    try{
                        userBookingService = new UserBookingService(userToLogin);
                    }catch (IOException ex){
                        return;
                    }
                    break;
                case 3:
                    System.out.println("Fetching your bookings");
                    userBookingService.fetchBookings();
                    break;
                case 4:
                    System.out.println("Type your source station");
                    String source = sc.next();
                    System.out.println("Type your destination station");
                    String dest = sc.next();
                    List<Train> trains = userBookingService.getTrains(source,dest);
                    int index =1;
                    for (Train t : trains){
                        System.out.println(index + " Train id : "+t.getTrainId());
                        for (Map.Entry<String,String> entry : t.getStationTimes().entrySet()){
                            System.out.println("station "+entry.getKey()+" time: "+entry.getValue());
                        }
                    }
                    System.out.println("Select a train by typing 1,2,3...");
                    trainSelectedForBooking = trains.get(sc.nextInt());
                    break;
                case 5:
                    System.out.println("Select a seat out of these seats");
                    List<List<Integer>> seats = userBookingService.fetchSeats(trainSelectedForBooking);
                    for (List<Integer> row: seats){
                        for (Integer val: row){
                            System.out.print(val+" ");
                        }
                        System.out.println();
                    }
                    System.out.println("Select the seat by typing the row and column");
                    System.out.println("Enter the row");
                    int row = sc.nextInt();
                    System.out.println("Enter the column");
                    int col = sc.nextInt();
                    System.out.println("Booking your seat....");
                    Boolean booked = userBookingService.bookTrainSeats(trainSelectedForBooking, row, col);
                    if(booked.equals(Boolean.TRUE)){
                        System.out.println("Booked! Enjoy your journey");
                    }else{
                        System.out.println("Can't book this seat");
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
