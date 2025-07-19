package ticket.booking.entities;

import lombok.Getter;

public class Ticket {
    @Getter
    private String ticketId;
    @Getter
    private String userId;
    @Getter
    private String source;
    @Getter
    private String destination;
    @Getter
    private String dateOfTravel;
    @Getter
    private Train train;

    public Ticket(){}

    public Ticket(String ticketId, String userId, String source, String destination, String dateOfTravel, Train train){
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }
    public String getTicketInfo(){
        return String.format("Ticket ID: %s belongs to User %s from %s to %s on %s", ticketId, userId, source, destination, dateOfTravel);
    }

    public String getTicketId(){
        return ticketId;
    }
    public String getUserId(){
        return userId;
    }

    public String getDestination(){
        return destination;
    }
    public String getDateOfTravel(){
        return dateOfTravel;
    }
    public Train getTrain(){
        return train;
    }

    public void setTicketId(String ticketId){
        this.ticketId = ticketId;
    }
    public void setSource(String source){
        this.source = source;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setDateOfTravel(String dateOfTravel){
        this.dateOfTravel = dateOfTravel;
    }
    public void setTrain(Train train){
        this.train = train;
    }
}
