import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection{
    public static void main(String[]args){
        String url = "jdbc:mysql://localhost:3306/vehicle_pool?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user ="root";
        String password= "Risaf@123";

        try {

            Connection con =DriverManager.getConnection(url,user,password);
            System.out.println("Sucess:Connected to vehicle_pool database!");
            con.close();
        } catch (Exception e){
            System.out.println("FAILED to connect.");
            e.printStackTrace();
        }

    }



}