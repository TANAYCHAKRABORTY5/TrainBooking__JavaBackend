package ticket.booking.entities;

import lombok.Setter;

import java.util.List;

public class User {
    @Setter
    private String name;
    private String password;
    @Setter
    private String hashedPassword;
    @Setter
    private List<Ticket> ticketsBooked;
    @Setter
    private String userId;

    public User(){} // required for Object Deserialization


    public User(String name,String password,String hashedPassword,List<Ticket> ticketsBooked,String userId){
        this.name = name;
        this.password = password;
        this.hashedPassword = hashedPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;

    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public List<Ticket> getTicketsBooked() {
        return ticketsBooked;
    }

    public String getUserId() {
        return userId;
    }

    public void printTickets(){
        for (Ticket ticket : ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }

}
