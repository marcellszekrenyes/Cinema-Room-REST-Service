package cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@JsonIgnoreProperties(value = { "available", "token"})
public class Seat {
    private int row;
    private int column;
    private int price;
    private boolean isAvailable;
    private UUID token;
    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isAvailable = true;
        this.token = UUID.randomUUID();
        this.setSeatPrice();
    }

    public Seat() {
    }

    private void setSeatPrice(){
        if (this.row <= 4){
            this.price = 10;
        } else {
            this.price = 8;
        }
    }

    //If you call the seatIsAvailable() method, the input parameter
    //changes the state of seat accordingly:
    //true == available, false == booked,
    //and in case of return of a ticket, assigns a new UUID
    public void seatIsAvailable(Boolean changeTo){
        this.isAvailable = changeTo;
        if(changeTo == true){
            this.token = UUID.randomUUID();
        }
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }
}