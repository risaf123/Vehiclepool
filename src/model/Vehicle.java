package model;

public class Vehicle {
    private int vehicleId;
    private int userId;
    private String vehicleNo;
    private String vehicleType;
    private int capacity;

    public Vehicle(int vehicleId, int userId, String vehicleNo, String vehicleType, int capacity) {
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
    } 

    public int getVehicleId() { return vehicleId; }
    public int getUserId() { return userId; }
    public String getVehicleNo() { return vehicleNo; }
    public String getVehicleType() { return vehicleType; }
    public int getCapacity() { return capacity; }
}