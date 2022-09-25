package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseController {

    public static ResponseEntity errorHandler(String message, HttpStatus status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("error", message);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity returnHandler(Seat returnedSeat, HttpStatus status){
        HashMap<String, Object> response = new HashMap<>();
        response.put("returned_ticket", returnedSeat);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity statsHandler(HashMap<String, Object> stats, HttpStatus status){
        return new ResponseEntity<>(stats, status);
    }
}
