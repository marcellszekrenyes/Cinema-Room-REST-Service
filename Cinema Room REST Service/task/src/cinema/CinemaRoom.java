package cinema;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

@JsonIgnoreProperties(value = { "all_seats"})
public class CinemaRoom {
    private int total_rows = 9;
    private int total_columns = 9;
    private final ArrayList<Seat> all_seats;
    final static String password = "super_secret";
    public CinemaRoom() {
        this.all_seats = this.createSeats();
    }

    private ArrayList<Seat> createSeats(){

        ArrayList<Seat> seats = new ArrayList<>();

        for(int j = 1; j <= this.total_rows; j++) {
            for(int k = 1; k <= this.total_columns; k++){
                seats.add(new Seat(j, k));
            }
        }
        return seats;
    }

    public ArrayList<Seat> getAvailable_seats(){
        ArrayList<Seat> available_seats = new ArrayList<>();
        available_seats = this.all_seats.stream().filter(Seat::isAvailable).collect(Collectors.toCollection(ArrayList::new));
        return available_seats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public ArrayList<Seat> getAll_seats() {
        return all_seats;
    }

    public HashMap<String, Object> currentStats(){
        HashMap<String, Object> stats = new HashMap<>();
        int currentIncome = 0;

        ArrayList<Seat> booked_seats = this.all_seats.stream().filter(Seat -> !Seat.isAvailable()).collect(Collectors.toCollection(ArrayList::new));

        for(Seat seat : booked_seats){
            currentIncome += seat.getPrice();
        }
        stats.put("current_income", currentIncome);
        stats.put("number_of_available_seats", this.getAvailable_seats().size());
        stats.put("number_of_purchased_tickets", booked_seats.size());

        return stats;
    }
}