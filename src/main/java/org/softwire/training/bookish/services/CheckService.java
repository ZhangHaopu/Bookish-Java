package org.softwire.training.bookish.services;


import org.softwire.training.bookish.models.database.Technology;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckService extends DatabaseService{
    public void checkoutBycopyid(String username, int copyid){
        jdbi.useHandle(handle ->{
                handle.createUpdate("UPDATE copyOfBooks SET available = FALSE WHERE id_copyofbooks = :id" )
                        .bind("id", copyid)
                        .execute();
                handle.createUpdate("INSERT INTO transactions (userid, bookid, outdate, duedate) " +
                        "SELECT members.id_members, :bookid, GETDATE(), DATEADD(day, 14, GETDATE()) " +
                        "FROM members WHERE members.name = :username")
                        .bind("username" , username)
                        .bind("bookid", copyid)
                        .execute();

                }
        );
    }

    public LocalDateTime checkinBycopyid(int copyid){
        return jdbi.withHandle(handle ->{
            handle.createUpdate("UPDATE copyOfBooks SET available = TRUE WHERE id_copyofbooks = :id")
                    .bind("id", copyid)
                    .execute();
            List<LocalDateTime> duedate = handle.createQuery("SELECT dueDate From transactions WHERE bookid = :id AND returnDate IS NULL")
                    .bind("id", copyid)
                    .mapTo(LocalDateTime.class)
                    .list();
            handle.createUpdate("UPDATE transactions SET returnDate = GETDATE() WHERE bookid = :id")
                    .bind("id", copyid)
                    .execute();
            return duedate.get(0);
        });

    }






}
