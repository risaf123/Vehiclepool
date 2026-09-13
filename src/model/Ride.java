package model;

public class Ride {
    private int rideId;
    private int driverId;
    private String driverName;
    private int vehicleId;
    private String vehicleNo;
    private String source;
    private String destination;
    private String pickupPoint;
    private String rideDate;
    private String rideTime;
    private int availableSeats;
    private double pricePerSeat;

    public Ride(int rideId, int driverId, String driverName, int vehicleId, String vehicleNo,
                String source, String destination, String pickupPoint,
                String rideDate, String rideTime, int availableSeats, double pricePerSeat) {
        this.rideId = rideId;
        this.driverId = driverId;
        this.driverName = driverName;
        this.vehicleId = vehicleId;
        this.vehicleNo = vehicleNo;
        this.source = source;
        this.destination = destination;
        this.pickupPoint = pickupPoint;
        this.rideDate = rideDate;
        this.rideTime = rideTime;
        this.availableSeats = availableSeats;
        this.pricePerSeat = pricePerSeat;
    }

    public int getRideId() { return rideId; }
    public int getDriverId() { return driverId; }
    public String getDriverName() { return driverName; }
    public int getVehicleId() { return vehicleId; }
    public String getVehicleNo() { return vehicleNo; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public String getPickupPoint() { return pickupPoint; }
    public String getRideDate() { return rideDate; }
    public String getRideTime() { return rideTime; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPricePerSeat() { return pricePerSeat; }

    @Override
    public String toString() {
        return pickupPoint + " -> " + destination + " | " + rideDate + " " + rideTime
                + " | Rs" + pricePerSeat + " | Seats left: " + availableSeats
                + " | Driver: " + driverName + " | Vehicle: " + vehicleNo;
    }
}