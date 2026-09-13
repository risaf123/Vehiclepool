package model;

public class Booking {
    private int bookingId;
    private int rideId;
    private int riderId;
    private String dropPoint;
    private int seatsBooked;

    public Booking(int rideId, int riderId, String dropPoint, int seatsBooked) {
        this.rideId = rideId;
        this.riderId = riderId;
        this.dropPoint = dropPoint;
        this.seatsBooked = seatsBooked;
    }

    public int getRideId() { return rideId; }
    public int getRiderId() { return riderId; }
    public String getDropPoint() { return dropPoint; }
    public int getSeatsBooked() { return seatsBooked; }
}