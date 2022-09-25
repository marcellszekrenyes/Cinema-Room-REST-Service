package cinema;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.*;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class SeatController {
    CinemaRoom room = new CinemaRoom();
    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/seats")

    public String returnSeats() {

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(room);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }

    @PostMapping("/purchase")
    public ResponseEntity purchaseSeat(@RequestBody Seat seatRequest) {

        if(seatRequest.getRow() <= 0 || seatRequest.getColumn() <= 0 || seatRequest.getRow() > 9 || seatRequest.getColumn() > 9){

            HashMap<String, String> outOfBounds = new HashMap<>();
            outOfBounds.put("error", "The number of a row or a column is out of bounds!");

            return new ResponseEntity<>(outOfBounds, HttpStatus.BAD_REQUEST);
        }

        ArrayList<Seat> seats = room.getAvailable_seats();
        for (Seat seat : seats) {
            if (seat.getRow() == seatRequest.getRow() && seat.getColumn() == seatRequest.getColumn()) {
                seat.seatIsAvailable(false);

                HashMap<String, Object> mapToPrint = new HashMap<>();
                mapToPrint.put("token", seat.getToken().toString());
                mapToPrint.put("ticket", seat);
                return new ResponseEntity<>(mapToPrint, HttpStatus.OK);
            }

        }
            HashMap<String, String> seatIsTaken = new HashMap<>();
            seatIsTaken.put("error", "The ticket has been already purchased!");

            return new ResponseEntity<>(seatIsTaken, HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/return")
    public ResponseEntity returnTicket (@RequestBody HashMap<String, String> token) {
        String key = "";
        for(String element : token.values()){
            key = element;
        }
        for (Seat seat: room.getAll_seats()){
            if(seat.getToken().toString().equals(key)){
                seat.seatIsAvailable(true);
                return ResponseController.returnHandler(seat, HttpStatus.OK);
            }
        }

        return ResponseController.errorHandler("Wrong token!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/stats")
    public ResponseEntity returnStats(@RequestParam(name="password",required=false) String password){
        String pwTester = null;
        pwTester = password;
        if (pwTester == null || !password.equals(CinemaRoom.password)){
            return ResponseController.errorHandler("The password is wrong!", HttpStatus.UNAUTHORIZED);
        }
        return ResponseController.statsHandler(room.currentStats(), HttpStatus.OK);

    }


    /*@PostMapping("/test")
    public String testRequest(@RequestBody HashMap<String, String> token) {

    }*/
}
