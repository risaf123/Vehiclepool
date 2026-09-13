package model;

public class Ride {
    private int rideId;
    private int driverId;
    private int vehicleId;
    private String source;
    private String destination;
    private String pickupPoint;
    private String rideDate;
    private String rideTime;
    private int availableSeats;
    private double pricePerSeat;

    public Ride(int driverId, int vehicleId, String source, String destination,
                String pickupPoint, String rideDate, String rideTime,
                int availableSeats, double pricePerSeat) {
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.source = source;
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
    }

    public int getDriverId() { return driverId; }
    public int getVehicleId() { return vehicleId; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getPickupPoint() { return pickupPoint; }
    public String getRideDate() { return rideDate; }
    public String getRideTime() { return rideTime; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPricePerSeat() { return pricePerSeat; }
}