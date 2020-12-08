package group.ACupOfJava.service;

import group.ACupOfJava.pojo.Room;
import group.ACupOfJava.pojo.Seat;

import java.util.List;

public interface SeatService {
    public void takeSeats(List<Seat> seats);
    public Room getRoomRowAndCol(String room_id);
}
