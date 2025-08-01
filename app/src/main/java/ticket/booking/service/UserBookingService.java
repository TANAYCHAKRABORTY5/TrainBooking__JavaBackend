package ticket.booking.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> userList;
    private User user;

    private final String USER_FILE_PATH = "app/src/main/localDB/users.json";


    public UserBookingService() throws IOException{
        loadUserListFromFile();
    }

    public UserBookingService(User user ) throws IOException{
        this.user = user;
        loadUserListFromFile();
    }

    private void loadUserListFromFile() throws IOException{


        userList = objectMapper.readValue(new File(USER_FILE_PATH), new TypeReference<List<User>>() {
        });
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user1.getPassword(),user.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    public void saveUserListToFile() throws IOException{
        File userFile = new File(USER_FILE_PATH);
        objectMapper.writeValue(userFile,userList);
    }

    public void fetchBookings(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getHashedPassword(),user1.getPassword());
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketId){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Ticket Id to cancel : ");
        ticketId = sc.next();

        if(ticketId == null || ticketId.isEmpty()){
            System.out.println("Ticket Id cannot be empty : ");
            return Boolean.FALSE;
        }
        String finalTicketId1 = ticketId;
        boolean removed = user.getTicketsBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));
        String finalTicketId = ticketId;
        user.getTicketsBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));

        if(removed){
            System.out.println("Ticket with ID " +ticketId + "has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketId) ;
            return Boolean.FALSE;
        }
    }

    public List<Train> getTrains(String source,String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source,destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }

    public List<List<Integer>> fetchSeats(Train train){
        return train.getSeats();
    }


    public Boolean bookTrainSeats(Train train, int row,int seat){
        try {
            TrainService trainService = new TrainService();
            List<List<Integer>> seats = train.getSeats();
            if(row >= 0 && row < seats.size() && seat >=0 && seat < seats.get(row).size()){
                if(seats.get(row).get(seat) ==0){
                    seats.get(row).set(seat,1);
                    train.setSeats(seats);
                    trainService.addTrain(train);
                    return true;
                }else{
                    return false;
                }
            }else {
                return false;
            }
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }
}
