package org.softwire.training.bookish.services;


import org.springframework.stereotype.Service;

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





}
